package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.UseDeviceLvAdapter;

public class UseDeviceActivity extends BaseActivity {

    private ListView use_device_lv;
    private UseDeviceLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_device);
        setTitle("历史佩戴情况");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
        use_device_lv = findViewById(R.id.use_device_lv);
        adapter = new UseDeviceLvAdapter(this);
        use_device_lv.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UseDeviceActivity.class);
        context.startActivity(intent);
    }

}
