package com.example.storedemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends Activity {

    private UserDAO userDAO;
    private boolean islogin;
    private EditText musername,mpassword;
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }
        if (ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return;
        }

        musername=(EditText)findViewById(R.id.et_loginactivity_username);
        mpassword=(EditText)findViewById(R.id.et_loginactivity_password);
        register=(TextView)findViewById(R.id.tv_loginactivity_register);
        login=(Button)findViewById(R.id.bt_loginactivity_login);

        userDAO = new UserDAO(this);
        SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
        islogin=sp.getBoolean("isLogin",false);

        //如果已经登录过自动进入主页面
        if (islogin==true){

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(LoginActivity.this,"您已登录过！自动进入应用！",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();//销毁此Activity
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jname = musername.getText().toString().trim();
                String jpassword = mpassword.getText().toString().trim();
                if (!TextUtils.isEmpty(jname) && !TextUtils.isEmpty(jpassword)) {
                    List<User> data = userDAO.SelectAllFromUser();
                    boolean match = false;
                    for(int i=0;i<data.size();i++) {
                        User user = data.get(i);
                        if (jname.equals(user.getName()) && jpassword.equals(user.getPassword())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //存放登录信息
                        SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", jname);
                        editor.putString("password", jpassword);
                        editor.putBoolean("isLogin",true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    }else {
                        Toast.makeText(LoginActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
