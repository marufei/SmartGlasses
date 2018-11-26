package com.yxys365.smartglasses.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

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
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.dialog.VisionDataView;
import com.yxys365.smartglasses.entity.LoginEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends CheckPermissionsActivity implements AMapLocationListener {

    private String TAG = "SplashActivity";
    private VisionDataView visionDataView;
    private LoginEntity loginEntity;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        full(true);
        setPermission();
        MyUtils.Loge(TAG,MyUtils.sHA1(this));
        setContentView(R.layout.activity_splash);
        initData();

    }

    private void initData() {
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.tel)) && !TextUtils.isEmpty(SaveUtils.getString(KeyUtils.pwd))) {
            MyUtils.Loge(TAG,"自动登录");
//            loginIn();
            initLocation();
        } else {
            MyUtils.Loge(TAG,"去登录界面");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(2000);
                        LoginActivity.start(SplashActivity.this);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }


    //启动页全屏，放在setContentView前面
    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 登录
     */
    public void loginIn() {
        MyUtils.Loge(TAG, "登录");
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    loginEntity = gson.fromJson(response, LoginEntity.class);
                    if (loginEntity.getCode() == 0) {
//                        SaveUtils.setString(KeyUtils.access_token, loginEntity.getData().getAccess_token());
//                        SaveUtils.setString(KeyUtils.expires_in, String.valueOf(loginEntity.getData().getExpires_in()));
//                        SaveUtils.setString(KeyUtils.token_type, loginEntity.getData().getToken_type());
//                        MyApplication.ONE_CODE="E30000000003";
//
//                        MainActivity.start(SplashActivity.this);
//                        finish();
                        if (loginEntity.getData().getVision_upload() == 0) {//未上传裸眼视力
                            showVisionDialog();
                        } else {//1.已上传裸眼视力
                            MainActivity.start(SplashActivity.this);
                        }
                    } else {
                        VolleyUtils.dealErrorStatus(SplashActivity.this, loginEntity.getCode(), loginEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SplashActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", SaveUtils.getString(KeyUtils.tel));
                map.put("password", SaveUtils.getString(KeyUtils.pwd));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(SplashActivity.this).addToRequestQueue(stringRequest);
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
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        SaveUtils.setString(KeyUtils.access_token, loginEntity.getData().getAccess_token());
                        SaveUtils.setString(KeyUtils.expires_in, String.valueOf(loginEntity.getData().getExpires_in()));
                        SaveUtils.setString(KeyUtils.token_type, loginEntity.getData().getToken_type());
                        MainActivity.start(SplashActivity.this);
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(SplashActivity.this, code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SplashActivity.this, "网络有问题");

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
        VolleyUtils.getInstance(SplashActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        MyUtils.Loge(TAG,"定位回调");
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                latitude = aMapLocation.getLatitude();//纬度
                longitude = aMapLocation.getLongitude();//经度
            } else {
                MyUtils.Loge(TAG, "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
            loginIn();

        }
    }
}
