package com.example.storedemo.Fragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.storedemo.Book.Book;
import com.example.storedemo.Book.BookDAO;
import com.example.storedemo.DetailActivity;
import com.example.storedemo.R;
import com.example.storedemo.adapter.ListViewAdpter;
import com.example.storedemo.adapter.Press_List_Adapter;

import java.util.List;

/**
 * Created by Anleeno on 2019/1/23.
 */

public class SortFragment extends Fragment {
    private ListView lv_left, lv_right;
    private BookDAO bookDAO;
    private List<Book> bookList2;
    private List<String> presslist;
    private Press_List_Adapter adapter;
    private ListViewAdpter adapter2;

    AnimationSet animationSet;
    Animation animation;
    LayoutAnimationController controller;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        lv_left = view.findViewById(R.id.sort_left);
        lv_right = view.findViewById(R.id.sort_right);

        bookDAO = new BookDAO(getActivity());
        presslist = bookDAO.GetPressFromBook();//获取出版社
        bookList2 = bookDAO.Initdata_sort_right();//初始填充


        adapter = new Press_List_Adapter(getActivity(), presslist);
        lv_left.setAdapter(adapter);

        adapter2 = new ListViewAdpter(getActivity(), bookList2);
        lv_right.setAdapter(adapter2);

        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectposition(position);
                adapter.notifyDataSetInvalidated();

                if (position==0){//第一个推荐的数据
                    bookList2 = bookDAO.Initdata_sort_right();//初始填充
                    adapter2 = new ListViewAdpter(getActivity(), bookList2);
                    AnimationSortList();
                    lv_right.setLayoutAnimation(controller);
                    lv_right.setAdapter(adapter2);
                }else {
                    String press = presslist.get(position);
                    bookList2 = bookDAO.GetPressList(press);
                    adapter2 = new ListViewAdpter(getActivity(), bookList2);
                    AnimationSortList();
                    lv_right.setLayoutAnimation(controller);
                    lv_right.setAdapter(adapter2);
                }
            }
        });

        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book2 = bookList2.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    public void AnimationSortList() {

        //加入动画
        animationSet = new AnimationSet(true);
        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(400);
        animationSet.addAnimation(animation);
        animation = new TranslateAnimation(500, 0,
                -500, 0);
        animation.setDuration(250);
        animationSet.addAnimation(animation);
        controller = new LayoutAnimationController(animationSet, 0.5f);
    }

}
