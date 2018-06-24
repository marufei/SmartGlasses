package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxys365.smartglasses.R;

public class ExpreciseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exprecise);
        setTitle("锻炼情况记录表");
        setBack(true);
        ToolBarStyle(1);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ExpreciseActivity.class);
        context.startActivity(intent);
    }
}
