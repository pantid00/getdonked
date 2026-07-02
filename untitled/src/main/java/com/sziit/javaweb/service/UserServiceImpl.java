package com.sziit.javaweb.service;

import com.sziit.javaweb.dao.UserDao;
import com.sziit.javaweb.dao.UserDaoImpl;
import com.sziit.javaweb.entity.User;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User userByName = userDao.getUserByName(username);
        if (null != userByName && userByName.getPassword().equals(password)) {
            return userByName;
        } else {
            return null;
        }
    }

    @Override
    public boolean register(User user) {
        User userByName = userDao.getUserByName(user.getUsername());
        if (null != userByName) {
            return false;
        }

        userDao.addUser(user);
        return true;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        com.sziit.javaweb.entity.User user = userDao.getUserByName(username);
        return user != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        userDao.deleteUser(username);
    }
}