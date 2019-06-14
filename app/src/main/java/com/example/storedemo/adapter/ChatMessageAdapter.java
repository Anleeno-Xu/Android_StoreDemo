package com.example.storedemo.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.storedemo.R;
import com.example.storedemo.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            //通过ItemType设置不同的布局
            if (getItemViewType(position) == 0){
                convertView = mInflater.inflate(R.layout.item_from_msg,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = convertView.findViewById(R.id.tv_from_msg_date);
                viewHolder.mMsg = convertView.findViewById(R.id.tv_from_msg_info);
            }else{
                convertView = mInflater.inflate(R.layout.item_send_msg,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = convertView.findViewById(R.id.tv_send_msg_date);
                viewHolder.mMsg = convertView.findViewById(R.id.tv_send_msg_info);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));

        //实现同一字符串不同位置的字体大小显示
        String str=chatMessage.getMsg()+"\n\n"+df.format(chatMessage.getDate());
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(10),str.length()-9,str.length()-8,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(28),str.length()-8,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.mMsg.setText(spannableStringBuilder);

        return convertView;
    }

    /**
     * 如果一个布局只要重写默认的四个方法就行
     * 这里多个布局要多重写getItemViewType()和getViewTypeCount()方法
     * 如果是接收消息return 0; 如果是发送消息return 1
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING)
            return 0;
        else
            return 1;
    }

    /**
     * 因为有两种布局，所以return 2
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    private final class ViewHolder{
        TextView mDate;//时间
        TextView mMsg;//消息
    }
}
