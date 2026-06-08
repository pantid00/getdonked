package com.sziit.javaweb.controller;

import com.sziit.javaweb.dao.UserJdbc;
import com.sziit.javaweb.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserJdbc.getUserByName(username);
        System.out.println("DEBUG: 从数据库查到的用户对象: " + user);
        System.out.println("DEBUG: 数据库密码: " + user.getPassword() + ", 输入密码: " + password);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession(true); // 确保创建 Session
            session.setAttribute("user", user);

            // 跳转到主页
            resp.sendRedirect(req.getContextPath() + "/main.html");
            return;
        } else {
            // 登录失败
            resp.sendRedirect(req.getContextPath() + "/loginFail.html");
            return;
        }
    }
}