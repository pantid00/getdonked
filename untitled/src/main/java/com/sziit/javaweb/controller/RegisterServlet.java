package com.sziit.javaweb.controller;

import com.sziit.javaweb.entity.User;
import com.sziit.javaweb.service.UserService;
import com.sziit.javaweb.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String ageStr = req.getParameter("age");

        if (username == null || password == null || ageStr == null) {
            resp.sendRedirect(req.getContextPath() + "/registerFail.html");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(password);
        user.setAge(Integer.parseInt(ageStr));

        boolean isSuccess = userService.register(user);

        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/registerSuccess.html");
        } else {
            resp.sendRedirect(req.getContextPath() + "/registerRepeat.html");
        }
    }
}