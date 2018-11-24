package com.park.smet_k.bauman_gis.model;

public class User {
    private int id;
//    private String name;
//    private String email;
    private String login;
    private String password;

    public User(int id, String login, String password) {
        this.id = id;
//        this.name = name;
//        this.email = email;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
