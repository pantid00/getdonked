package com.sziit.javaweb.dao;

import com.sziit.javaweb.entity.User;
import java.sql.Connection;

import java.sql.*;
public class UserDaoImpl implements UserDao {
    @Override
    public Connection getConnection()  throws SQLException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/web?useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, "root", "123456");
        }
        catch (Exception e)
        {
            System.err.println("【数据库连接失败！】请检查账号密码！");
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public void release(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByName(String userName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = this.getConnection();
            String sql = "select * from user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String sql = "insert into user(username,password,name,age) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }
    }
}
