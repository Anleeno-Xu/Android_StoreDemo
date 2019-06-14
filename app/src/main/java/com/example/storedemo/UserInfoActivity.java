package com.example.storedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class UserInfoActivity extends Activity {

    TextView username;
    EditText password, nick;
    Button commit;
    ImageView icon;
    String username1, password1, icon_path1;
    UserDAO userDAO;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        username = findViewById(R.id.username_up_tv);
        password = findViewById(R.id.password_up_et);
        nick = findViewById(R.id.nick_up_et);
        icon = findViewById(R.id.icon_up_iv);
        commit = findViewById(R.id.commit_info_bt);
        userDAO = new UserDAO(UserInfoActivity.this);

        SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        username1 = sp.getString("username", "noname");
        password1 = sp.getString("password", "000000");

        user = userDAO.SelectUserByName(username1);

        username.setText(user.getName());
        password.setText(user.getPassword());
        nick.setText(user.getNick_name());
        Glide.with(UserInfoActivity.this)
                .load(user.getIcon())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 0x115);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password11 = password.getText().toString();
                String nick11 = nick.getText().toString();
                String iconpath11 = icon_path1;
                if (!TextUtils.isEmpty(password11)) {
                    if (!TextUtils.isEmpty(nick11)) {
                        if (!TextUtils.isEmpty(iconpath11)) {
                            userDAO.update(password11,nick11,iconpath11,username1);
                            Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UserInfoActivity.this, "请选择头像！", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(UserInfoActivity.this, "请填写昵称！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(UserInfoActivity.this, "请填写密码！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x115) {
            if (data != null) {
                icon_path1 = data.getData().toString();
                //micon.setImageURI(Uri.parse(icon_path));
                Glide.with(UserInfoActivity.this)
                        .load(icon_path1)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(icon);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
