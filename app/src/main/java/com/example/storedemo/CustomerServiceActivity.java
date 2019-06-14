package com.example.storedemo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storedemo.adapter.ChatMessageAdapter;
import com.example.storedemo.bean.ChatMessage;
import com.example.storedemo.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerServiceActivity extends Activity {

    private ListView lv_msgs;
    private ChatMessageAdapter mAdater;
    private List<ChatMessage> mDatas;

    private EditText ed_input;
    private Button btn_send;

    private ImageButton back_ibt;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //等待子线程返回的数据然后更新UI
            ChatMessage fromChatMessage = (ChatMessage) msg.obj;
            mDatas.add(fromChatMessage);
            mAdater.notifyDataSetChanged();
            lv_msgs.setSelection(mAdater.getCount() - 1);//让listView始终显示最底下的数据，也就是实时显示最新消息，防止键盘的遮盖
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决软键盘调出布局上移的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_customer_service);

        initView();
        initDatas();
        initListener();

    }

    private void initListener() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检测网络状态，连接则继续下一步
                if (!isNetworkConnected(getApplicationContext())) {
                    Toast.makeText(CustomerServiceActivity.this, "网络已断开，请连接网络", Toast.LENGTH_SHORT).show();
                } else {//已连接网络
                    final String toMsg = ed_input.getText().toString();
                    if (TextUtils.isEmpty(toMsg)) {
                        Toast.makeText(CustomerServiceActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ChatMessage toChatMessage = new ChatMessage();
                    toChatMessage.setDate(new Date());
                    toChatMessage.setMsg(toMsg);
                    toChatMessage.setType(ChatMessage.Type.OUTCOMING);
                    mDatas.add(toChatMessage);
                    mAdater.notifyDataSetChanged();

                    ed_input.setText("");//清空输入框文本
                    lv_msgs.setSelection(mAdater.getCount() - 1);//让listView始终显示最底下的数据，也就是实时显示最新消息，防止键盘的遮盖

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ChatMessage fromChatMessage = HttpUtils.sendMessage(toMsg);
                            Message message = Message.obtain();
                            message.obj = fromChatMessage;
                            mHandler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });

        back_ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好，为您服务", ChatMessage.Type.INCOMING, new Date()));
        //        mDatas.add(new ChatMessage("你好", ChatMessage.Type.OUTCOMING, new Date()));
        mAdater = new ChatMessageAdapter(this, mDatas);

        lv_msgs.setAdapter(mAdater);
    }

    private void initView() {
        lv_msgs = findViewById(R.id.lv_msgs);
        ed_input = findViewById(R.id.et_input_msg);
        btn_send = findViewById(R.id.btn_send_msg);
        back_ibt = findViewById(R.id.kf_back_bt);

    }

    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
