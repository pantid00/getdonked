package com.sziit.javaweb.dao;

import com.sziit.javaweb.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public Connection getConnection() throws SQLException;
    public void release(ResultSet rs, PreparedStatement ps, Connection conn);
    public User getUserByName(String userName);
    public void addUser(User user);

    public List<User> getAllUsers();
    public void updateUser(User user);
    public void deleteUser(String username);
}
