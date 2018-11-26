package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.dialog.VisionDataView;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.entity.DistrictEntity;
import com.yxys365.smartglasses.entity.LoginEntity;
import com.yxys365.smartglasses.entity.Register3Entity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.SoftHideKeyBoardUtil;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;
import com.yxys365.smartglasses.views.DialogmidView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 服务信息
 */
public class ServiceInfoActivity extends BaseActivity implements View.OnClickListener,AMapLocationListener {

    private Button user_info_commit;
    private ImageView service_zxing;
    private EditText service_et_address;
    private TextView service_et_province;
    private TextView service_et_city;
    private TextView service_et_district;
    private EditText service_et_jxs;
    private EditText service_et_dls;
    private EditText service_et_bxk;
    private EditText service_et_mdfw;
    private EditText service_et_jcz;
    private EditText service_et_wym;
//    private EditText service_et_time;

    private List<DistrictEntity.RegionsBean> provinceData = new ArrayList<>();
    private List<String> provinceName = new ArrayList<>();

    private List<DistrictEntity.RegionsBean> cityData = new ArrayList<>();
    private List<String> cityName = new ArrayList<>();

    private List<DistrictEntity.RegionsBean> districtData = new ArrayList<>();
    private List<String> districtName = new ArrayList<>();

    /**
     * 地区码
     */
    private String adcode = "100000";
    private String TAG = "ServiceInfoActivity";
    private String single_data;
    private String[] data = null;
    /**
     * 省市区 id
     */
    private String provinceId,cityId,discrictId;
    private EditText service_et_bm;
    private LoginEntity loginEntity;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;
    private double latitude;
    private double longitude;
    private String province;
    private String city;
    private VisionDataView visionDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        SoftHideKeyBoardUtil.assistActivity(this);
        SoftHideKeyBoardUtil.assistActivity(this);
        setTitle("服务信息");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ServiceInfoActivity.class);
        context.startActivity(intent);
    }

    private void initDatas() {

    }

    private void initViews() {
        user_info_commit = findViewById(R.id.user_info_commit);
        user_info_commit.setOnClickListener(this);

        service_zxing = findViewById(R.id.service_zxing);
        service_zxing.setOnClickListener(this);

        service_et_address = findViewById(R.id.service_et_address);
        service_et_province = findViewById(R.id.service_et_province);
        service_et_province.setOnClickListener(this);
        service_et_city = findViewById(R.id.service_et_city);
        service_et_city.setOnClickListener(this);
        service_et_district = findViewById(R.id.service_et_district);
        service_et_district.setOnClickListener(this);
        service_et_jxs = findViewById(R.id.service_et_jxs);
        service_et_dls = findViewById(R.id.service_et_dls);
        service_et_bxk = findViewById(R.id.service_et_bxk);
        service_et_mdfw = findViewById(R.id.service_et_mdfw);
        service_et_jcz = findViewById(R.id.service_et_jcz);
        service_et_wym = findViewById(R.id.service_et_wym);
//        service_et_time = findViewById(R.id.service_et_time);
        service_et_bm=findViewById(R.id.service_et_bm);
    }

    /**
     * 注册成功弹窗
     *
     * @param layoutId
     */
    public void myDialog(int layoutId, final String tel, final String pwd) {
        final DialogmidView dialogBottomView = new DialogmidView(ServiceInfoActivity.this, layoutId);
        Button dialog_btn_login = dialogBottomView.findViewById(R.id.dialog_btn_login);
        TextView dialog_register_tel=dialogBottomView.findViewById(R.id.dialog_register_tel);
        TextView dialog_register_pwd=dialogBottomView.findViewById(R.id.dialog_register_pwd);
        if(!TextUtils.isEmpty(tel)) {
            dialog_register_tel.setText(tel);
        }
        if(!TextUtils.isEmpty(pwd)) {
            dialog_register_pwd.setText(pwd);
        }
        dialog_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottomView.dismiss();
//                LoginActivity.start(ServiceInfoActivity.this);
                loginIn(tel,pwd);
            }
        });
        dialogBottomView.showDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_commit:
                if(TextUtils.isEmpty(service_et_province.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先选择省份");
                    return;
                }
                if(TextUtils.isEmpty(service_et_city.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先选择城市");
                    return;
                }
//                if(TextUtils.isEmpty(service_et_address.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写详细地址");
//                    return;
//                }
                if(TextUtils.isEmpty(service_et_jxs.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写经销商代码");
                    return;
                }
                if(TextUtils.isEmpty(service_et_dls.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写代理商代码");
                    return;
                }
//                if(TextUtils.isEmpty(service_et_bxk.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写保修卡");
//                    return;
//                }
//                if(TextUtils.isEmpty(service_et_mdfw.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写门店服务代码");
//                    return;
//                }
//                if(TextUtils.isEmpty(service_et_jcz.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请输入检查者姓名");
//                    return;
//                }
                if(TextUtils.isEmpty(service_et_bm.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先填写机器编码");
                    return;
                }
//                if(TextUtils.isEmpty(service_et_wym.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请先绑定机器唯一码");
//                    return;
//                }
//                if(TextUtils.isEmpty(service_et_time.getText().toString())){
//                    MyUtils.showToast(ServiceInfoActivity.this,"请先设置购置锻炼时间");
//                    return;
//                }

                getStep4Data();
                break;
            case R.id.service_zxing:
                customScan();
                break;
            case R.id.service_et_province:
                getDistrict(1);
                break;
            case R.id.service_et_city:
                if(TextUtils.isEmpty(service_et_province.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先选择省份");
                    return;
                }
                getDistrict(2);
                break;
            case R.id.service_et_district:
                if(TextUtils.isEmpty(service_et_province.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先选择省份");
                    return;
                }
                if(TextUtils.isEmpty(service_et_city.getText().toString())){
                    MyUtils.showToast(ServiceInfoActivity.this,"请先选择城市");
                    return;
                }
                getDistrict(3);
                break;
        }
    }

    /**
     * 注册第四步
     */
    public void getStep4Data(){
        String url=HttpsAddress.BASE_ADDRESS+HttpsAddress.REGISTER4;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    Gson gson = new Gson();
                    Register3Entity register3Entity = gson.fromJson(response, Register3Entity.class);
                    if (register3Entity.getCode() == 0) {
                        SaveUtils.setString(KeyUtils.tel,register3Entity.getTel());
                        myDialog(R.layout.dialog_register, register3Entity.getTel(), register3Entity.getPassword());
                    } else {
                        VolleyUtils.dealErrorStatus(ServiceInfoActivity.this,register3Entity.getCode(),register3Entity.getMsg());
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ServiceInfoActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("register_code", SaveUtils.getString(KeyUtils.register_code));
                map.put("device_id",service_et_bm.getText().toString());
//                map.put("device_code",service_et_wym.getText().toString());
                map.put("province_id",provinceId);
                map.put("city_id",cityId);
                if(!TextUtils.isEmpty(discrictId)) {
                    map.put("district_id", discrictId);
                }
                if(!TextUtils.isEmpty(service_et_address.getText().toString())) {
                    map.put("address_detail", service_et_address.getText().toString());
                }
                if(!TextUtils.isEmpty(service_et_mdfw.getText().toString())) {
                    map.put("shop_code", service_et_mdfw.getText().toString());
                }
                if(!TextUtils.isEmpty(service_et_bxk.getText().toString())) {
                    map.put("warranty_number", service_et_bxk.getText().toString());
                }
                map.put("agent_code",service_et_dls.getText().toString());
                map.put("dealer_code",service_et_jxs.getText().toString());
                if(!TextUtils.isEmpty(service_et_jcz.getText().toString())) {
                    map.put("reviewer", service_et_jcz.getText().toString());
                }
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(ServiceInfoActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * 获取省市区
     */
    public void getDistrict(final int type) {
        MyUtils.Loge(TAG, "adcode::" + adcode);
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.REGION+"?padcode="+adcode;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    DistrictEntity districtEntity = gson.fromJson(response, DistrictEntity.class);
                    if (districtEntity.getCode() == 0) {
                        switch (type) {
                            case 1:
                                provinceData.addAll(districtEntity.getRegions());
                                for (int i = 0; i < provinceData.size(); i++) {
                                    provinceName.add(provinceData.get(i).getName());
                                }
                                showDialog1(1);
                                break;
                            case 2:
                                cityData.addAll(districtEntity.getRegions());
                                for (int i = 0; i < cityData.size(); i++) {
                                    cityName.add(cityData.get(i).getName());
                                }
                                showDialog1(2);
                                break;
                            case 3:
                                districtData.addAll(districtEntity.getRegions());
                                for (int i = 0; i < districtData.size(); i++) {
                                    districtName.add(districtData.get(i).getName());
                                }
                                showDialog1(3);
                                break;
                        }
                    } else {
                        String msg = districtEntity.getMsg();
                        VolleyUtils.dealErrorStatus(ServiceInfoActivity.this,districtEntity.getCode(),msg);
                    }
                }catch (Exception e){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ServiceInfoActivity.this,"网络有问题");
            }
        });
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(ServiceInfoActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * 省市区选择器
     */
    public void showDialog1(final int type) {
        String title = "";
        data = null;
        if (type == 1) {
            title = "省";
            data = provinceName.toArray(new String[provinceName.size()]);

        } else if (type == 2) {
            title = "市";
            data = cityName.toArray(new String[cityName.size()]);

        } else if (type == 3) {
            title = "区/县";
            data = districtName.toArray(new String[districtName.size()]);
        }
        DialogBottomView dialogBottomView = new DialogBottomView(ServiceInfoActivity.this, R.layout.dialog_wheel_one, title, 1);
        dialogBottomView.setData(data);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1, String item2, String item3) {
                if (!TextUtils.isEmpty(item2)) {
                    single_data = item2;
                } else {
                    single_data = data != null ? data[0] : "";
                }
                switch (type) {
                    case 1:
                        service_et_province.setText(single_data);
                        for (int i = 0; i < provinceData.size(); i++) {
                            if (provinceData.get(i).getName().equals(single_data)) {
                                adcode = provinceData.get(i).getAdcode();
                                provinceId=provinceData.get(i).getAdcode();

                                service_et_city.setText("");
                                service_et_district.setText("");
                                cityName.clear();
                                cityData.clear();
                                districtName.clear();
                                districtData.clear();
                            }
                        }
                        break;
                    case 2:
                        service_et_city.setText(single_data);
                        for (int i = 0; i < cityData.size(); i++) {
                            if (cityData.get(i).getName().equals(single_data)) {
                                adcode = cityData.get(i).getAdcode();
                                cityId=cityData.get(i).getAdcode();
                                service_et_district.setText("");
                                districtName.clear();
                                districtData.clear();
                            }
                        }
                        break;
                    case 3:
                        service_et_district.setText(single_data);
                        for(int i=0;i<districtData.size();i++){
                            if(districtData.get(i).getName().equals(single_data)){
                                discrictId=districtData.get(i).getAdcode();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }

    // 你也可以使用简单的扫描功能，但是一般扫描的样式和行为都是可以自定义的，这里就写关于自定义的代码了
    // 你可以把这个方法作为一个点击事件
    public void customScan() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
// 通过 onActivityResult的方法获取 扫描回来的 值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
//                String ScanResult = intentResult.getContents();
//                MyUtils.Loge(TAG,"扫描结果："+ScanResult);
//                MyUtils.showToast(ServiceInfoActivity.this,"扫描结果为："+ScanResult);
                service_et_bm.setText(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 登录
     */
    public void loginIn(final String tel, final String pwd) {
        MyUtils.Loge(TAG, "点击了登录");
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    loginEntity = gson.fromJson(response, LoginEntity.class);
                    if (loginEntity.getCode() == 0) {
                        SaveUtils.setString(KeyUtils.access_token, loginEntity.getData().getAccess_token());
                        SaveUtils.setString(KeyUtils.token_type, loginEntity.getData().getToken_type());
//                        MyApplication.ONE_CODE="E30000000003";
                        if (loginEntity.getData().getVision_upload() == 0) {//未上传裸眼视力
                            showVisionDialog(tel,pwd);
                        } else {//1.已上传裸眼视力
                            MainActivity.start(ServiceInfoActivity.this);
                        }
                    } else {
                        VolleyUtils.dealErrorStatus(ServiceInfoActivity.this, loginEntity.getCode(), loginEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ServiceInfoActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tel", tel);
                map.put("password",pwd);
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
        VolleyUtils.getInstance(ServiceInfoActivity.this).addToRequestQueue(stringRequest);
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

    /**
     * 填写 视力 标识数
     */
    public void showVisionDialog(final String tel, final String pwd) {
        visionDataView = new VisionDataView(this);
        visionDataView.setOnVisionListener(new VisionDataView.OnVisionListener() {
            @Override
            public void onSure(VisionDataView.VisionBean vision) {
                //TODO 上传数据
                MyUtils.Loge(TAG, "vision:::::" + vision);
                updateData(vision,tel,pwd);
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
    private void updateData(final VisionDataView.VisionBean vision, final String tel, final String pwd) {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_VISION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {

                        SaveUtils.setString(KeyUtils.expires_in, String.valueOf(loginEntity.getData().getExpires_in()));

                        SaveUtils.setString(KeyUtils.tel, tel);
                        SaveUtils.setString(KeyUtils.pwd, pwd);
                        MainActivity.start(ServiceInfoActivity.this);
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(ServiceInfoActivity.this, code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ServiceInfoActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(ServiceInfoActivity.this).addToRequestQueue(stringRequest);
    }
}
