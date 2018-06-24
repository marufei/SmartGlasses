package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

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

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener ,View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("title");
        setBack(false);
        setMenu(R.mipmap.connect_y,"已连接");
        initViews();
        initeven();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void initViews() {
//        operate = findViewById(R.id.operate);
//        operate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//////                byte [] bytes= Codeutil.hexStringToByte("5a5a00ee001314151617202122232425262727");
//////                byte [] aaa= SecurityUtil.encryptJNI(bytes);
////                Log.e(TAG, "------点击了按钮");
////
////                String str = "00FF067071727374E3E3E3E3E3E37575";
//////                str = str.substring(4, str.length() - 2);
////
////        String str1 = str.substring(0, str.length() / 2);
////                String str2 = str.substring(str.length() / 2, str.length());
////                Log.e(TAG, "---str:" + str);
////                Log.e(TAG, "---str1:" + str1);
////                Log.e(TAG, "---str2:" + str2);
////
////                String s = "5A5A" + Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str1))) +
////                        Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str2)));
////                s = s + Codeutil.getNum(s);
////
////                Log.e(TAG, "------s:" + s);
//
//                String str = "5a5a00ee001314151617202122232425262727";  //5A5A8A37FA67FAC9A768ED3389B4DFCA8276A6
//                str = str.substring(4, str.length() - 2);
//                String str1 = str.substring(0, str.length() / 2);
//                String str2 = str.substring(str.length() / 2, str.length());
//                String s = Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str1))) +
//                        Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str2)));
//                Log.e(TAG, "----s:" + s);
//            }
//        });

        vp_show = findViewById(R.id.vp_show);
        rb0 = findViewById(R.id.rb0);
        rb1 = findViewById(R.id.rb1);
        rb3 = findViewById(R.id.rb3);
        rb4=findViewById(R.id.rb4);
        ll_bottom = findViewById(R.id.ll_bottom);
        rg_bottom = findViewById(R.id.rg_bottom);
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
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_LONG).show();
            showToast = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu:
                break;
        }
    }
}
