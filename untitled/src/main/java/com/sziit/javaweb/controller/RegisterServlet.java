package com.sziit.javaweb.controller;

import com.sziit.javaweb.dao.UserJdbc;
import com.sziit.javaweb.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String age = req.getParameter("age");

        if (username == null || password == null || name == null || age == null
                || username.equals("") || password.equals("") || name.equals("") || age.equals(""))
        {
            resp.sendRedirect("registerFail.html");
            return;
        }

        User userByName = UserJdbc.getUserByName(username);
        if (null != userByName)
        {
            resp.sendRedirect("registerRepeat.html");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAge(Integer.parseInt(age));

        UserJdbc.addUser(user);
        resp.sendRedirect("registerSuccess.html");
    }
}