package com.example.storedemo.Order;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class Order {
    private String order_id;//order_id=cart_id+order
    private String order_bkpic;
    private String order_username;
    private String order_bkname;
    private int order_num;
    private String order_address;
    private String order_phone;
    private double order_totalprice;//总价
    private double order_price;//单价

    public Order(String order_id, String order_bkpic, String order_username, String order_bkname, int order_num, String order_address, String order_phone, double order_totalprice, double order_price) {
        this.order_id = order_id;
        this.order_bkpic = order_bkpic;
        this.order_username = order_username;
        this.order_bkname = order_bkname;
        this.order_num = order_num;
        this.order_address = order_address;
        this.order_phone = order_phone;
        this.order_totalprice = order_totalprice;
        this.order_price = order_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_bkpic() {
        return order_bkpic;
    }

    public void setOrder_bkpic(String order_bkpic) {
        this.order_bkpic = order_bkpic;
    }

    public String getOrder_username() {
        return order_username;
    }

    public void setOrder_username(String order_username) {
        this.order_username = order_username;
    }

    public String getOrder_bkname() {
        return order_bkname;
    }

    public void setOrder_bkname(String order_bkname) {
        this.order_bkname = order_bkname;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public double getOrder_totalprice() {
        return order_totalprice;
    }

    public void setOrder_totalprice(double order_totalprice) {
        this.order_totalprice = order_totalprice;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }
}
