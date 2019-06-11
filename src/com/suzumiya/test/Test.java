package com.suzumiya.test;

import com.suzumiya.service.UserService;

public class Test {
    public static void main(String[] args) {
        UserService service = new UserService();
        String name = service.getFirstName();
        System.out.println(name);
    }
}
