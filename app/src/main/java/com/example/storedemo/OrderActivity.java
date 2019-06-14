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

import com.example.storedemo.Order.Order;
import com.example.storedemo.Order.OrderDAO;
import com.example.storedemo.adapter.OrderListAdapter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderActivity extends Activity {

    private ListView listView;
    private OrderDAO orderDAO;
    private String username;
    private List<Order> list;
    private OrderListAdapter adapter;

    private ImageView imageView;

    AnimationSet animationSet;
    Animation animation;
    LayoutAnimationController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定删除?")
                        .setContentText("删除后不能恢复该记录")
                        .setConfirmText("删除!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                orderDAO.Delete_From_Order(list.get(position).getOrder_id(),list.get(position).getOrder_username());
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

        orderDAO=new OrderDAO(OrderActivity.this);
        listView=(ListView)findViewById(R.id.order_lv);

        list=orderDAO.GetAllFromOrderByUsername(username);
        adapter=new OrderListAdapter(OrderActivity.this,list);
        AnimationSortList();
        listView.setLayoutAnimation(controller);
        listView.setAdapter(adapter);

        imageView=(ImageView)findViewById(R.id.order_bk_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
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
