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

        if (username == null || password == null) {
            resp.sendRedirect("loginFail.html");
            return;
        }

        User user = UserJdbc.getUserByName(username);

        if (null != user && user.getPassword().equals(password)) {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");
            PrintWriter writer = resp.getWriter();
            writer.println("<h1>欢迎登录，" + user.getName() + "!</h1>");
            writer.println("<h1><a href=\"login.html\">退出登录</a></h1>");
        } else {
            resp.sendRedirect("loginFail.html");
        }

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("main.html");
            return;
        }
        resp.sendRedirect("loginFail.html");
    }
}