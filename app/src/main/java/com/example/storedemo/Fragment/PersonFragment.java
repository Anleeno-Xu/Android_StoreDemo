package com.example.storedemo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Book.Book;
import com.example.storedemo.CollectActivity;
import com.example.storedemo.CustomerServiceActivity;
import com.example.storedemo.OrderActivity;
import com.example.storedemo.R;
import com.example.storedemo.SettingsActivity;
import com.example.storedemo.User;
import com.example.storedemo.UserDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Anleeno on 2019/1/23.
 */
/*Glide.with(context)
* .load(uri)
* .apply(requestoptions.bitmaptransform(new CircleCrop()))
* .into(iv);*/

public class PersonFragment extends Fragment {
    UserDAO userDAO;
    User user;
    String username;
    String password;
    ImageView my_icon;
    TextView nick;
    SharedPreferences sp;
    View view;
    Date date;
    String currenttime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);

        //获取登录信息
        init();

        //我的客服
        LinearLayout kf = (LinearLayout) view.findViewById(R.id.myservice_layout);
        kf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerServiceActivity.class);
                startActivity(intent);
            }
        });

        //我的订单
        LinearLayout myorder = (LinearLayout) view.findViewById(R.id.myorder_layout);
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });

        //我的收藏
        LinearLayout mycollect = (LinearLayout) view.findViewById(R.id.mycollect_layout);
        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.person_toolbar);
        toolbar.inflateMenu(R.menu.person_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.date:
                        Toast.makeText(getActivity(), currenttime, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Intent intent = new Intent(getActivity(), SettingsActivity.class);
                        startActivityForResult(intent, 0x333);
                        //Toast.makeText(getActivity(),"Setting",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x333 && resultCode == Activity.RESULT_OK) {
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    public void init(){
        sp = getActivity().getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        username = sp.getString("username", "noname");
        password = sp.getString("password", "000000");

        date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        currenttime=df.format(date.getTime());

        //获取数据
        userDAO = new UserDAO(getActivity());
        user = userDAO.SelectUserByName(username);


        my_icon = (ImageView) view.findViewById(R.id.icon_iv);
        Glide.with(getActivity())
                .load(Uri.parse(user.getIcon()))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(my_icon);
        nick = (TextView) view.findViewById(R.id.nick_name_tv);
        nick.setText("昵称："+user.getNick_name());
    }
}
