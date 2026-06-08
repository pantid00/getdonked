package com.sziit.javaweb.service;

import com.sziit.javaweb.entity.User;

public interface UserService {
    /**
     * 用户登录业务
     * @return 登录成功返回用户对象，失败返回 null
     */
    User login(String username, String password);

    /**
     * 检查用户名是否已存在
     * @return true 存在，false 不存在
     */
    boolean checkUsernameExists(String username);

    /**
     * 用户注册业务
     * @return true 注册成功，false 注册失败
     */
    boolean register(User user);
}