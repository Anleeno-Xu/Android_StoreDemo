package com.example.storedemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.storedemo.Book.Book;
import com.example.storedemo.R;

import java.util.List;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class Press_List_Adapter extends BaseAdapter {
    List<String> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    private int selectposition;

    public void setSelectposition(int selectposition) {
        this.selectposition = selectposition;
    }

    public Press_List_Adapter(Context context, List<String> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.press_list_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.bk_press = (TextView) convertView.findViewById(R.id.press_tv);

            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        if(selectposition==position){
            holder.bk_press.setTextColor(context.getResources().getColor(R.color.mycolor));
            holder.bk_press.setTextSize(18);
        }else {
            holder.bk_press.setTextColor(0x8a000000);
            holder.bk_press.setTextSize(16);
        }

        String press = list.get(position);

        holder.bk_press.setText(press);

        return convertView;
    }
    private class ViewHolder {
        TextView bk_press;
    }
}
