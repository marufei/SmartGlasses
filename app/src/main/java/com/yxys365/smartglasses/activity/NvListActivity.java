package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yxys365.smartglasses.adapter.NvRvAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.VisionHistoryEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 历史裸眼视力表
 */
public class NvListActivity extends BaseActivity {

    private RecyclerView nv_list_rv;
    private NvRvAdapter adapter;


    private List<VisionHistoryEntity.RecordsBean> list=new ArrayList<>();
    /**
     * 当前页
     */
    private int page = 1;
    private String TAG = "NvListActivity";
    private TextView nv_list_total;
    private SmartRefreshLayout nv_list_rl;
    private RefreshLayout myRefreshLayout;
    private RefreshLayout myLoadMoreLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv_list);
        setTitle("历史裸眼视力");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initData();
        initListener();
    }

    private void initListener() {
        nv_list_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                myRefreshLayout=refreshlayout;
                list.clear();
                page=1;
                getNvData();
            }
        });
        nv_list_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                myLoadMoreLayout=refreshlayout;
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                page++;
                getNvData();
            }
        });
    }

    private void initData() {
        getNvData();
    }

    private void initViews() {
        nv_list_rl=findViewById(R.id.nv_list_rl);
        nv_list_rv = findViewById(R.id.nv_list_rv);
        adapter = new NvRvAdapter(NvListActivity.this);
        nv_list_rv.setAdapter(adapter);
        nv_list_rv.addItemDecoration(new RecycleViewDivider(NvListActivity.this, LinearLayoutManager.VERTICAL));
        nv_list_rv.setLayoutManager(new LinearLayoutManager(NvListActivity.this));
        nv_list_rv.setItemAnimator(new DefaultItemAnimator());
        nv_list_total=findViewById(R.id.nv_list_total);

    }



    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NvListActivity.class);
        context.startActivity(intent);
    }

    /**
     * 获取每页数据
     */
    public void getNvData() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.VERSION_HISTORY + "?page=" + page+"&per_page=10"+"&device_code="+ MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    VisionHistoryEntity nvFormEntity = gson.fromJson(response, VisionHistoryEntity.class);

                    if (nvFormEntity.getCode() == 0) {
                        nv_list_total.setText("总计："+nvFormEntity.getTotal()+"条");
                        list.addAll(nvFormEntity.getRecords());
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                        if(list.size()==0){
                            MyUtils.showToast(NvListActivity.this,"暂无历史裸眼视力记录");

                        }
                        if(myLoadMoreLayout!=null) {
                            if(nvFormEntity.getRecords().size()==0){
                                MyUtils.showToast(NvListActivity.this,"没有更多数据了");
                                page--;
                            }
                            myLoadMoreLayout.finishLoadMore(true);
                        }
                        if(myRefreshLayout!=null) {
                            myRefreshLayout.finishRefresh(true);
                        }


                    } else {
                        VolleyUtils.dealErrorStatus(NvListActivity.this,nvFormEntity.getCode(),nvFormEntity.getMsg());
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
                MyUtils.showToast(NvListActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(NvListActivity.this).addToRequestQueue(stringRequest);
    }

}
