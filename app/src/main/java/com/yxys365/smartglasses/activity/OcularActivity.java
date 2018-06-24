package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.OcularLvAdapter;

public class OcularActivity extends BaseActivity {

    private ListView ocular_lv;
    private OcularLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocular);
        setTitle("目镜管理表");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
        ocular_lv=findViewById(R.id.ocular_lv);
        adapter=new OcularLvAdapter(this);
        ocular_lv.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OcularActivity.class);
        context.startActivity(intent);
    }
}
