package com.example.storedemo;

/**
 * Created by Anleeno on 2019/3/5.
 */

public class Collect {
    String c_bk_ISBN;
    String c_username;

    public Collect(String c_bk_ISBN, String c_username) {
        this.c_bk_ISBN = c_bk_ISBN;
        this.c_username = c_username;
    }

    public String getC_bk_ISBN() {
        return c_bk_ISBN;
    }

    public void setC_bk_ISBN(String c_bk_ISBN) {
        this.c_bk_ISBN = c_bk_ISBN;
    }

    public String getC_username() {
        return c_username;
    }

    public void setC_username(String c_username) {
        this.c_username = c_username;
    }
}
