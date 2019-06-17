package com.yxys365.smartglasses.activity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.AutoPlanEntity;
import com.yxys365.smartglasses.entity.BaseEntity;
import com.yxys365.smartglasses.entity.InfoEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogIdcardView;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends BaseActivity implements View.OnClickListener,DialogIdcardView.OnCardCallback{

    private TextView user_name,user_tel,user_birth,user_parent,user_wx,user_card;
    private ImageView user_man,user_woman;
    private String TAG="UserActivity";
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//        ToolBarStyle(1);
        initView();
        initData();

    }

    private void initView() {
        user_name=findViewById(R.id.user_name);
        user_tel=findViewById(R.id.user_tel);
        user_birth=findViewById(R.id.user_birth);
        user_man=findViewById(R.id.user_man);
        user_woman=findViewById(R.id.user_woman);
        user_card=findViewById(R.id.user_card);
        user_card.setOnClickListener(this);
        user_parent=findViewById(R.id.user_parent);
        user_wx=findViewById(R.id.user_wx);
        user_name=findViewById(R.id.user_name);
        user_name=findViewById(R.id.user_name);
        title=findViewById(R.id.title);
        title.setText("用户信息");
        back=findViewById(R.id.back);
        back.setOnClickListener(this);


    }

    private void initData() {
        getPersionInfo();
    }

    private void getPersionInfo(){
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.INFO;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "getPersionInfo()--response:" + response);
                try {
                    Gson gson = new Gson();
                    InfoEntity infoEntity=gson.fromJson(response, InfoEntity.class);
                    if (infoEntity!=null&&infoEntity.getCode() == 0) {
                        if (infoEntity.getUser()!=null)
                        setView(infoEntity.getUser());
                    } else {
                        MyUtils.Loge(TAG, "getPersionInfo()");
                        VolleyUtils.dealErrorStatus(UserActivity.this, infoEntity.getCode(), infoEntity.getMsg());
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserActivity.this, "网络有问题");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(UserActivity.this).addToRequestQueue(stringRequest);
    }

    private void setView(InfoEntity.UserBean user) {
        if (!TextUtils.isEmpty(user.getName())) {
            user_name.setText(user.getName());
        }
        if (!TextUtils.isEmpty(user.getTel())){
            user_tel.setText(user.getTel());
        }
        if(!TextUtils.isEmpty(user.getBirthday())){
            user_birth.setText(user.getBirthday());
        }
        if (user.getSex()==1){
            user_man.setImageResource(R.mipmap.choose_y);
            user_woman.setImageResource(R.mipmap.choose_n);
        }else {
            user_man.setImageResource(R.mipmap.choose_n);
            user_woman.setImageResource(R.mipmap.choose_y);
        }
        if(!TextUtils.isEmpty(user.getGuardian_name())){
            user_parent.setText(user.getGuardian_name());
        }
        if(!TextUtils.isEmpty(user.getWechat())){
            user_wx.setText(user.getWechat());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_card:
                DialogIdcardView dialogIdcardView=new DialogIdcardView(UserActivity.this);
                dialogIdcardView.setOnCardCallback(this);
                dialogIdcardView.showDialog();
                break;
            case R.id.back:
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }



    private void commit(final String number){
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.IDCARD_UPDATE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "commit()--response:" + response);
                try {
                    Gson gson = new Gson();
                    BaseEntity baseEntity=gson.fromJson(response,BaseEntity.class);
                    if (baseEntity!=null&&baseEntity.getCode()==0){
                        MyUtils.showToast(UserActivity.this,"提交成功");
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    } else {
                        MyUtils.Loge(TAG, "getPersionInfo()");
                        VolleyUtils.dealErrorStatus(UserActivity.this, baseEntity.getCode(), baseEntity.getMsg());
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserActivity.this, "网络有问题");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idcard", number);
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(UserActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCardclick(String number) {
        commit(number);
    }
}
