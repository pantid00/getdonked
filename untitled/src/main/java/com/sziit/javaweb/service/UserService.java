package com.sziit.javaweb.service;

import com.sziit.javaweb.entity.User;

public interface UserService {
    public User login(String username, String password);

    public boolean register(User user);

    public boolean checkUsernameExists(String username);
}