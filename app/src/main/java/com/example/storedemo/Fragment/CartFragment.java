package com.example.storedemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storedemo.Cart.Cart;
import com.example.storedemo.Cart.CartDAO;
import com.example.storedemo.Order.Order;
import com.example.storedemo.Order.OrderDAO;
import com.example.storedemo.PayActivity;
import com.example.storedemo.R;
import com.example.storedemo.adapter.CartListAdapter;

import org.w3c.dom.Text;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Anleeno on 2019/1/23.
 */

public class CartFragment extends Fragment {
    private CartListAdapter adapter;
    private List<Cart> list;
    private ListView listView;
    private CartDAO cartDAO;
    private String username;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        username = sp.getString("username", "noname");

        init();

        listView.setItemsCanFocus(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定删除?")
                        .setContentText("删除后不能恢复该记录")
                        .setConfirmText("删除!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                cartDAO.Delete_From_Cart(list.get(position).getCart_id());
                                sweetAlertDialog.setTitleText("删除成功!")
                                        .setContentText("已从记录中删除")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                init();
                            }
                        })
                        .show();
                return true;
            }
        });

        return view;
    }

    private void init() {
        cartDAO = new CartDAO(getActivity());
        listView = (ListView) view.findViewById(R.id.cart_lv);

        list = cartDAO.GetAllFromCartbyUsername(username);

        adapter = new CartListAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }
}
