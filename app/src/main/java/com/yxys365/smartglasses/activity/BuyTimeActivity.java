package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.BuyTimeRvAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.BuyTimeEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyTimeActivity extends BaseActivity {

    private RecyclerView buy_time_rv;
    private BuyTimeRvAdapter adapter;
    private String TAG = "BuyTimeActivity";
    private int page = 1;
    private List<BuyTimeEntity.RecordsBean> listData=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private SmartRefreshLayout buy_time_rl;
    private RefreshLayout myRefreshLayout;
    private RefreshLayout myLoadMoreLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_time);
        setTitle("购置时间管理表");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas();
        initListener();
    }

    private void initListener() {
        buy_time_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                myRefreshLayout=refreshlayout;
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                listData.clear();
                page=1;
                getBuyTimeList();
            }
        });
        buy_time_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                myLoadMoreLayout=refreshlayout;
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                page++;
                getBuyTimeList();
            }
        });
    }

    private void initDatas() {
        getBuyTimeList();
    }

    private void initViews() {
        buy_time_rl=findViewById(R.id.buy_time_rl);
        buy_time_rv = findViewById(R.id.buy_time_rv);
        buy_time_rv.addItemDecoration(new RecycleViewDivider(BuyTimeActivity.this, LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(BuyTimeActivity.this);
        buy_time_rv.setLayoutManager(layoutManager);
        buy_time_rv.setItemAnimator(new DefaultItemAnimator());

        adapter = new BuyTimeRvAdapter(BuyTimeActivity.this);
        buy_time_rv.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BuyTimeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 获取数据源
     */
    public void getBuyTimeList() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.BUY_TIME + "?page=" + page + "&per_page=10" + "&device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    BuyTimeEntity buyTimeEntity = gson.fromJson(response, BuyTimeEntity.class);
                    if (buyTimeEntity.getCode() == 0) {
                        listData.addAll(buyTimeEntity.getRecords());
                        adapter.setList(listData);
                        adapter.notifyDataSetChanged();
                        if(listData.size()==0){
                            MyUtils.showToast(BuyTimeActivity.this,"暂无购置时间记录");

                        }
                        if(myLoadMoreLayout!=null) {
                            if(buyTimeEntity.getRecords().size()==0){
                                MyUtils.showToast(BuyTimeActivity.this,"没有更多数据了");
                                page--;
                            }
                            myLoadMoreLayout.finishLoadMore(true);
                        }
                        if(myRefreshLayout!=null) {
                            myRefreshLayout.finishRefresh(true);
                        }

                    } else {
                        VolleyUtils.dealErrorStatus(BuyTimeActivity.this, buyTimeEntity.getCode(), buyTimeEntity.getMsg());
                        if(myLoadMoreLayout!=null) {
                            myLoadMoreLayout.finishLoadMore(false);
                        }
                        if(myRefreshLayout!=null) {
                            myRefreshLayout.finishRefresh(false);
                        }
                    }
                } catch (Exception e) {
                    if(myLoadMoreLayout!=null) {
                        myLoadMoreLayout.finishLoadMore(false);
                    }
                    if(myRefreshLayout!=null) {
                        myRefreshLayout.finishRefresh(false);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(BuyTimeActivity.this, "网络有问题");
                if(myLoadMoreLayout!=null) {
                    myLoadMoreLayout.finishLoadMore(false);
                }
                if(myRefreshLayout!=null) {
                    myRefreshLayout.finishRefresh(false);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(BuyTimeActivity.this).addToRequestQueue(stringRequest);
    }
}
