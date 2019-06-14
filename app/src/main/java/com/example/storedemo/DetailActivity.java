package com.example.storedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.storedemo.Book.Book;
import com.example.storedemo.Cart.Cart;
import com.example.storedemo.Cart.CartDAO;

import java.util.List;

public class DetailActivity extends Activity {

    private Book book;
    private TextView bk_isbn, bk_press, bk_name, bk_author, bk_detail, bk_price, addcart, buy;
    private LinearLayout collect, kf;
    private ImageView bk_pic, detail_back_iv,collect_iv;

    private String username;

    private CartDAO cartDAO;
    CollectDAO collectDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initview();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book");

        initdata();

        collectDAO=new CollectDAO(DetailActivity.this);
        if(collectDAO.Select_Collect_is_Repeated(username,book.getBk_ISBN()).isEmpty()){
            collect_iv.setImageResource(R.drawable.shoucang);
        }else {
            collect_iv.setImageResource(R.drawable.shoucang_red);
        }

        initclick();

    }

    private void initdata() {
        bk_isbn.setText("ISBN：" + book.getBk_ISBN());
        bk_name.setText(book.getBk_name());
        Glide.with(this)
                .load(Uri.parse(book.getBk_picuri()))
                .into(bk_pic);
        bk_press.setText("出版社：" + book.getBk_press());
        bk_author.setText("作者：" + book.getBk_author());
        bk_detail.setText(book.getBk_detail());
        bk_price.setText(book.getBk_price() + " ￥");

        SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        username = sp.getString("username", "noname");
    }

    private void initview() {
        //基本信息
        bk_pic = (ImageView) findViewById(R.id.bk_pic_iv);
        bk_isbn = (TextView) findViewById(R.id.ISBN);
        bk_press = (TextView) findViewById(R.id.press);
        bk_name = (TextView) findViewById(R.id.name);
        bk_author = (TextView) findViewById(R.id.author);
        bk_detail = (TextView) findViewById(R.id.detail);
        bk_price = (TextView) findViewById(R.id.price);
        detail_back_iv = (ImageView) findViewById(R.id.detail_back_iv);

        addcart = (TextView) findViewById(R.id.addtocart_tv);
        buy = (TextView) findViewById(R.id.buy_tv);
        collect = (LinearLayout) findViewById(R.id.collect_layout);
        kf = (LinearLayout) findViewById(R.id.kf_layout);

        collect_iv=(ImageView)findViewById(R.id.detail_collect_iv);


    }

    private void initclick() {
        kf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CustomerServiceActivity.class);
                startActivity(intent);
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(collectDAO.Select_Collect_is_Repeated(username,book.getBk_ISBN()).isEmpty()){
                    collectDAO.Insert_Collect(new Collect(book.getBk_ISBN(),username));
                    Toast.makeText(DetailActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                    collect_iv.setImageResource(R.drawable.shoucang_red);
                }else {
                    collectDAO.Delete_Collect(book.getBk_ISBN(),username);
                    Toast.makeText(DetailActivity.this,"已取消收藏",Toast.LENGTH_SHORT).show();
                    collect_iv.setImageResource(R.drawable.shoucang);
                }


            }
        });
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDAO = new CartDAO(DetailActivity.this);
                String cartid = username + book.getBk_ISBN();
                List<Cart> list = cartDAO.GetAllFromCartbyCartID(cartid);
                if (!list.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "不能重复加入！", Toast.LENGTH_SHORT).show();
                } else {
                    cartDAO.Insert_Cart(new Cart(username + book.getBk_ISBN(), book.getBk_picuri(), username, book.getBk_name(), 1, book.getBk_price()));
                    Toast.makeText(getApplicationContext(), "已加入购物车！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart(username + book.getBk_ISBN(), book.getBk_picuri(), username, book.getBk_name(), 1, book.getBk_price());
                Intent intent = new Intent(DetailActivity.this, PayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cart", cart);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"购买",Toast.LENGTH_SHORT).show();
            }
        });

        detail_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
