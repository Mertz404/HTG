package com.htg;

public class LoginResponse {
    String status;
    String message;
    User user;

    public class User {
        int id;
        String name;
        String permission;
        String plan;
        int timesLogged;
    }
}
