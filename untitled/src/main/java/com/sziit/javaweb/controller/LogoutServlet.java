package com.sziit.javaweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 销毁服务器端会话
        HttpSession session = req.getSession(false);
        if (session != null) {
            System.out.println("Servlet: 正在注销 Session，ID: " + session.getId());
            session.invalidate();
        }

        // 2. 物理清除浏览器本地的 JSESSIONID 凭证（核心保险）
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0); // 立刻过期
        cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath()); // 确保路径一致
        resp.addCookie(cookie);

        // 3. 重定向回登录页面
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}