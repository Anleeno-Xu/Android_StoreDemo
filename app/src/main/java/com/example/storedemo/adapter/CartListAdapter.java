package com.example.storedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Book.Book;
import com.example.storedemo.Cart.Cart;
import com.example.storedemo.PayActivity;
import com.example.storedemo.R;

import java.util.List;

/**
 * Created by Anleeno on 2019/3/2.
 */

public class CartListAdapter extends BaseAdapter {
    List<Cart> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    public CartListAdapter(Context context, List<Cart> list) {
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
            convertView = mInflater.inflate(R.layout.cart_list_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.bk_name = (TextView) convertView.findViewById(R.id.cart_bk_name_tv);
            holder.bk_num = (TextView) convertView.findViewById(R.id.bk_num_tv);
            holder.bk_price = (TextView) convertView.findViewById(R.id.cart_bk_price_tv);
            holder.bk_pic = (ImageView) convertView.findViewById(R.id.cart_bookpic_iv);
            holder.add = (TextView) convertView.findViewById(R.id.add_tv);
            holder.sub = (TextView) convertView.findViewById(R.id.sub_tv);
            holder.num = (TextView) convertView.findViewById(R.id.num_tv);
            holder.pay_cash = (LinearLayout) convertView.findViewById(R.id.cash_layout);

            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        Cart cart = list.get(position);

        holder.bk_name.setText(cart.getCart_bkname());
        holder.bk_num.setText("数量:" + cart.getCart_num());
        holder.bk_price.setText(cart.getCart_price() + " ￥");
        holder.num.setText(cart.getCart_num() + "");


        Glide.with(context)
                .load(Uri.parse(cart.getCart_bkpic()))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.bk_pic);

        holder.pay_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cart", cart);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        TextView bk_name, bk_num, bk_price, sub, add, num;
        ImageView bk_pic;
        LinearLayout pay_cash;
    }
}
