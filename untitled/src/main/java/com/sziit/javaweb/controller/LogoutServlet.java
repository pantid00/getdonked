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
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 获取当前会话
        HttpSession session = req.getSession();
        // 2. 强制销毁 Session（让里面的 "user" 标记失效）
        session.invalidate();

        // 3. 退出后跳转回登录页
        resp.sendRedirect("login.html");
    }
}