package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.yxys365.smartglasses.adapter.OcularRvAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.OcularEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OcularActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView ocular_rv;
    private OcularRvAdapter adapter;
    private int page = 1;
    private String TAG = "OcularActivity";
    private SmartRefreshLayout ocular_rl;
    private RefreshLayout myRefreshLayout;
    private RefreshLayout myLoadMoreLayout;
    private List<OcularEntity.RecordsBean> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocular);
        setTitle("目镜管理表");
        setBack(true);
        ToolBarStyle(1);
        setMenu("更换",R.color.white);
        initViews();
        initData();
        initListener();
    }

    private void initListener() {
        ocular_rl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                myRefreshLayout = refreshlayout;
                listData.clear();
                page = 1;
                getOcularData();
            }
        });
        ocular_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                myLoadMoreLayout = refreshlayout;
                page++;
                getOcularData();
            }
        });
    }

    private void initData() {
        getOcularData();
    }

    private void initViews() {
        ocular_rl = findViewById(R.id.ocular_rl);
        ocular_rv = findViewById(R.id.ocular_rv);
        adapter = new OcularRvAdapter(OcularActivity.this);
        ocular_rv.setAdapter(adapter);
        ocular_rv.addItemDecoration(new RecycleViewDivider(OcularActivity.this, LinearLayoutManager.VERTICAL));
        ocular_rv.setLayoutManager(new LinearLayoutManager(OcularActivity.this));
        ocular_rv.setItemAnimator(new DefaultItemAnimator());
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OcularActivity.class);
        context.startActivity(intent);
    }

    public void getOcularData() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.MY_OCULAR + "?page=" + page + "&per_page=10" + "&device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    OcularEntity ocularEntity = gson.fromJson(response, OcularEntity.class);
                    if (ocularEntity.getCode() == 0) {
                        listData.addAll(ocularEntity.getRecords());
                        adapter.setList(listData);
                        adapter.notifyDataSetChanged();

                        if(listData.size()==0){
                            MyUtils.showToast(OcularActivity.this,"暂无更换目镜记录");

                        }
                        if(myLoadMoreLayout!=null) {
                            if(ocularEntity.getRecords().size()==0){
                                MyUtils.showToast(OcularActivity.this,"没有更多数据了");
                                page--;
                            }
                            myLoadMoreLayout.finishLoadMore(true);
                        }
                        if(myRefreshLayout!=null) {
                            myRefreshLayout.finishRefresh(true);
                        }

                    } else {
                        VolleyUtils.dealErrorStatus(OcularActivity.this, ocularEntity.getCode(), ocularEntity.getMsg());
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
                MyUtils.showToast(OcularActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(OcularActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
//                GlassesReplaceActivity.start(this);
                Intent intent=new Intent(this,GlassesReplaceActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,0x01);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x01&&resultCode==RESULT_OK){
            listData.clear();
            page = 1;
            getOcularData();
        }
    }
}
