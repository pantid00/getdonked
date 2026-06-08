package com.sziit.javaweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            System.out.println("Servlet: 正在注销 Session，ID: " + session.getId());
            session.invalidate(); // <--- 彻底销毁服务器内存中的会话数据
        }

        // 重定向回登录页面
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}