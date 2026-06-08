package com.sziit.javaweb.service;

import com.sziit.javaweb.dao.UserJdbc;
import com.sziit.javaweb.entity.User;
import com.sziit.javaweb.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String username, String password) {
        // 核心业务：先查用户，再比对密码
        User user = UserJdbc.getUserByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        User user = UserJdbc.getUserByName(username);
        return user != null;
    }

    @Override
    public boolean register(User user) {
        // 如果用户名已存在，则不允许注册
        if (checkUsernameExists(user.getUsername())) {
            return false;
        }
        // 调用持久层保存用户
        return UserJdbc.saveUser(user);
    }
}