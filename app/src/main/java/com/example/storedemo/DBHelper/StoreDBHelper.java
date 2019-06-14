package com.example.storedemo.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anleeno on 2019/2/28.
 */

public class StoreDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "test.db";
    public static final int DB_VERSION = 1;
    public StoreDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT," +
                "nick_name TEXT," +
                "icon VARCHAR(200))");
        db.execSQL("CREATE TABLE IF NOT EXISTS book(" +
                "bk_ISBN VARCHAR(25) PRIMARY KEY," +
                "bk_name VARCHAR(80)," +
                "bk_author VARCHAR(50),"+
                "bk_picuri VARCHAR(200),"+
                "bk_press VARCHAR(50),"+
                "bk_detail VARCHAR(2000),"+
                "bk_price NUMBER(4,2))");
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(" +
                "cart_id VARCHAR(40) PRIMARY KEY," +
                "cart_bkpic VARCHAR(200)," +
                "cart_username VARCHAR(20),"+
                "cart_bkname VARCHAR(80)," +
                "cart_num NUMBER(3)," +
                "cart_price NUMBER(4,2))");
        db.execSQL("CREATE TABLE IF NOT EXISTS orders(" +
                "order_id VARCHAR(50) PRIMARY KEY," +
                "order_bkpic VARCHAR(200)," +
                "order_username VARCHAR(20),"+
                "order_bkname VARCHAR(80)," +
                "order_num NUMBER(3)," +
                "order_address VARCHAR(100)," +
                "order_phone VARCHAR(20),"+
                "order_totalprice NUMBER(6,2)," +
                "order_price NUMBER(4,2))");

        db.execSQL("CREATE TABLE IF NOT EXISTS collect (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "c_bk_ISBN VARCHAR(25)," +
                "c_username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
