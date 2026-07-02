package com.sziit.javaweb.entity;

public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private String avatar; // 新增头像路径属性

    public User() {
    }

    public User(String username, String password, String name, int age, String avatar) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.avatar = avatar;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}