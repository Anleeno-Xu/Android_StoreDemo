package com.example.storedemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Book.Book;
import com.example.storedemo.Book.BookDAO;

import com.example.storedemo.DetailActivity;
import com.example.storedemo.R;
import com.example.storedemo.SearchActivity;
import com.example.storedemo.adapter.BookRecAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anleeno on 2019/1/23.
 */

public class HomeFragment extends Fragment {

    private List<Book> bookList = new ArrayList<Book>();
    //private ListViewAdpter adpter;
    private BookDAO bookDAO;
    private RecyclerView rv;
    private BookRecAdapter adapter;
    private Banner banner;

    AnimationSet animationSet;
    android.view.animation.Animation animation;
    LayoutAnimationController controller;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bookDAO = new BookDAO(getActivity());
        bookList = bookDAO.SelectAllFromBook();

        rv = view.findViewById(R.id.recycler_view);
        banner=view.findViewById(R.id.banner);

        //轮播图
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(bookDAO.SelectPicFromBook());
        banner.setBannerTitles(bookDAO.SelectBnameFromBook());
        banner.setDelayTime(2000);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String str=bookDAO.SelectBnameFromBook().get(position);
                Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
            }
        });
        banner.start();

        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //线性布局Manager
        //        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        //        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网络布局Manager
        //        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        //给recyclerView设置LayoutManager
        rv.setLayoutManager(recyclerViewLayoutManager);

        adapter = new BookRecAdapter(getActivity(), bookList);
        adapter.setOnItemClickListener(new BookRecAdapter.OnItemClickListener() {
            @Override
            public Void onItemClick(View v, int position) {
                Book book = bookList.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                intent.putExtras(bundle);
                startActivity(intent);
                return null;
            }
        });

        AnimationHomeList();
        rv.setLayoutAnimation(controller);
        rv.setAdapter(adapter);

        LinearLayout view1 = (LinearLayout) view.findViewById(R.id.search_view);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        ImageView scan = view.findViewById(R.id.scan_iv);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Scan", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void AnimationHomeList() {

        //加入动画
        animationSet = new AnimationSet(true);
        //alpha属性
        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        animationSet.addAnimation(animation);
        //位移属性
        animation = new TranslateAnimation(1.0f, 0.0f,
                1.0f, 0.0f);
        animation.setDuration(300);
        animationSet.addAnimation(animation);
        controller = new LayoutAnimationController(animationSet, 0.5f);
    }

    //重写imageloader方法，使用glide方式加载图片
    public class GlideImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                    .into(imageView);
        }
    }

}
