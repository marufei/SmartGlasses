package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.MainVpAdapter;
import com.yxys365.smartglasses.fragment.HomeFragment;
import com.yxys365.smartglasses.fragment.LyFragment;
import com.yxys365.smartglasses.fragment.MineFragment;
import com.yxys365.smartglasses.fragment.PdFragment;
import com.yxys365.smartglasses.utils.Codeutil;
import com.yxys365.smartglasses.utils.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CheckPermissionsActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private Button operate;
    private String TAG = "MainActivity";
    private ViewPager vp_show;
    private RadioButton rb0;
    private RadioButton rb1;
    private RadioButton rb3;
    private LinearLayout ll_bottom;
    private RadioGroup rg_bottom;
    private HomeFragment fragment1;
    private LyFragment fragment2;
    private MineFragment fragment4;
    private PdFragment fragment3;
    private List<Fragment> listFragnet = new ArrayList<>();

    long[] mHits = new long[2];
    boolean showToast = true;
    private RadioButton rb4;
    private static TextView home_menu;
    public static Handler mHandler = new Handler() {
        /**
         * handleMessage接收消息后进行相应的处理
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                home_menu.setText("已连接");
                Drawable connect_y = mainActivity.getResources().getDrawable(R.mipmap.connect_y);
                connect_y.setBounds(0, 0, connect_y.getMinimumWidth(), connect_y.getMinimumHeight());
                home_menu.setCompoundDrawables(null, null, connect_y, null);
            } else {
                home_menu.setText("未连接");
                Drawable connect_n = mainActivity.getResources().getDrawable(R.mipmap.connect_n);
                connect_n.setBounds(0, 0, connect_n.getMinimumWidth(), connect_n.getMinimumHeight());
                home_menu.setCompoundDrawables(null, null, connect_n, null);
            }
        }
    };
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermission();
        setDarkStatusIcon(true);
        mainActivity=this;
        initViews();
        initeven();
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initViews() {
        vp_show = findViewById(R.id.vp_show);
        rb0 = findViewById(R.id.rb0);
        rb1 = findViewById(R.id.rb1);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        ll_bottom = findViewById(R.id.ll_bottom);
        rg_bottom = findViewById(R.id.rg_bottom);

        home_menu = findViewById(R.id.home_menu);
    }

    private void initeven() {

        //设置主页第一个分页被选中
        rb0.setSelected(true);
        rb0.setChecked(true);

        fragment1 = new HomeFragment();
        fragment2 = new LyFragment();
        fragment3 = new PdFragment();
        fragment4 = new MineFragment();

        listFragnet.add(fragment1);
        listFragnet.add(fragment2);
        listFragnet.add(fragment3);
        listFragnet.add(fragment4);

        rg_bottom.setOnCheckedChangeListener(this);
        vp_show.setAdapter(new MainVpAdapter(getSupportFragmentManager()));
        vp_show.setCurrentItem(0);
        vp_show.setOffscreenPageLimit(3);
        vp_show.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (vp_show.getCurrentItem()) {
                case 0:
                    rb0.setChecked(true);
                    break;
                case 1:
                    rb1.setChecked(true);
                    break;
                case 2:
                    rb3.setChecked(true);
                    break;
                case 3:
                    rb4.setChecked(true);
                    break;
//                case 4:
//                    rb4.setChecked(true);
//                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb0:
                vp_show.setCurrentItem(0);
                break;
            case R.id.rb1:
                vp_show.setCurrentItem(1);
                break;
            case R.id.rb3:
                vp_show.setCurrentItem(2);
                break;
            case R.id.rb4:
                vp_show.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);// 数组向左移位操作
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {
            MyApplication.AppExit();
            finish();
        } else {
            showToast = true;
        }
        if (showToast) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            showToast = false;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }
}
