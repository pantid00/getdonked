package com.sziit.javaweb.controller;

import com.sziit.javaweb.entity.User;
import com.sziit.javaweb.service.UserService;
import com.sziit.javaweb.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 引入业务层对象
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            resp.sendRedirect(req.getContextPath() + "/loginFail.html");
            return;
        }

        // Controller 不做具体逻辑比对，直接扔给 Service
        User user = userService.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            System.out.println("Servlet: 登录成功，SessionID: " + session.getId());
            resp.sendRedirect(req.getContextPath() + "/main.html");
        } else {
            System.out.println("Servlet: 登录失败，凭证错误");
            resp.sendRedirect(req.getContextPath() + "/loginFail.html");
        }
    }
}