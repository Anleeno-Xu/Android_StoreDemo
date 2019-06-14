package com.example.storedemo.Cart;

import java.io.Serializable;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class Cart implements Serializable{
    private String cart_id;//cart_id=username+ISBN
    private String cart_bkpic;//书图片
    private String cart_username;//用户名
    private String cart_bkname;//书名
    private int cart_num;//数量
    private double cart_price;//单价

    public Cart(String cart_id, String cart_bkpic, String cart_username, String cart_bkname, int cart_num, double cart_price) {
        this.cart_id = cart_id;
        this.cart_bkpic = cart_bkpic;
        this.cart_username = cart_username;
        this.cart_bkname = cart_bkname;
        this.cart_num = cart_num;
        this.cart_price = cart_price;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getCart_bkpic() {
        return cart_bkpic;
    }

    public void setCart_bkpic(String cart_bkpic) {
        this.cart_bkpic = cart_bkpic;
    }

    public String getCart_username() {
        return cart_username;
    }

    public void setCart_username(String cart_username) {
        this.cart_username = cart_username;
    }

    public String getCart_bkname() {
        return cart_bkname;
    }

    public void setCart_bkname(String cart_bkname) {
        this.cart_bkname = cart_bkname;
    }

    public int getCart_num() {
        return cart_num;
    }

    public void setCart_num(int cart_num) {
        this.cart_num = cart_num;
    }

    public double getCart_price() {
        return cart_price;
    }

    public void setCart_price(double cart_price) {
        this.cart_price = cart_price;
    }
}
