package com.example.storedemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.storedemo.Book.Book;
import com.example.storedemo.adapter.ListViewAdpter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CollectActivity extends Activity {

    private List<Book> list;
    private CollectDAO collectDAO;
    ListView lv;
    ListViewAdpter adpter;
    String username;
    ImageView back;

    AnimationSet animationSet;
    Animation animation;
    LayoutAnimationController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        init();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new SweetAlertDialog(CollectActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定删除?")
                        .setContentText("删除后不能恢复该记录")
                        .setConfirmText("删除!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                collectDAO.Delete_Collect(list.get(position).getBk_ISBN(),username);
                                sweetAlertDialog.setTitleText("删除成功!")
                                        .setContentText("已从记录中删除")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                init();
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    private void init(){
        SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        username = sp.getString("username", "noname");

        collectDAO=new CollectDAO(CollectActivity.this);
        list=collectDAO.Select_Collect(username);

        lv=(ListView)findViewById(R.id.collect_lv);
        adpter=new ListViewAdpter(CollectActivity.this,list);
        AnimationSortList();
        lv.setLayoutAnimation(controller);
        lv.setAdapter(adpter);

        back=(ImageView)findViewById(R.id.collect_bk_iv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void AnimationSortList(){

        //加入动画
        animationSet=new AnimationSet(true);
        animation=new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(400);
        animationSet.addAnimation(animation);
        animation=new TranslateAnimation(500,0,
                -1000,0);
        animation.setDuration(250);
        animationSet.addAnimation(animation);
        controller=new LayoutAnimationController(animationSet,0.5f);
    }
}
