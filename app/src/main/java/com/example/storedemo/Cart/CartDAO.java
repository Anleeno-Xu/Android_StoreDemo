package com.example.storedemo.Cart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.storedemo.DBHelper.StoreDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class CartDAO {
    private StoreDBHelper helper;
    private SQLiteDatabase db;

    public CartDAO(Context context) {
        helper = new StoreDBHelper(context);
    }

    public void Insert_Cart(Cart cart){
        db=helper.getWritableDatabase();
        String string="INSERT INTO cart VALUES(?,?,?,?,?,?)";
        db.execSQL(string,new Object[]{
                cart.getCart_id(),
                cart.getCart_bkpic(),
                cart.getCart_username(),
                cart.getCart_bkname(),
                cart.getCart_num(),
                cart.getCart_price()
        });
        db.close();
    }
    public void Delete_From_Cart(String cartid){
        db=helper.getWritableDatabase();
        String sql="Delete from cart where cart_id=?";
        db.execSQL(sql, new Object[]{cartid});
        db.close();
    }

    public void Update_Cart_item(Cart cart){
        db=helper.getWritableDatabase();
        String str="Update cart set cart_num=?";
        db.execSQL(str,new Object[]{
                cart.getCart_num()
        });
        db.close();
    }

    public List<Cart> GetAllFromCartbyUsername(String username) {
        List<Cart> list = new ArrayList<Cart>();
        Cart cart;
        db = helper.getWritableDatabase();
        String sql="SELECT * from cart where cart_username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        while (cursor.moveToNext()) {
            cart=new Cart(cursor.getString(cursor.getColumnIndex("cart_id")),
                    cursor.getString(cursor.getColumnIndex("cart_bkpic")),
                    cursor.getString(cursor.getColumnIndex("cart_username")),
                    cursor.getString(cursor.getColumnIndex("cart_bkname")),
                    cursor.getInt(cursor.getColumnIndex("cart_num")),
                    cursor.getDouble(cursor.getColumnIndex("cart_price")));
            list.add(cart);
        }
        db.close();
        return list;
    }

    public List<Cart> GetAllFromCartbyCartID(String cartid) {
        List<Cart> list = new ArrayList<Cart>();
        Cart cart;
        db = helper.getWritableDatabase();
        String sql="SELECT * from cart where cart_id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{cartid});
        while (cursor.moveToNext()) {
            cart=new Cart(cursor.getString(cursor.getColumnIndex("cart_id")),
                    cursor.getString(cursor.getColumnIndex("cart_bkpic")),
                    cursor.getString(cursor.getColumnIndex("cart_username")),
                    cursor.getString(cursor.getColumnIndex("cart_bkname")),
                    cursor.getInt(cursor.getColumnIndex("cart_num")),
                    cursor.getDouble(cursor.getColumnIndex("cart_price")));
            list.add(cart);
        }
        db.close();
        return list;
    }
}
