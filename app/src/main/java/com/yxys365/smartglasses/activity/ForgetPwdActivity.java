package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.Register1Entity;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SmsTimeUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{


    private EditText forget_tel;
    private EditText forget_code;
    private EditText forget_pwd1;
    private EditText forget_pwd2;
    private Button forget_sure;
    private String TAG="ForgetPwdActivity";
    private TextView forget_regcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        setTitle("忘记密码");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        //判断是否需要倒计时
        if (SmsTimeUtils.check(SmsTimeUtils.SETTING_FINANCE_ACCOUNT_TIME, true)) {
            SmsTimeUtils.startCountdown(forget_regcode);
        }
    }

    private void initViews() {
        forget_tel=findViewById(R.id.forget_tel);
        forget_code=findViewById(R.id.forget_code);
        forget_pwd1=findViewById(R.id.forget_pwd1);
        forget_pwd2=findViewById(R.id.forget_pwd2);
        forget_sure=findViewById(R.id.forget_sure);
        forget_sure.setOnClickListener(this);
        forget_regcode=findViewById(R.id.forget_regcode);
        forget_regcode.setOnClickListener(this);

    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_sure:
                if(TextUtils.isEmpty(forget_tel.getText().toString())){
                    MyUtils.showToast(ForgetPwdActivity.this,"请先输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(forget_code.getText().toString())){
                    MyUtils.showToast(ForgetPwdActivity.this,"请先输入手机验证码");
                    return;
                }
                if(TextUtils.isEmpty(forget_pwd1.getText().toString())){
                    MyUtils.showToast(ForgetPwdActivity.this,"请先输入密码");
                    return;
                }
                if(TextUtils.isEmpty(forget_pwd2.getText().toString())){
                    MyUtils.showToast(ForgetPwdActivity.this,"请输入确认密码");
                    return;
                }
                findPwd();
                break;
            case R.id.forget_regcode:
                if(TextUtils.isEmpty(forget_tel.getText().toString())){
                    MyUtils.showToast(ForgetPwdActivity.this,"请先输入手机号");
                    return;
                }
                getSmsCode();
                break;
        }
    }

    public void findPwd(){
        String url= HttpsAddress.BASE_ADDRESS+HttpsAddress.RESET_PWD;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int code=jsonObject.getInt("code");
                    if(code==0){
                        MyUtils.showToast(ForgetPwdActivity.this,"重置密码成功");
                        finish();
                    }else {
                        String msg=jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(ForgetPwdActivity.this,code,msg);
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ForgetPwdActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("tel",forget_tel.getText().toString());
                map.put("vercode",forget_code.getText().toString());
                map.put("password",forget_pwd1.getText().toString());
                map.put("password_confirmation",forget_pwd2.getText().toString());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(ForgetPwdActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * 获取验证码
     */
    private void getSmsCode() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.SMS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    Register1Entity register1Entity = gson.fromJson(response, Register1Entity.class);
                    if (register1Entity.getCode() == 0) {
                        SmsTimeUtils.check(SmsTimeUtils.SETTING_FINANCE_ACCOUNT_TIME, false);
                        SmsTimeUtils.startCountdown(forget_regcode);
                    } else {
                        VolleyUtils.dealErrorStatus(ForgetPwdActivity.this,register1Entity.getCode(),register1Entity.getMsg());
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ForgetPwdActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", forget_tel.getText().toString());
                map.put("type", "reset");
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(ForgetPwdActivity.this).addToRequestQueue(stringRequest);
    }
}
