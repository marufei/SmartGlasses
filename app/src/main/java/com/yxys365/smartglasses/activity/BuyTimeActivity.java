package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.BuyTimeLvAdapter;

public class BuyTimeActivity extends BaseActivity {

    private ListView buy_time_lv;
    private BuyTimeLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_time);
        setTitle("购置时间管理表");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
        buy_time_lv=findViewById(R.id.buy_time_lv);
        adapter=new BuyTimeLvAdapter(this);
        buy_time_lv.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BuyTimeActivity.class);
        context.startActivity(intent);
    }
}
