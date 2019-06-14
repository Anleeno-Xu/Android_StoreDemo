package com.example.storedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.storedemo.Book.Book;
import com.example.storedemo.Book.BookDAO;
import com.example.storedemo.adapter.ListViewAdpter;

import java.util.List;

public class SearchActivity extends Activity {

    private ImageView bk_iv,clear_iv;
    private EditText ed_search;
    private ListView search_lv;
    private List<Book> bookList;
    private BookDAO bookDAO;

    AnimationSet animationSet;
    Animation animation;
    LayoutAnimationController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initview();
    }

    private void initview(){
        bk_iv=findViewById(R.id.search_back_iv);
        ed_search=findViewById(R.id.search_et);
        search_lv=findViewById(R.id.search_bk_lv);
        clear_iv=findViewById(R.id.clear);
        bookDAO=new BookDAO(this);

        bk_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                //在该改变事件里调用查询函数，可以即输即查
                //                if (!str.equals("")) {
                //                    clear.setVisibility(View.VISIBLE);

                if (!str.equals("")) {
                    clear_iv.setVisibility(View.VISIBLE);

                } else {
                    clear_iv.setVisibility(View.INVISIBLE);
                }

                bookList = bookDAO.GetBookByName(str);
                ListViewAdpter adapter = new ListViewAdpter(SearchActivity.this, bookList);
                AnimationSearchList();
                search_lv.setLayoutAnimation(controller);
                search_lv.setAdapter(adapter);
            }

            public void afterTextChanged(Editable s) {

            }
        });

        clear_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ed_search.getText().clear();
            }
        });

        search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void AnimationSearchList(){

        //加入动画
        animationSet=new AnimationSet(true);
        animation=new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(400);
        animationSet.addAnimation(animation);
        animation=new TranslateAnimation(-500,0,
                400,0);
        animation.setDuration(250);
        animationSet.addAnimation(animation);
        controller=new LayoutAnimationController(animationSet,0.5f);
    }

}
