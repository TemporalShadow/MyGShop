package com.example.MyGShop.ui.home;

import java.util.Date;

public class User {

    private int user_id;
    private String user_name;
    private String user_pass;
    private String user_nick;
    private String user_email;
    private int user_phone;
    private String user_birth;
    private int user_profile;


    public User(String user_name, String user_pass, String user_nick, String user_email, int user_phone, String user_birth, int user_profile) {
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.user_nick = user_nick;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_birth = user_birth;
        this.user_profile = user_profile;
    }

    public User(int user_id, String user_name, String user_pass, String user_nick, String user_email, int user_phone, String user_birth, int user_profile) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.user_nick = user_nick;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_birth = user_birth;
        this.user_profile = user_profile;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(int user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public int getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(int user_profile) {
        this.user_profile = user_profile;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_pass='" + user_pass + '\'' +
                ", user_nick='" + user_nick + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_phone=" + user_phone +
                ", user_birth=" + user_birth +
                ", user_profile=" + user_profile +
                '}';
    }
}
