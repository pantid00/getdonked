package com.sziit.javaweb.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class filter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // 1. 公开资源白名单
        if (isPublicResource(uri)) {
            chain.doFilter(req, res);
            return;
        }

        // 2. 特殊放行：如果是直接访问项目根路径，且没有登录，强制送去登录页
        if (uri.equals(contextPath + "/")) {
            response.sendRedirect(contextPath + "/login.html");
            return; // 必须切断
        }

        // 3. 权限校验：非白名单的受保护资源（如 main.html），必须持有合法的登录 Session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("【核心拦截】拒绝未登录用户访问受保护资源 -> " + uri);

            // 彻底斩断浏览器缓存，确保退出了就绝对无法利用本地缓存渲染页面
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // 执行重定向
            response.sendRedirect(contextPath + "/login.html");
            return; // <--- 【核心修复点】这里极其关键，确保当前 Filter 链路彻底终结，绝不执行后面的 chain.doFilter！
        }

        // 4. 只有 Session 校验完全通过的用户，才允许放行
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