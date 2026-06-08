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

        // 1. 公开资源白名单
        if (isPublicResource(uri)) {
            chain.doFilter(req, res);
            return;
        }

        // 2. 权限校验
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("安全拦截: 非法尝试访问 " + uri);

            // 【重点】设置响应头，强制浏览器不缓存这个重定向页面
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isPublicResource(String uri) {
        return uri.endsWith("/login.html") ||
                uri.endsWith("/login") ||
                uri.endsWith(".js") ||
                uri.endsWith(".css");
    }
}