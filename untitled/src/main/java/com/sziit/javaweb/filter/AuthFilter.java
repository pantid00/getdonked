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

        // 1. 公开资源白名单：如果是白名单资源，直接放行并终止后续 Filter 逻辑
        if (isPublicResource(uri)) {
            chain.doFilter(req, res);
            return;
        }

        // 2. 特殊放行：如果是直接访问项目根路径，且没有登录，强制送去登录页
        if (uri.equals(contextPath + "/")) {
            response.sendRedirect(contextPath + "/login.html");
            return; // 必须切断响应
        }

        // 3. 严格权限校验：非白名单的受保护资源（如 main.html），必须持有合法的登录 Session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("【！！！绝对新代码拦截！！！】非法尝试访问受保护资源 -> " + uri);

            // 设置防缓存响应头，双重保险
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // 重定向到登录页面
            response.sendRedirect(contextPath + "/login.html");
            return; // <--- 【核心修复点】重定向后必须立刻 return 终止方法，绝对不能再往下走 chain.doFilter() ！
        }

        // 4. 只有前三步都合法通过（即已登录用户），才允许访问 main.html 等核心页面
        chain.doFilter(req, res);
    }

    private boolean isPublicResource(String uri) {
        return uri.endsWith("/login.html") ||
                uri.endsWith("/login") ||
                uri.endsWith("/loginFail.html") ||
                uri.endsWith("/register.html") ||
                uri.endsWith("/register") ||
                uri.endsWith("/logout") ||
                uri.endsWith(".js") ||
                uri.endsWith(".css");
    }
}