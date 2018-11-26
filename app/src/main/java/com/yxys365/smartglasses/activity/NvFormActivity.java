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
import com.yxys365.smartglasses.entity.NvFormEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

public class NvFormActivity extends BaseActivity {

    private TextView nv_num_left, nv_num_right, nv_num_double;
    private TextView nv_version_left, nv_version_right, nv_version_double;
    private String TAG = "NvFormActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv_form);
        setTitle("裸眼视力记录表");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas();
    }

    private void initDatas() {
        getVersionTable();
    }

    private void initViews() {
        nv_num_left = findViewById(R.id.nv_num_left);
        nv_num_right = findViewById(R.id.nv_num_right);
        nv_num_double = findViewById(R.id.nv_num_double);
        nv_version_left = findViewById(R.id.nv_version_left);
        nv_version_right = findViewById(R.id.nv_version_right);
        nv_version_double = findViewById(R.id.nv_version_double);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NvFormActivity.class);
        context.startActivity(intent);
    }

    /**
     * 获取收据
     */
    public void getVersionTable() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.VERSION + "?device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    NvFormEntity nvFormEntity = gson.fromJson(response, NvFormEntity.class);
                    if (nvFormEntity.getCode() == 0) {
                        if (nvFormEntity.getVision() != null) {
                            nv_version_left.setText(nvFormEntity.getVision().getLeft_vision());
                            nv_version_right.setText(nvFormEntity.getVision().getRight_vision());
                            nv_version_double.setText(nvFormEntity.getVision().getDouble_vision());
                            nv_num_left.setText(nvFormEntity.getVision().getLeft_num());
                            nv_num_right.setText(nvFormEntity.getVision().getRight_num());
                            nv_num_double.setText(nvFormEntity.getVision().getDouble_num());
                        }
                    } else {
                        VolleyUtils.dealErrorStatus(NvFormActivity.this, nvFormEntity.getCode(), nvFormEntity.getMsg());
                    }

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(NvFormActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(NvFormActivity.this).addToRequestQueue(stringRequest);
    }
}
