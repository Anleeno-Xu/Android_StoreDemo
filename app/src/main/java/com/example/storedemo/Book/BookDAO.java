package com.example.storedemo.Book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.storedemo.DBHelper.StoreDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/2/27.
 */

public class BookDAO {
    private StoreDBHelper helper;
    private SQLiteDatabase db;

    public BookDAO(Context context) {
        helper = new StoreDBHelper(context);
    }

    public void Insert_Book(List<Book> list) {
        db = helper.getWritableDatabase();
        String sql = "INSERT INTO book VALUES (?,?,?,?,?,?,?)";
        db.beginTransaction();
        try {
            for (int i = 0; i < list.size(); i++) {
                db.execSQL(sql, new Object[]{
                        list.get(i).getBk_ISBN(),
                        list.get(i).getBk_name(),
                        list.get(i).getBk_author(),
                        list.get(i).getBk_picuri(),
                        list.get(i).getBk_press(),
                        list.get(i).getBk_detail(),
                        list.get(i).getBk_price()
                });
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
        db.close();
    }

    //    public void update(Note note){
    //        db=helper.getWritableDatabase();
    //        String sql="Update Note set title=?, content=?,date=? where id=?";
    //        db.execSQL(sql,new Object[]{
    //                note.getTitle(),
    //                note.getContent(),
    //                note.getDate(),
    //                note.getId()
    //        });
    //        db.close();
    //    }
    public List<Book> SelectAllFromBook() {
        List<Book> list = new ArrayList<>();
        Book book;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM book", new String[]{});
        while (cursor.moveToNext()) {
            book = new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
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

    //    public Book FindBookByISBN(String ISBN) {
    //        Book book;
    //        db = helper.getWritableDatabase();
    //        String sql = "SELECT * FROM book where bk_ISBN= ?";
    //        Cursor cursor = db.rawQuery(sql, new String[]{ISBN});
    //        if (cursor.moveToNext()) {
    //            book = new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
    //                    cursor.getString(cursor.getColumnIndex("bk_name")),
    //                    cursor.getString(cursor.getColumnIndex("bk_author")),
    //                    cursor.getString(cursor.getColumnIndex("bk_picuri")),
    //                    cursor.getString(cursor.getColumnIndex("bk_press")),
    //                    cursor.getString(cursor.getColumnIndex("bk_detail")),
    //                    cursor.getDouble(cursor.getColumnIndex("bk_price")));
    //            return book;
    //        }
    //        cursor.close();
    //        db.close();
    //        return null;
    //    }

    public void Delete_Book_ByISBN(String ISBN) {
        db = helper.getWritableDatabase();
        db.execSQL("Delete from book where bk_ISBN=" + ISBN);
        db.close();
    }

    public List<Book> GetBookByName(String bk_name) {
        List<Book> list = new ArrayList<>();
        Book book;
        db = helper.getWritableDatabase();
        String str = "SELECT * FROM book where bk_name like ?";
        Cursor cursor = db.rawQuery(str, new String[]{"%" + bk_name + "%"});
        while (cursor.moveToNext()) {
            book = new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
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

    //获取分类（出版社）
    public List<String> GetPressFromBook() {
        List<String> list = new ArrayList<>();
        list.add("推荐");
        db = helper.getWritableDatabase();
        String str = "SELECT distinct bk_press FROM book order by bk_press";
        Cursor cursor = db.rawQuery(str, new String[]{});
        while (cursor.moveToNext()) {
            String str1= cursor.getString(cursor.getColumnIndex("bk_press"));
            list.add(str1);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Book> GetPressList(String bk_press) {
        List<Book> list = new ArrayList<>();
        Book book;
        db = helper.getWritableDatabase();
        String str = "SELECT * FROM book where bk_press =?";
        Cursor cursor = db.rawQuery(str, new String[]{bk_press});
        while (cursor.moveToNext()) {
            book = new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
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

    public List<String> SelectPicFromBook() {
        List<String> list = new ArrayList<>();
        String str;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT bk_picuri FROM book limit 10", new String[]{});
        while (cursor.moveToNext()) {
            str = cursor.getString(cursor.getColumnIndex("bk_picuri"));
            list.add(str);
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<String> SelectBnameFromBook() {
        List<String> list = new ArrayList<>();
        String str;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT bk_name FROM book limit 10", new String[]{});
        while (cursor.moveToNext()) {
            str = cursor.getString(cursor.getColumnIndex("bk_name"));
            list.add(str);
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<Book> Initdata_sort_right(){
        List<Book> list=new ArrayList<>();
        Book book;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM book limit 30", new String[]{});
        while (cursor.moveToNext()) {
            book=new Book(cursor.getString(cursor.getColumnIndex("bk_ISBN")),
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

}
