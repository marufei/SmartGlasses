package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.ExpreciseEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

public class ExpreciseActivity extends BaseActivity {

    private String TAG = "ExpreciseActivity";
    private TextView exprecise_plan_name, exprecise_plan_time1, exprecise_plan_time2, exprecise_plan_time3, exprecise_plan_number, exprecise_plan_pj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exprecise);
        setTitle("锻炼情况记录表");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initData();
    }

    private void initData() {
        getExpriceOnce();
    }

    private void initViews() {
        exprecise_plan_name = findViewById(R.id.exprecise_plan_name);
        exprecise_plan_time1 = findViewById(R.id.exprecise_plan_time1);
        exprecise_plan_time2 = findViewById(R.id.exprecise_plan_time2);
        exprecise_plan_time3 = findViewById(R.id.exprecise_plan_time3);
        exprecise_plan_pj = findViewById(R.id.exprecise_plan_pj);
        exprecise_plan_number = findViewById(R.id.exprecise_plan_number);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ExpreciseActivity.class);
        context.startActivity(intent);
    }

    public void getExpriceOnce() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.EXPRICE_ONCE+"?device_code="+ MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    ExpreciseEntity expreciseEntity = gson.fromJson(response, ExpreciseEntity.class);
                    if (expreciseEntity.getCode() == 0) {
                        if(expreciseEntity.getWear()!=null) {
                            exprecise_plan_number.setText("目镜" + expreciseEntity.getWear().getGlass_number());
                            exprecise_plan_name.setText(expreciseEntity.getWear().getPlan_name());
                            exprecise_plan_time1.setText(expreciseEntity.getWear().getDuration_display());
                            exprecise_plan_time2.setText(expreciseEntity.getWear().getExercise_time_display());
                            exprecise_plan_time3.setText(expreciseEntity.getWear().getTotal_exercise_time());
                            exprecise_plan_pj.setText(expreciseEntity.getWear().getEvaluation());
                        }
                    } else {
                        VolleyUtils.dealErrorStatus(ExpreciseActivity.this,expreciseEntity.getCode(),expreciseEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ExpreciseActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(ExpreciseActivity.this).addToRequestQueue(stringRequest);
    }
}
