package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.Register1Bean;
import com.yxys365.smartglasses.entity.Register1Entity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.SmsTimeUtils;
import com.yxys365.smartglasses.utils.SoftHideKeyBoardUtil;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;
import com.yxys365.smartglasses.widget.CustomDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private Button user_info_next;
    private EditText user_info_et_phone;
    private TextView user_info_tv_code;
    private EditText user_info_et_code;
    private EditText user_info_et_pwd;
    private EditText user_info_et_name;
    private RadioGroup user_info_rg_sex;
    private RadioButton user_info_rb_man;
    private RadioButton user_info_rb_woman;
    private TextView user_info_tv_date;
    private TextView user_info_et_card;
    private ImageView user_info_iv_card;
    private EditText user_info_et_card_number;
    private String TAG = "UserInfoActivity";
    private String doc_type;
    private String sex;
    private CustomDatePicker customDatePicker;
    private EditText user_info_et_parent;
    private EditText user_info_et_wx;
    private Register1Bean register1Bean = new Register1Bean();
    private EditText user_info_et_id_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setTitle("用户信息");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initEvent();
        initDatePicker();
        //判断是否需要倒计时
        if (SmsTimeUtils.check(SmsTimeUtils.SETTING_FINANCE_ACCOUNT_TIME, true)) {
            SmsTimeUtils.startCountdown(user_info_tv_code);
        }

    }

    private void initEvent() {
        user_info_rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.user_info_rb_man:
                        sex = "1";
                        break;
                    case R.id.user_info_rb_woman:
                        sex = "2";
                        break;
                }
            }
        });
    }

    /**
     * 初始化日期选择
     */
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                user_info_tv_date.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    private void initViews() {
        user_info_next = findViewById(R.id.user_info_next);
        user_info_next.setOnClickListener(this);

        user_info_et_phone = findViewById(R.id.user_info_et_phone);
        user_info_tv_code = findViewById(R.id.user_info_tv_code);
        user_info_tv_code.setOnClickListener(this);
        user_info_et_code = findViewById(R.id.user_info_et_code);
        user_info_et_pwd = findViewById(R.id.user_info_et_pwd);
        user_info_et_name = findViewById(R.id.user_info_et_name);
        user_info_rg_sex = findViewById(R.id.user_info_rg_sex);
        user_info_rb_man = findViewById(R.id.user_info_rb_man);
        user_info_rb_woman = findViewById(R.id.user_info_rb_woman);
        user_info_tv_date = findViewById(R.id.user_info_tv_date);
        user_info_tv_date.setOnClickListener(this);
        user_info_et_card = findViewById(R.id.user_info_et_card);
        user_info_iv_card = findViewById(R.id.user_info_iv_card);
        user_info_iv_card.setOnClickListener(this);
        user_info_et_card.setOnClickListener(this);
        user_info_et_card_number = findViewById(R.id.user_info_et_card_number);

        user_info_et_parent = findViewById(R.id.user_info_et_parent);
        user_info_et_wx = findViewById(R.id.user_info_et_wx);
        user_info_et_id_card = findViewById(R.id.user_info_et_id_card);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_next:

                if (TextUtils.isEmpty(user_info_et_phone.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(user_info_et_code.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(user_info_et_pwd.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入密码");
                    return;
                }
                if (TextUtils.isEmpty(user_info_et_name.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入孩子姓名");
                    return;
                }
                if (TextUtils.isEmpty(user_info_et_id_card.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入孩子身份证号");
                    return;
                }
                if (TextUtils.isEmpty(sex)) {
                    MyUtils.showToast(UserInfoActivity.this, "请先选择性别");
                    return;
                }
                if (TextUtils.isEmpty(user_info_tv_date.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入生日");
                    return;
                }

//                if (TextUtils.isEmpty(doc_type)) {
//                    MyUtils.showToast(UserInfoActivity.this, "请先选择证件类型");
//                    return;
//                }

//                if (TextUtils.isEmpty(user_info_et_card_number.getText().toString())) {
//                    MyUtils.showToast(UserInfoActivity.this, "请先输入证件号码");
//                    return;
//                }

                if (TextUtils.isEmpty(user_info_et_parent.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入监护人姓名");
                    return;
                }

                if (TextUtils.isEmpty(user_info_et_wx.getText().toString())) {
                    MyUtils.showToast(UserInfoActivity.this, "请先输入监护人微信号");
                    return;
                }

                register1Bean.setTel(user_info_et_phone.getText().toString().trim());
                register1Bean.setPassword(user_info_et_pwd.getText().toString().trim());
                register1Bean.setVercode(user_info_et_code.getText().toString().trim());
                register1Bean.setName(user_info_et_name.getText().toString().trim());
                register1Bean.setSex(sex);
//                register1Bean.setDoc_type(doc_type);
                register1Bean.setBirthday(user_info_tv_date.getText().toString());
//                register1Bean.setDoc_number(user_info_et_card_number.getText().toString().trim());
                register1Bean.setWechat(user_info_et_wx.getText().toString());
                register1Bean.setGuardian_name(user_info_et_parent.getText().toString());
                register1Bean.setIdcard(user_info_et_id_card.getText().toString());
                MyUtils.Loge(TAG, "register1Bean----1-----:" + register1Bean.toString());
//                UserInfo2Activity.start(this,register1Bean);
                getStep1Result();
                break;
            case R.id.user_info_tv_code:
                if (!TextUtils.isEmpty(user_info_et_phone.getText().toString())) {
                    getSmsCode();
                } else {
                    MyUtils.showToast(UserInfoActivity.this, "请先填写手机号");
                }
                break;
            case R.id.user_info_iv_card:
            case R.id.user_info_et_card:
                showDialogData();
                break;
            case R.id.user_info_tv_date:
                if (!TextUtils.isEmpty(user_info_tv_date.getText().toString())) {
                    customDatePicker.show(user_info_tv_date.getText().toString());
                } else {
                    customDatePicker.show("1990-01-01");
                }
                break;
        }
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
                        SmsTimeUtils.startCountdown(user_info_tv_code);
                    } else {
                        MyUtils.showToast(UserInfoActivity.this, register1Entity.getMsg());
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", user_info_et_phone.getText().toString());
                map.put("type", "register");
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(UserInfoActivity.this).addToRequestQueue(stringRequest);
    }


    private void showDialogData() {
        String[] sex = new String[]{"身份证", "护照"};
        DialogBottomView dialogBottomView = new DialogBottomView(UserInfoActivity.this, R.layout.dialog_wheel_one, "选择性别", 1);
        dialogBottomView.setData(sex);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1, String item2, String item3) {
                if (!TextUtils.isEmpty(item2)) {
                    if (item2.equals("身份证")) {
                        doc_type = "1";
                        user_info_et_card.setText("身份证");
                    }
                    if (item2.equals("护照")) {
                        doc_type = "2";
                        user_info_et_card.setText("护照");
                    }
                } else {
                    doc_type = "1";
                    user_info_et_card.setText("身份证");
                }
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }

    /**
     * 注册第一步请求
     */
    private void getStep1Result() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.REGISTER1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        String register_code = jsonObject.getString("register_code");
                        SaveUtils.setString(KeyUtils.register_code, register_code);
                        NvActivity.start(UserInfoActivity.this);

                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(UserInfoActivity.this, code, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
                map.put("idcard", register1Bean.getIdcard());
//                map.put("doc_type", register1Bean.getDoc_type());
//                map.put("doc_number", register1Bean.getDoc_number());
                map.put("wechat", register1Bean.getWechat());
//                map.put("email",register1Bean.getEmail());
                map.put("guardian_name", register1Bean.getGuardian_name());
//                map.put("guardian_tel",register1Bean.getGuardian_tel());
                return map;
            }
        };

        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(UserInfoActivity.this).addToRequestQueue(stringRequest);
    }


}
