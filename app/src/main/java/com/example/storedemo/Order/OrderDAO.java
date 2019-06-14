package com.example.storedemo.Order;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.storedemo.DBHelper.StoreDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class OrderDAO {
    private StoreDBHelper helper;
    private SQLiteDatabase db;

    public OrderDAO(Context context) {
        helper = new StoreDBHelper(context);
    }

    public void Insert_Order(Order order){
        db=helper.getWritableDatabase();
        String string="INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?)";
        db.execSQL(string,new Object[]{
                order.getOrder_id(),
                order.getOrder_bkpic(),
                order.getOrder_username(),
                order.getOrder_bkname(),
                order.getOrder_num(),
                order.getOrder_address(),
                order.getOrder_phone(),
                order.getOrder_totalprice(),
                order.getOrder_price()
        });
        db.close();
    }
    public void Delete_From_Order(String orderid,String username){
        db=helper.getWritableDatabase();
        String sql="Delete from orders where order_id=? and order_username=?";
        db.execSQL(sql, new Object[]{orderid,username});
        db.close();
    }

    public List<Order> GetAllFromOrderByUsername(String username) {
        List<Order> list = new ArrayList<Order>();
        Order order;
        db = helper.getWritableDatabase();
        String sql="SELECT * from orders where order_username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        while (cursor.moveToNext()) {
            order=new Order(cursor.getString(cursor.getColumnIndex("order_id")),
                    cursor.getString(cursor.getColumnIndex("order_bkpic")),
                    cursor.getString(cursor.getColumnIndex("order_username")),
                    cursor.getString(cursor.getColumnIndex("order_bkname")),
                    cursor.getInt(cursor.getColumnIndex("order_num")),
                    cursor.getString(cursor.getColumnIndex("order_address")),
                    cursor.getString(cursor.getColumnIndex("order_phone")),
                    cursor.getDouble(cursor.getColumnIndex("order_totalprice")),
                    cursor.getDouble(cursor.getColumnIndex("order_price")));
            list.add(order);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Order> GetAllFromOrderByUsernameAndOrderid(String username,String orderid) {
        List<Order> list = new ArrayList<Order>();
        Order order;
        db = helper.getWritableDatabase();
        String sql="SELECT * from orders where order_username = ? and order_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username,orderid});
        while (cursor.moveToNext()) {
            order=new Order(cursor.getString(cursor.getColumnIndex("order_id")),
                    cursor.getString(cursor.getColumnIndex("order_bkpic")),
                    cursor.getString(cursor.getColumnIndex("order_username")),
                    cursor.getString(cursor.getColumnIndex("order_bkname")),
                    cursor.getInt(cursor.getColumnIndex("order_num")),
                    cursor.getString(cursor.getColumnIndex("order_address")),
                    cursor.getString(cursor.getColumnIndex("order_phone")),
                    cursor.getDouble(cursor.getColumnIndex("order_totalprice")),
                    cursor.getDouble(cursor.getColumnIndex("order_price")));
            list.add(order);
        }
        cursor.close();
        db.close();
        return list;
    }
}
