package com.example.storedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends Activity {

    private String realCode;
    private UserDAO userDAO;
    String icon_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        userDAO = new UserDAO(this);
        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

    }


    @BindView(R.id.rl_registeractivity_top)
    RelativeLayout mRlRegisteractivityTop;
    @BindView(R.id.iv_registeractivity_back)
    ImageView mIvRegisteractivityBack;
    @BindView(R.id.ll_registeractivity_body)
    LinearLayout mLlRegisteractivityBody;
    @BindView(R.id.et_registeractivity_username)
    EditText mEtRegisteractivityUsername;
    @BindView(R.id.et_registeractivity_password1)
    EditText mEtRegisteractivityPassword1;
    @BindView(R.id.et_registeractivity_password2)
    EditText mEtRegisteractivityPassword2;
    @BindView(R.id.et_registeractivity_phoneCodes)
    EditText mEtRegisteractivityPhonecodes;
    @BindView(R.id.iv_registeractivity_showCode)
    ImageView mIvRegisteractivityShowcode;
    @BindView(R.id.rl_registeractivity_bottom)
    RelativeLayout mRlRegisteractivityBottom;
    @BindView(R.id.et_registeractivity_nickname)
    EditText mnickname;
    @BindView(R.id.re_icon_iv)
    ImageView micon;

    /**
     * 注册页面能点击的就三个地方
     * top处返回箭头、刷新验证码图片、注册按钮
     */
    @OnClick({
            R.id.iv_registeractivity_back,
            R.id.iv_registeractivity_showCode,
            R.id.bt_registeractivity_register,
            R.id.re_icon_iv
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                String nickname = mnickname.getText().toString().trim();
                //注册验证
                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password2)
                        && !TextUtils.isEmpty(phoneCode)
                        && !TextUtils.isEmpty(password1)
                        && !TextUtils.isEmpty(nickname)
                        && !TextUtils.isEmpty(icon_path)) {
                    if (phoneCode.equals(realCode)) {
                        if (password1.equals(password2)) {
                            List<User> data = userDAO.SelectAllFromUser();
                            boolean match = false;
                            for (int i = 0; i < data.size(); i++) {
                                User user = data.get(i);
                                if (username.equals(user.getName())) {
                                    match = true;
                                    break;
                                } else {
                                    match = false;
                                }
                            }
                            if (match) {
                                Toast.makeText(this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                            } else {
                                //将用户名和密码加入到数据库中
                                userDAO.Insert_User(new User(username, password1, nickname, icon_path));
                                Intent intent2 = new Intent(this, LoginActivity.class);
                                startActivity(intent2);
                                finish();
                                Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(this, "密码不一致,请重新确认", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.re_icon_iv:
                //选择插入图片
                micon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        //intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, 0x112);
                    }
                });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x112) {
            if (data != null) {
                icon_path = data.getData().toString();
                micon.setImageURI(Uri.parse(icon_path));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
