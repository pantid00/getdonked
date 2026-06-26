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

        if (uri.endsWith("/login.html") ||
                uri.endsWith("/axiosdemo1.html") ||
                uri.endsWith("/user1") ||
                uri.endsWith("/login") ||
                uri.endsWith("/loginFail.html") ||
                uri.endsWith("/register.html") ||
                uri.endsWith("/register") ||
                uri.endsWith("/logout") ||
                uri.endsWith(".js") ||
                uri.endsWith("/checkUser") ||
                uri.endsWith("/axios") ||
                uri.endsWith(".css")) {
            chain.doFilter(req, res);
            return;
        }

        if (uri.equals(contextPath + "/")) {
            response.sendRedirect(contextPath + "/login.html");
            return;
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(contextPath + "/login.html");
            return;
        }

        chain.doFilter(req, res);
    }
}