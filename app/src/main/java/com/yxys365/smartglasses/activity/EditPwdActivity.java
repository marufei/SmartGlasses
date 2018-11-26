package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.BaseEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditPwdActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_phone;
    private EditText edit_code;
    private EditText edit_pwd1;
    private EditText edit_pwd2;
    private Button edit_sure;
    private String TAG = "EditPwdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);
        setTitle("修改密码");
        setBack(true);
        ToolBarStyle(1);
        initViews();
    }

    private void initViews() {
        edit_phone = findViewById(R.id.edit_phone);
        edit_code = findViewById(R.id.edit_code);
        edit_pwd1 = findViewById(R.id.edit_pwd1);
        edit_pwd2 = findViewById(R.id.edit_pwd2);
        edit_sure = findViewById(R.id.edit_sure);
        edit_sure.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, EditPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_sure:
                if (TextUtils.isEmpty(edit_phone.getText().toString())) {
                    MyUtils.showToast(EditPwdActivity.this, "请先输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(edit_code.getText().toString())) {
                    MyUtils.showToast(EditPwdActivity.this, "请先输入证件号");
                    return;
                }
                if (TextUtils.isEmpty(edit_pwd1.getText().toString())) {
                    MyUtils.showToast(EditPwdActivity.this, "请先输入密码");
                    return;
                }
                if (TextUtils.isEmpty(edit_pwd2.getText().toString())) {
                    MyUtils.showToast(EditPwdActivity.this, "请先确认密码");
                    return;
                }
                editPwd();
                break;
        }
    }

    public void editPwd() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.EDITTEXT_PWD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        MyUtils.showToast(EditPwdActivity.this, "修改成功");
                        LoginActivity.start(EditPwdActivity.this);
                        MyApplication.finishAllActivity();

                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(EditPwdActivity.this,code,msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(EditPwdActivity.this, "网络有问题");
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
                map.put("tel", edit_phone.getText().toString());
                map.put("id_card", edit_code.getText().toString());
                map.put("password", edit_pwd1.getText().toString());
                map.put("password_confirmation", edit_pwd2.getText().toString());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(EditPwdActivity.this).addToRequestQueue(stringRequest);
    }
}
