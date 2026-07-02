package com.sziit.javaweb.service;

import com.sziit.javaweb.entity.User;
import java.util.List;

public interface UserService {
    public User login(String username, String password);

    public boolean register(User user);

    public boolean checkUsernameExists(String username);

    public List<User> getAllUsers();
    public void updateUser(User user);
    public void deleteUser(String username);
}