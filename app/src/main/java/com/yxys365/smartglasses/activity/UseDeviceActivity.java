package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
import com.yxys365.smartglasses.adapter.UseHistoryRvAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.UseHistoryEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseDeviceActivity extends BaseActivity {

    private RecyclerView use_device_rv;
    private UseHistoryRvAdapter adapter;
    private String TAG = "UseDeviceActivity";
    private int page = 1;
    private TextView use_device_total;
    private SmartRefreshLayout use_device_rl;
    private List<UseHistoryEntity.RecordsBean> listData = new ArrayList<>();
    private RefreshLayout myRefreshLayout;
    private RefreshLayout myLoadMoreLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_device);
        setTitle("历史佩戴情况");
        setBack(true);
        ToolBarStyle(1);
        initData();
        initViews();
        initListener();
    }

    private void initListener() {
        use_device_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                myRefreshLayout = refreshlayout;
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                listData.clear();
                page = 1;
                getHistoryData();
            }
        });
        use_device_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                myLoadMoreLayout = refreshlayout;
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                page++;
                getHistoryData();
            }
        });
    }

    private void initData() {
        getHistoryData();
    }

    private void initViews() {
        use_device_rl = findViewById(R.id.use_device_rl);
        use_device_rv = findViewById(R.id.use_device_rv);
        adapter = new UseHistoryRvAdapter(UseDeviceActivity.this);
        use_device_rv.setAdapter(adapter);
        use_device_rv.addItemDecoration(new RecycleViewDivider(UseDeviceActivity.this, LinearLayoutManager.VERTICAL));
        use_device_rv.setLayoutManager(new LinearLayoutManager(UseDeviceActivity.this));
        use_device_rv.setItemAnimator(new DefaultItemAnimator());

        use_device_total = findViewById(R.id.use_device_total);
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UseDeviceActivity.class);
        context.startActivity(intent);
    }

    public void getHistoryData() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.USE_HISTORY + "?page=" + page + "&per_page=10" + "&device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    UseHistoryEntity useHistoryEntity = gson.fromJson(response, UseHistoryEntity.class);
                    if (useHistoryEntity.getCode() == 0) {
                        use_device_total.setText(String.valueOf(useHistoryEntity.getTotal()));
                        listData.addAll(useHistoryEntity.getRecords());
                        adapter.setList(listData);
                        adapter.notifyDataSetChanged();
                        if (listData.size() == 0) {
                            MyUtils.showToast(UseDeviceActivity.this, "暂无历史佩戴记录");

                        }
                        if (myLoadMoreLayout != null) {
                            if (useHistoryEntity.getRecords().size() == 0) {
                                MyUtils.showToast(UseDeviceActivity.this, "没有更多数据了");
                                page--;
                            }
                            myLoadMoreLayout.finishLoadMore(true);
                        }
                        if (myRefreshLayout != null) {
                            myRefreshLayout.finishRefresh(true);
                        }
                    } else {
                        VolleyUtils.dealErrorStatus(UseDeviceActivity.this, useHistoryEntity.getCode(), useHistoryEntity.getMsg());
                        if (myLoadMoreLayout != null) {
                            myLoadMoreLayout.finishLoadMore(false);
                        }
                        if (myRefreshLayout != null) {
                            myRefreshLayout.finishRefresh(false);
                        }
                    }
                } catch (Exception e) {
                    if (myLoadMoreLayout != null) {
                        myLoadMoreLayout.finishLoadMore(false);
                    }
                    if (myRefreshLayout != null) {
                        myRefreshLayout.finishRefresh(false);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UseDeviceActivity.this, "网络有问题");
                if (myLoadMoreLayout != null) {
                    myLoadMoreLayout.finishLoadMore(false);
                }
                if (myRefreshLayout != null) {
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
        VolleyUtils.getInstance(UseDeviceActivity.this).addToRequestQueue(stringRequest);
    }

}
