package com.sziit.javaweb.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

    @Autowired
    private WelcomeService welcomeService;

    public void handleWelcome(String studentName) {
        welcomeService.welcome(studentName);
    }
}