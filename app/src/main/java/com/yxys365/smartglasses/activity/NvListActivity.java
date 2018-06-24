package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.NvListLvAdapter;

/**
 * TODO 历史裸眼视力表
 */
public class NvListActivity extends BaseActivity {

    private ListView nv_list_lv;
    private NvListLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv_list);
        setTitle("历史裸眼视力");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
        nv_list_lv=findViewById(R.id.nv_list_lv);
        adapter=new NvListLvAdapter(this);
        nv_list_lv.setAdapter(adapter);
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NvListActivity.class);
        context.startActivity(intent);
    }
}
