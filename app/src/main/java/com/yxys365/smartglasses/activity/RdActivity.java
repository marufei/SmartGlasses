package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.MyTabAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.fragment.Rd1Fragment;
import com.yxys365.smartglasses.fragment.Rd2Fragment;
import com.yxys365.smartglasses.fragment.Rd3Fragment;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 屈光检测信息
 */
public class RdActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout rd_tab;
    private ViewPager rd_vp;

    /**
     * 屈光检测状况(sph:球镜,cyl:柱镜,ax:轴位)
     */
    public static String test_refraction;

    /**
     * 凹镜屈光矫正状况
     */
    public static String concave_correct_refraction;

    /**
     * 凸镜屈光矫正状况
     */
    public static String convex_correct_refraction;

    /**
     * 调节状况
     */
    public static String regulation;
    private String TAG = "RdActivity";
    private Button rd_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd);
        setTitle("屈光检测信息");
        setBack(true);
        ToolBarStyle(1);
        setMenu("跳过", R.color.white);
        initViews();
        initDatas();
    }

    private void initDatas() {
        //装载Fragment
        List<Fragment> fragmentList = new ArrayList<>();
        Rd1Fragment rdFragment1 = new Rd1Fragment();
        Rd2Fragment rdFragment2 = new Rd2Fragment();
        Rd3Fragment rdFragment3 = new Rd3Fragment();

        fragmentList.add(rdFragment1);
        fragmentList.add(rdFragment2);
        fragmentList.add(rdFragment3);


        //装载 标题
        List<String> titleList = new ArrayList<>();
        titleList.add("屈光检测状况");
        titleList.add("屈光矫正状况");
        titleList.add("调节状况");

        //设置TabLayout的模式
        rd_tab.setTabMode(TabLayout.MODE_FIXED);
        rd_tab.addTab(rd_tab.newTab().setText(titleList.get(0)));
        rd_tab.addTab(rd_tab.newTab().setText(titleList.get(1)));
        rd_tab.addTab(rd_tab.newTab().setText(titleList.get(2)));

        MyTabAdapter adapter = new MyTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        rd_vp.setAdapter(adapter);
        rd_vp.setOffscreenPageLimit(1);
        rd_tab.setupWithViewPager(rd_vp);
    }

    private void initViews() {
        rd_tab = findViewById(R.id.rd_tab);
        rd_vp = findViewById(R.id.rd_vp);
        rd_next=findViewById(R.id.rd_next);
        rd_next.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                ServiceInfoActivity.start(RdActivity.this);
                break;
            case R.id.rd_next:
                if(TextUtils.isEmpty(test_refraction)&&TextUtils.isEmpty(concave_correct_refraction)&&TextUtils.isEmpty(convex_correct_refraction)&&TextUtils.isEmpty(regulation)) {
                    ServiceInfoActivity.start(RdActivity.this);
                }else {
//                    if (TextUtils.isEmpty(test_refraction)) {
//                        MyUtils.showToast(RdActivity.this, "请先填写选择屈光检测状况表");
//                        return;
//                    } else {
//                        if (test_refraction.contains("null")) {
//                            MyUtils.showToast(RdActivity.this, "请先完善选择屈光检测状况表");
//                            return;
//                        }
//                    }
//                    if (TextUtils.isEmpty(concave_correct_refraction) && TextUtils.isEmpty(convex_correct_refraction)) {
//                        MyUtils.showToast(RdActivity.this, "请先填写屈光矫正状况表");
//                        return;
//                    } else {
//
//                        MyUtils.Loge(TAG, "concave_correct_refraction:" + concave_correct_refraction);
//                        MyUtils.Loge(TAG, "convex_correct_refraction:" + convex_correct_refraction);
//                        if (!concave_correct_refraction.contains("null") || !convex_correct_refraction.contains("null")) {
////                        MyUtils.showToast(RdActivity.this, "可以请求了");
//                            if (!TextUtils.isEmpty(regulation) && !regulation.contains("null")) {
//                                getStep3Data();
//                            }else {
//                                MyUtils.showToast(RdActivity.this, "请先完善选择调节状况表");
//                                return;
//                            }
//
//                        } else {
//                            MyUtils.showToast(RdActivity.this, "请先完善屈光矫正状况表");
//                        }
//                    }


                    if(!TextUtils.isEmpty(test_refraction)&&test_refraction.contains("null")){
                        MyUtils.showToast(RdActivity.this, "请先填写选择屈光检测状况表");
                        return;
                    }

                    if(!TextUtils.isEmpty(concave_correct_refraction)&&concave_correct_refraction.contains("null")){
                        MyUtils.showToast(RdActivity.this, "请先填写屈光矫正状况表");
                        return;
                    }

                    if(!TextUtils.isEmpty(convex_correct_refraction)&&convex_correct_refraction.contains("null")){
                        MyUtils.showToast(RdActivity.this, "请先填写屈光矫正状况表");
                        return;
                    }

                    if (!TextUtils.isEmpty(regulation) && regulation.contains("null")) {
                        MyUtils.showToast(RdActivity.this, "请先填写调节状况表");
                        return;
                    }
                    getStep3Data();

                }
                break;
        }
    }

    /**
     * 请求注册第三步
     */
    public void getStep3Data(){
        String url= HttpsAddress.BASE_ADDRESS+HttpsAddress.REGISTER3;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if(code==0){
                        ServiceInfoActivity.start(RdActivity.this);
                    }else {
                        String msg=jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(RdActivity.this,code,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RdActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("register_code", SaveUtils.getString(KeyUtils.register_code));
                if(!TextUtils.isEmpty(test_refraction)&&!test_refraction.contains("null")) {
                    map.put("test_refraction", test_refraction);
                }
                if(!TextUtils.isEmpty(concave_correct_refraction)&&!concave_correct_refraction.contains("null")) {
                    map.put("concave_correct_refraction", concave_correct_refraction);
                }
                if(!TextUtils.isEmpty(convex_correct_refraction)&&!convex_correct_refraction.contains("null")) {
                    map.put("convex_correct_refraction", convex_correct_refraction);
                }
                if(!TextUtils.isEmpty(regulation)&&!regulation.contains("null")) {
                    map.put("regulation", regulation);
                }
//                MyUtils.Loge(TAG,"register_code:"+SaveUtils.getString(KeyUtils.register_code)+"--test_refraction:"+test_refraction
//                +"--concave_correct_refraction:"+concave_correct_refraction+"--convex_correct_refraction:"+convex_correct_refraction
//                +"--regulation:"+regulation);
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(RdActivity.this).addToRequestQueue(stringRequest);
    }


}
