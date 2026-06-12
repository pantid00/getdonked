package com.sziit.javaweb.controller;

import com.sziit.javaweb.dao.UserDao;
import com.sziit.javaweb.dao.UserDaoImpl;
import com.sziit.javaweb.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user1")
public class User1Servlet extends HttpServlet {
    UserDao userDao = new UserDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        User user = userDao.getUserByName(username);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        if (user != null) {
            String userstr = "[" + user.getUsername() + "," + user.getName() + "," + user.getAge() + "]";
            resp.getWriter().write(userstr);
        }
        else {
            resp.getWriter().write("用户不存在");
        }
    }
}
