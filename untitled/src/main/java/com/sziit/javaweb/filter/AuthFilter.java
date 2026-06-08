package com.sziit.javaweb.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // 1. 精准白名单：登录、注册、注销接口及静态资源，直接放行
        if (uri.endsWith("/login.html") ||
                uri.endsWith("/login") ||
                uri.endsWith("/loginFail.html") ||
                uri.endsWith("/register.html") ||
                uri.endsWith("/register") ||
                uri.endsWith("/logout") ||
                uri.endsWith(".js") ||
                uri.endsWith(".css")) {
            chain.doFilter(req, res);
            return;
        }

        // 2. 特殊处理：如果是直接访问项目根路径，且没有登录，强制送去登录页
        if (uri.equals(contextPath + "/")) {
            response.sendRedirect(contextPath + "/login.html");
            return;
        }

        // 3. 核心拦截：非白名单页面（如 main.html），没登录一律在这里击杀
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("【核心拦截】拦截到未登录访问: " + uri);

            // 强制清理浏览器缓存，防止浏览器用历史缓存欺骗用户
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // 重定向并立刻 return，绝不把请求传递给下一个 Filter 或 Servlet
            response.sendRedirect(contextPath + "/login.html");
            return;
        }

        // 4. 只有登录正常的请求，才允许放行
        chain.doFilter(req, res);
    }
}