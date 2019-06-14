package com.example.storedemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Order.Order;
import com.example.storedemo.R;

import java.util.List;

/**
 * Created by Anleeno on 2019/3/3.
 */

public class OrderListAdapter extends BaseAdapter {
    List<Order> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    public OrderListAdapter(Context context, List<Order> list) {
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
            convertView = mInflater.inflate(R.layout.order_list_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.orderusername = (TextView) convertView.findViewById(R.id.username_tv);
            holder.orderphone = (TextView) convertView.findViewById(R.id.phone_tv);
            holder.orderaddress = (TextView) convertView.findViewById(R.id.address_tv);
            holder.orderId = (TextView) convertView.findViewById(R.id.order_id_tv);

            holder.orderpic = (ImageView) convertView.findViewById(R.id.order_pic_iv);
            holder.orderbkname = (TextView) convertView.findViewById(R.id.order_bkname_tv);
            holder.orderbknum = (TextView) convertView.findViewById(R.id.order_bknum);
            holder.orderbkprice = (TextView) convertView.findViewById(R.id.order_bkprice);

            holder.ordertotalprice = (TextView) convertView.findViewById(R.id.totalprice_tv);
            holder.pay = (TextView) convertView.findViewById(R.id.to_pay_tv);

            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = list.get(position);

        holder.orderusername.setText("用户名:"+order.getOrder_username());
        holder.orderphone.setText("电话:" + order.getOrder_phone());
        holder.orderaddress.setText("地址:"+order.getOrder_address());
        holder.orderId.setText("订单号:"+order.getOrder_id());
        holder.ordertotalprice.setText("合计:"+order.getOrder_totalprice()+"￥");

        holder.orderbkname.setText(order.getOrder_bkname());
        holder.orderbknum.setText("数量:"+order.getOrder_num());
        holder.orderbkprice.setText(order.getOrder_price()+" ￥");

        Glide.with(context)
                .load(Uri.parse(order.getOrder_bkpic()))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.orderpic);
        return convertView;
    }


    private class ViewHolder {
        TextView orderusername, orderphone, orderaddress, orderId, ordertotalprice, orderbkname, orderbknum, orderbkprice, pay;
        ImageView orderpic;
    }
}
