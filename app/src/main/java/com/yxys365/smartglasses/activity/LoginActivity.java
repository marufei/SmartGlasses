package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxys365.smartglasses.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TextView login_register;
    private TextView login_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        login_register=findViewById(R.id.login_register);
        login_register.setOnClickListener(this);


        login_forget=findViewById(R.id.login_forget);
        login_forget.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_register:
                UserInfoActivity.start(this);
                break;
            case R.id.login_forget:
                ForgetPwdActivity.start(this);
                break;
        }
    }
}
