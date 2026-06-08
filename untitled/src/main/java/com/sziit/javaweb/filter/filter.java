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

        // 核心：直接放行登录相关的基础文件，不要用 contains，用 endsWith
        if (uri.endsWith("login.html") || uri.endsWith("LoginServlet") || uri.endsWith(".css") || uri.endsWith(".js")) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 跳转到根目录下的 login.html
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        chain.doFilter(req, res);
    }
}