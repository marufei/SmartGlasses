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
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.Register1Bean;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfo2Activity extends BaseActivity implements View.OnClickListener {

    private String TAG = "UserInfo2Activity";
    private Button user_info2_next;
    private EditText user_info2_name;
    private EditText user_info2_phone;
    private EditText user_info2_email;
    private Register1Bean register1Bean;
    private EditText user_info2_wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);

        setTitle("用户信息");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initData();
    }

    private void initData() {
        register1Bean = (Register1Bean) getIntent().getSerializableExtra("register1Bean");
        MyUtils.Loge(TAG, "register1Bean:" + register1Bean.toString());
    }

    private void initViews() {
        user_info2_next = findViewById(R.id.user_info2_next);
        user_info2_next.setOnClickListener(this);

        user_info2_name = findViewById(R.id.user_info2_name);
        user_info2_phone = findViewById(R.id.user_info2_phone);
        user_info2_email = findViewById(R.id.user_info2_email);
        user_info2_wx = findViewById(R.id.user_info2_wx);


    }

    public static void start(Context context, Register1Bean register1Bean) {
        Intent intent = new Intent();
        intent.setClass(context, UserInfo2Activity.class);
        intent.putExtra("register1Bean", register1Bean);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info2_next:
//                NvActivity.start(this);
                if (TextUtils.isEmpty(user_info2_name.getText().toString())) {
                    register1Bean.setGuardian_name("");
                } else {
                    register1Bean.setGuardian_name(user_info2_name.getText().toString());
                }
                if (TextUtils.isEmpty(user_info2_phone.getText().toString())) {
                    register1Bean.setGuardian_tel("");
                } else {
                    register1Bean.setGuardian_tel(user_info2_phone.getText().toString());
                }
                if (TextUtils.isEmpty(user_info2_email.getText().toString())) {
                    register1Bean.setEmail("");
                } else {
                    register1Bean.setEmail(user_info2_email.getText().toString());
                }
                if (TextUtils.isEmpty(user_info2_wx.getText().toString())) {
                    register1Bean.setWechat("");
                } else {
                    register1Bean.setWechat(user_info2_wx.getText().toString());
                }
                MyUtils.Loge(TAG,"register1Bean:::"+register1Bean.toString());

                getStep1Result();
                break;
        }
    }

    /**
     * 注册第一步请求
     */
    private void getStep1Result() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.REGISTER1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if(code==0){
                        String register_code=jsonObject.getString("register_code");
                        SaveUtils.setString(KeyUtils.register_code,register_code);
                        NvActivity.start(UserInfo2Activity.this);

                    }else {
                        String msg=jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(UserInfo2Activity.this,code,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfo2Activity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", register1Bean.getTel());
                map.put("password", register1Bean.getPassword());
                map.put("password_confirmation", register1Bean.getPassword());
                map.put("vercode", register1Bean.getVercode());
                map.put("name", register1Bean.getName());
                map.put("sex", register1Bean.getSex());
                map.put("birthday", register1Bean.getBirthday());
                map.put("doc_type", register1Bean.getDoc_type());
                map.put("doc_number", register1Bean.getDoc_number());
                map.put("wechat",register1Bean.getWechat());
                map.put("email",register1Bean.getEmail());
                map.put("guardian_name",register1Bean.getGuardian_name());
                map.put("guardian_tel",register1Bean.getGuardian_tel());
                return map;
            }
        };

        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(UserInfo2Activity.this).addToRequestQueue(stringRequest);
    }
}
