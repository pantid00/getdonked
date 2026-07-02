package com.sziit.javaweb.entity;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        Student student = context.getBean(Student.class);

        student.handleWelcome("欧阳宇曦");
    }
}