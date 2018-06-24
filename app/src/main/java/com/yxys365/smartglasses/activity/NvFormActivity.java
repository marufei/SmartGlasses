package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxys365.smartglasses.R;

public class NvFormActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv_form);
        setTitle("裸眼视力记录表");
        setBack(true);
        ToolBarStyle(1);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NvFormActivity.class);
        context.startActivity(intent);
    }
}
