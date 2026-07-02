package com.sziit.javaweb.controller;

import com.google.gson.Gson;
import com.sziit.javaweb.dao.UserDaoImpl;
import com.sziit.javaweb.entity.User;
import com.sziit.javaweb.service.UserService;
import com.sziit.javaweb.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        resp.setContentType("application/json;charset=utf-8");

        if (username != null && !username.trim().isEmpty()) {
            User targetUser = new UserDaoImpl().getUserByName(username);
            resp.getWriter().write(gson.toJson(targetUser));
        } else {
            List<User> allUsers = userService.getAllUsers();
            resp.getWriter().write(gson.toJson(allUsers));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        User user = parseUserRequest(req);

        resp.setContentType("application/json;charset=utf-8");
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            resp.getWriter().write("{\"status\":\"error\",\"message\":\"参数解析失败\"}");
            return;
        }

        boolean isSuccess = userService.register(user);
        if (isSuccess) {
            resp.getWriter().write("{\"status\":\"success\"}");
        } else {
            resp.getWriter().write("{\"status\":\"exists\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        User user = parseUserRequest(req);

        resp.setContentType("application/json;charset=utf-8");
        if (user == null || user.getUsername() == null) {
            resp.getWriter().write("{\"status\":\"error\",\"message\":\"参数解析失败\"}");
            return;
        }

        userService.updateUser(user);
        resp.getWriter().write("{\"status\":\"success\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            userService.deleteUser(username);
        }
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write("{\"status\":\"success\"}");
    }

    private User parseUserRequest(HttpServletRequest req) throws IOException {
        String contentType = req.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            return gson.fromJson(sb.toString(), User.class);
        }
        else {
            User user = new User();
            user.setUsername(req.getParameter("username"));
            user.setPassword(req.getParameter("password"));
            user.setName(req.getParameter("name"));
            String ageStr = req.getParameter("age");
            if (ageStr != null && !ageStr.isEmpty()) {
                user.setAge(Integer.parseInt(ageStr));
            }
            return user;
        }
    }
}