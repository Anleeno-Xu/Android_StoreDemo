package com.example.storedemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Book.Book;
import com.example.storedemo.R;

import java.util.List;

/**
 * Created by Anleeno on 2019/3/1.
 */

public class BookRecAdapter extends RecyclerView.Adapter<BookRecAdapter.BookRecViewHolder> {
    private Context mContext;
    /**
     * 数据集合
     */
    private List<Book> data;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        Void onItemClick(View v,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public BookRecAdapter(Context context, List<Book> data) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public BookRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new BookRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookRecViewHolder holder, int position) {
        //将数据设置到item上
        Book book = data.get(position);
        //holder.bk_pic.setImageResource(book.getBk_picuri());
        holder.bk_name.setText(book.getBk_name());
        holder.bk_author.setText(book.getBk_author());
        holder.bk_price.setText(book.getBk_price() + " ￥");
        Glide.with(mContext)
                .load(Uri.parse(book.getBk_picuri()))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.bk_pic);

        if(onItemClickListener!=null){
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class BookRecViewHolder extends RecyclerView.ViewHolder {
        ImageView bk_pic;
        TextView bk_name, bk_author, bk_price;
        LinearLayout linearLayout;

        public BookRecViewHolder(View itemView) {
            super(itemView);
            bk_pic = itemView.findViewById(R.id.iv_item);
            bk_name = itemView.findViewById(R.id.bk_rec_name);
            bk_author = itemView.findViewById(R.id.bk_rec_author);
            bk_price = itemView.findViewById(R.id.bk_rec_price);
            linearLayout=itemView.findViewById(R.id.rec_layout);
        }
    }
}
