package com.sziit.javaweb.entity;

import org.springframework.stereotype.Component;

@Component
public class WelcomeServiceImpl implements WelcomeService {
    @Override
    public void welcome(String name) {
        System.out.println("SZIIT提示：欢迎来到信息学院，" + name + " 同学！");
    }
}