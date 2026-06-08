package com.sziit.javaweb.controller;

import com.sziit.javaweb.service.UserService;
import com.sziit.javaweb.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkUser")
public class UsernameCheckServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        resp.setContentType("text/plain;charset=utf-8");

        // 调用业务层判断是否存在
        boolean exists = userService.checkUsernameExists(username);

        if (exists) {
            resp.getWriter().write("fail"); // 用户名已被占用
        } else {
            resp.getWriter().write("success"); // 用户名可用
        }
    }
}