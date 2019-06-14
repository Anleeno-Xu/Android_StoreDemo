package com.example.storedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.storedemo.Cart.Cart;
import com.example.storedemo.Order.Order;
import com.example.storedemo.Order.OrderDAO;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends Activity {

    private EditText address, phone, num_et;
    private ImageView bk_pic, back_iv;
    private TextView bk_name, bk_num, bk_price, order_id, order_totalprice, user_name;
    private Button commit, add, sub;

    private Cart cart1;

    private OrderDAO orderDAO;
    private Order order;
    double num;
    double total;
    int number, bknum2=1;
    String orderid1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initview();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cart1 = (Cart) bundle.getSerializable("cart");

        Glide.with(PayActivity.this)
                .load(cart1.getCart_bkpic())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(bk_pic);
        num = cart1.getCart_num();

        bk_name.setText(cart1.getCart_bkname());
        bk_num.setText("数量：" + cart1.getCart_num());
        bk_price.setText(cart1.getCart_price() + " ￥");
        user_name.setText("用户名：" + cart1.getCart_username());
        order_id.setText("订单号：" + cart1.getCart_id() + "order");
        orderid1 = cart1.getCart_id() + "order";
        order_totalprice.setText("总价："+cart1.getCart_price()+ " ￥");

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                num_et.setText(number + "");
                bk_num.setText("数量：" + num_et.getText());

                bknum2 = Integer.parseInt(num_et.getText().toString());
                total = bknum2 * cart1.getCart_price();
                order_totalprice.setText("总价："+total + " ￥");

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 1) {
                    number--;
                    num_et.setText(number + "");
                    bk_num.setText("数量：" + num_et.getText());

                    bknum2 = Integer.parseInt(num_et.getText().toString());
                    total = bknum2 * cart1.getCart_price();
                    order_totalprice.setText("总价："+total + " ￥");
                }
            }
        });

        num_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    String str = s.toString();
                    bk_num.setText("数量：" + str);

                    bknum2 = Integer.parseInt(num_et.getText().toString());
                    total = bknum2 * cart1.getCart_price();
                    order_totalprice.setText("总价："+total + " ￥");
                }
                else {
                    Toast.makeText(PayActivity.this,"请输入数量！",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!address.getText().toString().equals("")) {
                    if (!phone.getText().toString().equals("")) {
                        if (!bk_num.getText().toString().equals("")) {
                            //如果之前没有买，直接生成cartid+order的订单号
                            orderDAO = new OrderDAO(PayActivity.this);

                            if(orderDAO.GetAllFromOrderByUsernameAndOrderid(cart1.getCart_username(),orderid1).isEmpty()){
                                String bkpic1 = cart1.getCart_bkpic();
                                String username1 = cart1.getCart_username();
                                String bkname1 = cart1.getCart_bkname();

                                String address1 = address.getText().toString();
                                String phone1 = phone.getText().toString();

                                double bkprice1 = cart1.getCart_price();


                                double totalprice1 = total;

                                order = new Order(orderid1, bkpic1, username1, bkname1, bknum2, address1, phone1, totalprice1, bkprice1);
                                orderDAO.Insert_Order(order);

                                Toast.makeText(PayActivity.this, "已提交订单！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(PayActivity.this, "已购买过该商品！", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(PayActivity.this, "请填写数量！", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PayActivity.this, "请填写电话！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PayActivity.this, "请填写地址！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initview() {
        commit = (Button) findViewById(R.id.commit_bt);

        bk_name = (TextView) findViewById(R.id.write_bkname_tv);
        bk_num = (TextView) findViewById(R.id.write_bknum);
        bk_price = (TextView) findViewById(R.id.write_bkprice);
        order_id = (TextView) findViewById(R.id.write_order_id_tv);
        order_totalprice = (TextView) findViewById(R.id.write_totalprice);

        bk_pic = (ImageView) findViewById(R.id.write_pic_iv);
        back_iv = (ImageView) findViewById(R.id.write_back_iv);
        address = (EditText) findViewById(R.id.address_et);
        phone = (EditText) findViewById(R.id.phone_et);

        user_name = (TextView) findViewById(R.id.user_tv);

        add = (Button) findViewById(R.id.add_bt);
        sub = (Button) findViewById(R.id.sub_bt);
        num_et = (EditText) findViewById(R.id.num_et);
        number = 1;
        num_et.setText(number + "");
    }
}
