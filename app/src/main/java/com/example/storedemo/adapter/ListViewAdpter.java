package com.example.storedemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Book.Book;
import com.example.storedemo.R;

import java.util.List;

/**
 * Created by Anleeno on 2019/2/27.
 */

public class ListViewAdpter extends BaseAdapter {
    List<Book> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    public ListViewAdpter(Context context, List<Book> list) {
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
            convertView = mInflater.inflate(R.layout.bk_list_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.bk_name = (TextView) convertView.findViewById(R.id.bk_name_tv);
            holder.bk_author=(TextView)convertView.findViewById(R.id.bk_author_tv);
            holder.bk_price=(TextView)convertView.findViewById(R.id.bk_price_tv);
            holder.bk_pic=(ImageView)convertView.findViewById(R.id.bookpic_iv);

            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = list.get(position);

        holder.bk_name.setText(book.getBk_name());
        holder.bk_author.setText(book.getBk_author());

        holder.bk_price.setText(book.getBk_price()+" ￥");
                Glide.with(context)
                        .load(Uri.parse(book.getBk_picuri()))
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(holder.bk_pic);
                        //holder.rectangleCircleImageView.setImageURI(Uri.parse(recipes.getIcon_addr()));

        return convertView;
    }


    private class ViewHolder {
        TextView bk_name,bk_author,bk_price;
        ImageView bk_pic;
    }
}
