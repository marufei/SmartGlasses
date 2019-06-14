package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.dialog.VisionDataView;
import com.yxys365.smartglasses.entity.LoginEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONObject;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends CheckPermissionsActivity implements View.OnClickListener, AMapLocationListener {

    private TextView login_register;
    private TextView login_forget;
    private EditText login_tel;
    private EditText login_pwd;
    private Button login_in;
    private String TAG = "LoginActivity";
    private AlertDialog dlg;
    private VisionDataView visionDataView;
    private LoginEntity loginEntity;
    /**
     * 经度
     */
    private double latitude;
    /**
     * 纬度
     */
    private double longitude;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPermission();
        MyUtils.Loge(TAG, MyUtils.sHA1(this));
        initLocation();
        showLoad(this, "登录中...");
        setDarkStatusIcon(true);
        initViews();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.tel))) {
            login_tel.setText(SaveUtils.getString(KeyUtils.tel));
        }
    }

    private void initViews() {
        login_register = findViewById(R.id.login_register);
        login_register.setOnClickListener(this);


        login_forget = findViewById(R.id.login_forget);
        login_forget.setOnClickListener(this);

        login_tel = findViewById(R.id.login_tel);
        login_pwd = findViewById(R.id.login_pwd);
        login_in = findViewById(R.id.login_in);
        login_in.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                UserInfoActivity.start(this);
                break;
            case R.id.login_forget:
                ForgetPwdActivity.start(this);
                break;
            case R.id.login_in:
                if (TextUtils.isEmpty(login_tel.getText().toString())) {
                    MyUtils.showToast(LoginActivity.this, "请先输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(login_pwd.getText().toString())) {
                    MyUtils.showToast(LoginActivity.this, "请先输入密码");
                    return;
                }
                dlg.show();
                loginIn();
                break;
        }
    }

    /**
     * 登录
     */
    public void loginIn() {
        MyUtils.Loge(TAG, "点击了登录");
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.LOGIN;
        MyUtils.Loge(TAG,"登录地址："+url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                dlg.dismiss();
                try {
                    Gson gson = new Gson();
                    loginEntity = gson.fromJson(response, LoginEntity.class);
                    if (loginEntity.getCode() == 0) {
                        SaveUtils.setString(KeyUtils.access_token, loginEntity.getData().getAccess_token());
                        SaveUtils.setString(KeyUtils.token_type, loginEntity.getData().getToken_type());
                        SaveUtils.setString(KeyUtils.tel,login_tel.getText().toString());
                        SaveUtils.setString(KeyUtils.pwd,login_pwd.getText().toString());
                        SaveUtils.setInt(KeyUtils.vision_upload,loginEntity.getData().getVision_upload());
                        MainActivity.start(LoginActivity.this);
                        finish();
//                        if (loginEntity.getData().getVision_upload() == 0) {//未上传裸眼视力
//                            showVisionDialog();
//                        } else {//1.已上传裸眼视力
//                            MainActivity.start(LoginActivity.this);
//                        }
                    } else {
                        VolleyUtils.dealErrorStatus(LoginActivity.this, loginEntity.getCode(), loginEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(LoginActivity.this, "网络有问题");
                dlg.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", login_tel.getText().toString());
                map.put("password", login_pwd.getText().toString());
                if (!TextUtils.isEmpty(province)) {
                    map.put("province", province);
                }
                if (!TextUtils.isEmpty(city)) {
                    map.put("city", city);
                }
                if (!TextUtils.isEmpty(String.valueOf(longitude)) && !TextUtils.isEmpty(String.valueOf(latitude))) {
                    map.put("location", longitude + "," + latitude);
                }
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * 加载动画
     */
    public void showLoad(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View layout = inflater.inflate(R.layout.dialog_show, null);
        TextView dialog_msg = layout.findViewById(R.id.dialog_msg);
        dialog_msg.setText(msg);
        builder.setView(layout);
        dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
    }

    /**
     * 填写 视力 标识数
     */
    public void showVisionDialog() {
        visionDataView = new VisionDataView(this);
        visionDataView.setOnVisionListener(new VisionDataView.OnVisionListener() {
            @Override
            public void onSure(VisionDataView.VisionBean vision) {
                //TODO 上传数据
                MyUtils.Loge(TAG, "vision:::::" + vision);
                updateData(vision);
                visionDataView.dismiss();
            }
        });
        visionDataView.showDialog();
    }

    /**
     * 高德地图定位
     */
    private void initLocation() {
        //初始化定位参数  
        mLocationOption = new AMapLocationClientOption();
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位监听  
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式  
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms  
        mLocationOption.setInterval(1000);
        //设置是否只定位一次,默认为false  
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。  
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。  
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数  
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，  
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求  
        // 在定位结束后，在合适的生命周期调用onDestroy()方法  
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除  
        //启动定位  
        mLocationClient.startLocation();
    }

    /**
     * 上传裸眼数据
     *
     * @param vision
     */
    private void updateData(final VisionDataView.VisionBean vision) {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_VISION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                dlg.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {

                        SaveUtils.setString(KeyUtils.expires_in, String.valueOf(loginEntity.getData().getExpires_in()));

                        SaveUtils.setString(KeyUtils.tel, login_tel.getText().toString());
                        SaveUtils.setString(KeyUtils.pwd, login_pwd.getText().toString());
                        MainActivity.start(LoginActivity.this);
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(LoginActivity.this, code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(LoginActivity.this, "网络有问题");
                dlg.dismiss();
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
                map.put("left_vision", vision.getLeft_vision());
                map.put("left_num", vision.getLeft_num());
                map.put("right_vision", vision.getRight_vision());
                map.put("right_num", vision.getRight_num());
                map.put("double_vision", vision.getDouble_vision());
                map.put("double_num", vision.getDouble_num());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                latitude = aMapLocation.getLatitude();//纬度
                longitude = aMapLocation.getLongitude();//经度
                province = aMapLocation.getProvince();
                city = aMapLocation.getCity();

                MyUtils.Loge(TAG, "latitude:" + latitude);
                MyUtils.Loge(TAG, "longitude:" + longitude);
            } else {
                MyUtils.Loge(TAG, "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

        }
    }
}
