package com.example.storedemo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.storedemo.Book.Book;
import com.example.storedemo.Book.BookDAO;
import com.example.storedemo.Fragment.CartFragment;
import com.example.storedemo.Fragment.HomeFragment;
import com.example.storedemo.Fragment.PersonFragment;
import com.example.storedemo.Fragment.SortFragment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    List<Fragment> list;//存储页面对象
    private BottomNavigationView navigation;

    private List<Book> bookList;

    private BookDAO bookDAO1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        bookList = new ArrayList<Book>();
        bookList = readExcel("/storage/emulated/0/tencent/QQfile_recv/book.xls");
        bookDAO1 = new BookDAO(this);
        bookDAO1.Insert_Book(bookList);

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.getBackground().setAlpha(60);
        //BottomNavigationViewHelper.disableShiftMode(navigation);//取消动画

        //向viewpager添加页面
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new SortFragment());
        list.add(new CartFragment());
        list.add(new PersonFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, list);
        viewPager.setAdapter(viewPagerAdapter);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_sort:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_cart:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_person:
                        viewPager.setCurrentItem(3);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static List<Book> readExcel(String filename) {
        List<Book> bookList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            Workbook wb = null;
            if (filename.toLowerCase().endsWith("xls")) {
                wb = new HSSFWorkbook(fileInputStream);
            }
            int numberofsheet = wb.getNumberOfSheets();
            for (int i = 0; i < numberofsheet; ++i) {
                Sheet sheet = wb.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    String bk_ISBN = "";//ISBN码
                    String bk_name = "";//书名
                    String bk_author = "";//作者
                    String bk_picuri = "";//封面
                    String bk_press = "";//分类
                    String bk_detail = "";//简介
                    double bk_price = 0;//价格
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case STRING:
                                if (bk_ISBN.equalsIgnoreCase("")) {
                                    bk_ISBN = cell.getStringCellValue().trim();
                                } else if (bk_name.equalsIgnoreCase("")) {
                                    bk_name = cell.getStringCellValue().trim();
                                } else if (bk_author.equalsIgnoreCase("")) {
                                    bk_author = cell.getStringCellValue().trim();
                                } else if (bk_picuri.equalsIgnoreCase("")) {
                                    bk_picuri = cell.getStringCellValue().trim();
                                } else if (bk_press.equalsIgnoreCase("")) {
                                    bk_press = cell.getStringCellValue().trim();
                                } else if (bk_detail.equalsIgnoreCase("")) {
                                    bk_detail = cell.getStringCellValue().trim();
                                } else {
                                    System.out.println("Random data::" + cell.getStringCellValue());
                                }
                                break;
                            case NUMERIC:
                                if (bk_price == 0) {
                                    bk_price = cell.getNumericCellValue();
                                } else {
                                    //System.out.println("Random data::"+cell.getNumericCellValue());
                                }
                        }
                    }
                    if (!"bk_ISBN".equals(bk_ISBN)) {
                        Book book = new Book(bk_ISBN, bk_name, bk_author, bk_picuri, bk_press, bk_detail, bk_price);
                        bookList.add(book);
                    }
                }
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
