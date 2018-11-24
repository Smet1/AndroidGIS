package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

public class User {
//    @SerializedName ("id")
//    private int id;
//    private String name;
//    private String email;
    @SerializedName ("login")
    private String login;

    @SerializedName ("password")
    private String password;


    public User(String login, String password) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
        this.login = login;
        this.password = password;
    }

//    public int getId() {
//        return id;
//    }

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
