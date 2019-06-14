package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clj.fastble.BleManager;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.BuyTimeActivity;
import com.yxys365.smartglasses.activity.EditPwdActivity;
import com.yxys365.smartglasses.activity.ExpreciseActivity;
import com.yxys365.smartglasses.activity.LoginActivity;
import com.yxys365.smartglasses.activity.NvFormActivity;
import com.yxys365.smartglasses.activity.NvListActivity;
import com.yxys365.smartglasses.activity.OcularActivity;
import com.yxys365.smartglasses.activity.UseDeviceActivity;
import com.yxys365.smartglasses.activity.Web_Activity;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaRufei
 * on 2018/6/6.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private LinearLayout mine_ll1;
    private LinearLayout mine_ll3;
    private LinearLayout mine_ll5;
    private LinearLayout mine_ll4;
    private LinearLayout mine_ll2;
    private LinearLayout mine_ll6;
    private LinearLayout instruction;
    private LinearLayout mine_pwd;
    private TextView mine_device;
    private Button mine_login_out;
    private LinearLayout update_version;
    private TextView mine_version;

    @Override
    protected void lazyLoad() {
//        if(!TextUtils.isEmpty(MyApplication.ONE_CODE)){
//            getDevice();
//        }else {
//            if(view!=null){
//                mine_device.setText("未绑定");
//            }
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        initViews();
        initData();
        return view;
    }

    private void initData() {
        getDevice();
    }

    private void initViews() {
        mine_ll1 = view.findViewById(R.id.mine_ll1);
        mine_ll1.setOnClickListener(this);

        mine_ll2 = view.findViewById(R.id.mine_ll2);
        mine_ll2.setOnClickListener(this);

        mine_ll3 = view.findViewById(R.id.mine_ll3);
        mine_ll3.setOnClickListener(this);

        mine_ll4 = view.findViewById(R.id.mine_ll4);
        mine_ll4.setOnClickListener(this);

        mine_ll5 = view.findViewById(R.id.mine_ll5);
        mine_ll5.setOnClickListener(this);

        mine_ll6 = view.findViewById(R.id.mine_ll6);
        mine_ll6.setOnClickListener(this);
        instruction = view.findViewById(R.id.instruction);
        instruction.setOnClickListener(this);

        mine_pwd = view.findViewById(R.id.mine_pwd);
        mine_pwd.setOnClickListener(this);

        mine_device = view.findViewById(R.id.mine_device);

        mine_login_out=view.findViewById(R.id.mine_login_out);
        mine_login_out.setOnClickListener(this);

        update_version=view.findViewById(R.id.update_version);
        update_version.setOnClickListener(this);
        mine_version=view.findViewById(R.id.mine_version);
        mine_version.setText("v"+MyUtils.getVersionName(getActivity()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_ll1:
                NvFormActivity.start(getActivity());
                break;
            case R.id.mine_ll2:
                NvListActivity.start(getActivity());
                break;
            case R.id.mine_ll3:
                OcularActivity.start(getActivity());
                break;
            case R.id.mine_ll4:
                ExpreciseActivity.start(getActivity());
                break;
            case R.id.mine_ll5:
                BuyTimeActivity.start(getActivity());
                break;
            case R.id.mine_ll6:
                UseDeviceActivity.start(getActivity());
                break;
            case R.id.instruction:
                getInstruction();
                break;
            case R.id.mine_pwd:
                EditPwdActivity.start(getActivity());
                break;
            case R.id.mine_login_out:
                SaveUtils.setString(KeyUtils.tel,"");
                SaveUtils.setString(KeyUtils.pwd,"");
                SaveUtils.setString(KeyUtils.access_token,"");
                SaveUtils.setString(KeyUtils.expires_in,"");
                SaveUtils.setString(KeyUtils.token_type,"");
                SaveUtils.setInt(KeyUtils.vision_upload,0);
                MyApplication.finishAllActivity();
                LoginActivity.start(getActivity());
                break;
            case R.id.update_version:
                break;
        }
    }

    /**
     * 说明书
     */
    public void getInstruction() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.INSTRUCTION;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        String url = jsonObject.getString("url");
                        Web_Activity.start(getActivity(), url);
                    } else {
                        MyUtils.showToast(getActivity(), "服务器异常，请稍后再试");
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        });
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 我的设备
     */
    public void getDevice() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.MY_DEVICE + "?device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("device");
                            String id = jsonObject1.getString("id");
                            mine_device.setText(id);
                        } else {
                            mine_device.setText("未绑定");
                        }
                    } else {
                        MyUtils.showToast(getActivity(), "服务器异常，请稍后再试");
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
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
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }
}
