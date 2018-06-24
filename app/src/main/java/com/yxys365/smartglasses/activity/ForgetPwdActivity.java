package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxys365.smartglasses.R;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        setTitle("忘记密码");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
