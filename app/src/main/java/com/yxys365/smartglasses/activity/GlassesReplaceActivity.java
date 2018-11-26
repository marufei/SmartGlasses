package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.BaseEntity;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.entity.RemindEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;

import java.util.HashMap;
import java.util.Map;

public class GlassesReplaceActivity extends BaseActivity implements View.OnClickListener {

    private TextView glasses_replace_num;
    private Button glasses_replace_sure;
    private String TAG = "GlassesReplaceActivity";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glasses_replace);
        setTitle("更换目镜");
        setBack(true);
        ToolBarStyle(1);
        initView();
        initData();
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 0);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, GlassesReplaceActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        glasses_replace_num = findViewById(R.id.glasses_replace_num);
        glasses_replace_num.setOnClickListener(this);
        glasses_replace_sure = findViewById(R.id.glasses_replace_sure);
        glasses_replace_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.glasses_replace_num:
                DialogBottomView dialogBottomView = new DialogBottomView(GlassesReplaceActivity.this, R.layout.dialog_wheel_one, "更换目镜", 1);
                dialogBottomView.setData(Const.glasses);
                dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
                    @Override
                    public void onSure(String item1, String item2, String item3) {
                        if (!TextUtils.isEmpty(item2)) {
                            glasses_replace_num.setText(item2);
                        } else {
                            glasses_replace_num.setText(Const.glasses[0]);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialogBottomView.showDialog();
                break;
            case R.id.glasses_replace_sure:
                if (!TextUtils.isEmpty(glasses_replace_num.getText().toString())) {
                    glassesChange();
                } else {
                    MyUtils.showToast(this, "请先选择目镜");
                }
                break;
        }
    }

    /**
     * 更换目镜
     */
    public void glassesChange() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.GLASSES_CHANGE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "glassesChange()--response:" + response);
                try {
                    Gson gson = new Gson();
                    BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                    if (baseEntity.getCode() == 0) {
                        if (type == 1) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                        }
                        MyUtils.showToast(GlassesReplaceActivity.this, "更换目镜成功");
                        finish();
                    } else {
                        VolleyUtils.dealErrorStatus(GlassesReplaceActivity.this, baseEntity.getCode(), baseEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(GlassesReplaceActivity.this, "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("glass_number", glasses_replace_num.getText().toString().trim());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(GlassesReplaceActivity.this).addToRequestQueue(stringRequest);
    }
}
