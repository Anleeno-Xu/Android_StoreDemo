package com.example.storedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.storedemo.DBHelper.StoreDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/2/28.
 */

public class UserDAO {
    private StoreDBHelper helper;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        helper = new StoreDBHelper(context);
    }

    public void Insert_User(User user) {
        db = helper.getWritableDatabase();
        String sql = "INSERT INTO user VALUES (null,?,?,?,?)";
        db.execSQL(sql, new Object[]{
                user.getName(),
                user.getPassword(),
                user.getNick_name(),
                user.getIcon()
        });
        db.close();
    }

    public void update(String password,String nick_name,String icon,String username) {
        db = helper.getWritableDatabase();
        String sql = "Update user set password=?, nick_name=?,icon=? where name=?";
        db.execSQL(sql, new String[]{password,nick_name,icon,username});
        db.close();
    }

    public List<User> SelectAllFromUser() {
        List<User> list = new ArrayList<User>();
        User user;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", new String[]{});
        while (cursor.moveToNext()) {
            user = new User(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("nick_name")),
                    cursor.getString(cursor.getColumnIndex("icon")));
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    //查找用户信息
    public User SelectUserByName(String name) {
        db = helper.getWritableDatabase();
        String string = "SELECT * FROM user where name=?";
        Cursor cursor = db.rawQuery(string, new String[]{name});
        if (cursor.moveToNext()) {
            return new User(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("nick_name")),
                    cursor.getString(cursor.getColumnIndex("icon")));
        }
        cursor.close();
        db.close();
        return null;
    }

    public void Delete_User(String name) {
        db = helper.getWritableDatabase();
        db.execSQL("Delete from user where name=" + name);
        db.close();
    }
}
