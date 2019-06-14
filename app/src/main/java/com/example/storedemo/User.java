package com.example.storedemo;

import android.net.Uri;

/**
 * Created by Anleeno 2018/6/24
 */
public class User {
    private String name;            //用户名
    private String password;//密码
    private String nick_name;//昵称
    private String icon;//头像

    public User(String name, String password, String nick_name, String icon) {
        this.name = name;
        this.password = password;
        this.nick_name = nick_name;
        this.icon = icon;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

