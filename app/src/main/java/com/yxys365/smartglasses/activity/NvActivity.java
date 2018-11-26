package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.MyTabAdapter;
import com.yxys365.smartglasses.fragment.Nv2Fragment;
import com.yxys365.smartglasses.fragment.NvFragment;
import com.yxys365.smartglasses.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 裸眼视力
 */
public class NvActivity extends BaseActivity {

    private TabLayout nv_tab;
    private ViewPager nv_vp;
    public String TAG = "NvActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv);
        setTitle("用户信息");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas();
        initEvent();
    }

    private void initEvent() {

//        nv_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                MyUtils.Loge(TAG,"position:"+position);
//                switch (position) {
//                    case 0:
//                        vision_type = "1";
//                        break;
//                    case 1:
//                        vision_type = "2";
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private void initDatas() {
        //装载Fragment
        List<Fragment> fragmentList = new ArrayList<>();
        NvFragment nvFragment = new NvFragment();
        Nv2Fragment nvFragment2 = new Nv2Fragment();
        fragmentList.add(nvFragment);
        fragmentList.add(nvFragment2);


        //装载 标题
        List<String> titleList = new ArrayList<>();
        titleList.add("远距离裸眼视力");
        titleList.add("老花眼近距离裸眼视力");

        //设置TabLayout的模式
        nv_tab.setTabMode(TabLayout.MODE_FIXED);
        nv_tab.addTab(nv_tab.newTab().setText(titleList.get(0)));
        nv_tab.addTab(nv_tab.newTab().setText(titleList.get(1)));

        MyTabAdapter adapter = new MyTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        nv_vp.setAdapter(adapter);
        nv_vp.setOffscreenPageLimit(1);
        nv_tab.setupWithViewPager(nv_vp);
    }

    private void initViews() {
        nv_tab = findViewById(R.id.nv_tab);
        nv_vp = findViewById(R.id.nv_vp);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NvActivity.class);
        context.startActivity(intent);
    }
}
