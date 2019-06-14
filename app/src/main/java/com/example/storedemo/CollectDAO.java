package com.example.storedemo;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.storedemo.Book.Book;
import com.example.storedemo.DBHelper.StoreDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/3/5.
 */
public class CollectDAO {
     StoreDBHelper helper;
     SQLiteDatabase db;
    Context context;
    public CollectDAO(Context context) {
        helper = new StoreDBHelper(context);
        this.context=context;
    }

    public void Insert_Collect(Collect collect) {
        String sql = "INSERT INTO collect VALUES (null,?,?)";
        db=helper.getWritableDatabase();
        db.execSQL(sql,new Object[]{
                collect.getC_bk_ISBN(),
                collect.getC_username()
        });
        db.close();
    }

    public void Delete_Collect(String ISBN, String username) {
        db=helper.getWritableDatabase();
        String sql = "delete from collect where c_bk_ISBN = ?  and c_username = ?";
        db.execSQL(sql,new String[]{ISBN,username});
        db.close();
    }

    public List<Book> Select_Collect(String username) {
        List<Book> list = new ArrayList<>();
        db=helper.getWritableDatabase();
        String sql = "select * from collect,book where collect.c_bk_ISBN =book.bk_ISBN and collect.c_username =?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        while (cursor.moveToNext()) {
            Book book = new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
                    cursor.getString(cursor.getColumnIndex("bk_name")),
                    cursor.getString(cursor.getColumnIndex("bk_author")),
                    cursor.getString(cursor.getColumnIndex("bk_picuri")),
                    cursor.getString(cursor.getColumnIndex("bk_press")),
                    cursor.getString(cursor.getColumnIndex("bk_detail")),
                    cursor.getDouble(cursor.getColumnIndex("bk_price")));
            list.add(book);
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<Collect> Select_Collect_is_Repeated(String username,String isbn) {
        List<Collect> list = new ArrayList<>();
        db=helper.getWritableDatabase();
        String sql = "select * from collect where c_username = ? and c_bk_ISBN= ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username,isbn});
        while (cursor.moveToNext()) {
            Collect collect=new Collect(cursor.getString(cursor.getColumnIndex("c_username")),
                    cursor.getString(cursor.getColumnIndex("c_bk_ISBN")));
            list.add(collect);
        }
        cursor.close();
        db.close();
        return list;
    }
}
