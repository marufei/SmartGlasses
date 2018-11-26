package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.NvActivity;
import com.yxys365.smartglasses.activity.RdActivity;
import com.yxys365.smartglasses.activity.UserInfo2Activity;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.entity.Register2Bean;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaRufei
 * on 2018/6/4.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class NvFragment extends BaseFragment implements View.OnClickListener{


    private View view;
    private Button fragment_nv_next;
    private TextView nv_bs_left;
    private TextView nv_bs_right;
    private TextView nv_bs_both;
    private TextView nv_s_left;
    private TextView nv_s_right;
    private TextView nv_s_both;

    private Register2Bean register2Bean=new Register2Bean();
    /**
     * 视力： 左右双眼，表示数量：左右双眼
     */
    private String s_left,s_right,s_both,bs_left,bs_right,bs_both;


    @Override
    protected void lazyLoad() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_nv,null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.register_code))) {
            register2Bean.setRegister_code(SaveUtils.getString(KeyUtils.register_code));
        }
        register2Bean.setVision_type("1");
        MyUtils.Loge(TAG,"--vision_type:"+register2Bean.getVision_type());
    }

    private void initView() {
        fragment_nv_next=view.findViewById(R.id.fragment_nv_next);
        fragment_nv_next.setOnClickListener(this);

        nv_bs_left=view.findViewById(R.id.nv_bs_left);
        nv_bs_left.setOnClickListener(this);

        nv_bs_right=view.findViewById(R.id.nv_bs_right);
        nv_bs_right.setOnClickListener(this);

        nv_bs_both=view.findViewById(R.id.nv_bs_both);
        nv_bs_both.setOnClickListener(this);

        nv_s_left=view.findViewById(R.id.nv_s_left);
        nv_s_left.setOnClickListener(this);

        nv_s_right=view.findViewById(R.id.nv_s_right);
        nv_s_right.setOnClickListener(this);

        nv_s_both=view.findViewById(R.id.nv_s_both);
        nv_s_both.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_nv_next:

                if(TextUtils.isEmpty(s_left)){
                   MyUtils.showToast(getActivity(),"请先选择左眼视力");
                   return;
                }
                if(TextUtils.isEmpty(s_right)){
                    MyUtils.showToast(getActivity(),"请先选择右眼视力");
                    return;
                }
                if(TextUtils.isEmpty(s_both)){
                    MyUtils.showToast(getActivity(),"请先选择双眼视力");
                    return;
                }
                if(TextUtils.isEmpty(bs_left)){
                    MyUtils.showToast(getActivity(),"请先选择左眼标识数量");
                    return;
                }
                if(TextUtils.isEmpty(bs_right)){
                    MyUtils.showToast(getActivity(),"请先选择右眼标识数量");
                    return;
                }
                if(TextUtils.isEmpty(bs_both)){
                    MyUtils.showToast(getActivity(),"请先选择双眼标识数量");
                    return;
                }

                getStep2Result();


                break;
            case R.id.nv_bs_left:
                showDialog(2,1);
                break;
            case R.id.nv_bs_right:
                showDialog(2,2);
                break;
            case R.id.nv_bs_both:
                showDialog(2,3);
                break;
            case R.id.nv_s_left:
                showDialog(1,4);
                break;
            case R.id.nv_s_right:
                showDialog(1,5);
                break;
            case R.id.nv_s_both:
                showDialog(1,6);
                break;
        }
    }

    public void showDialog(int type, final int side){
        int layoutId=R.layout.dialog_wheel_one;
        String title="";
        String[] data=null;
        if(type==1){
            title="裸眼视力";
            data = Const.s_data;
        }else {
            title="标识数量";
            data=Const.bs_data;
        }

        DialogBottomView dialogBottomView = new DialogBottomView(getActivity(), layoutId,title,1);
        dialogBottomView.setData(data);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1,String item2,String item3) {
                if(!TextUtils.isEmpty(item2)){
                    switch (side){
                        case 1:
                            bs_left=item2;
                            nv_bs_left.setText(bs_left);
                            register2Bean.setLeft_num(bs_left);
                            break;
                        case 2:
                            bs_right=item2;
                            nv_bs_right.setText(bs_right);
                            register2Bean.setRight_num(bs_right);
                            break;
                        case 3:
                            bs_both=item2;
                            nv_bs_both.setText(item2);
                            register2Bean.setDouble_num(bs_both);
                            break;
                        case 4:
                            s_left=item2;
                            nv_s_left.setText(s_left);
                            register2Bean.setLeft_vision(s_left);
                            break;
                        case 5:
                            s_right=item2;
                            nv_s_right.setText(s_right);
                            register2Bean.setRight_vision(s_right);
                            break;
                        case 6:
                            s_both=item2;
                            nv_s_both.setText(s_both);
                            register2Bean.setDouble_vision(s_both);
                             break;
                    }
                }else {
                    switch (side){
                        case 1:
                            bs_left=Const.bs_data[0];
                            nv_bs_left.setText(bs_left);
                            register2Bean.setLeft_num(bs_left);
                            break;
                        case 2:
                            bs_right=Const.bs_data[0];
                            nv_bs_right.setText(bs_right);
                            register2Bean.setRight_num(bs_right);
                            break;
                        case 3:
                            bs_both=Const.bs_data[0];
                            nv_bs_both.setText(bs_both);
                            register2Bean.setDouble_num(bs_both);
                            break;
                        case 4:
                            s_left=Const.s_data[0];
                            nv_s_left.setText(s_left);
                            register2Bean.setLeft_vision(s_left);
                            break;
                        case 5:
                            s_right=Const.s_data[0];
                            nv_s_right.setText(s_right);
                            register2Bean.setRight_vision(s_right);
                            break;
                        case 6:
                            s_both=Const.s_data[0];
                            nv_s_both.setText(s_both);
                            register2Bean.setDouble_vision(s_both);
                            break;
                    }

                }
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }

    /**
     * 注册第二步请求
     */
    public void getStep2Result(){
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.REGISTER2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if(code==0){
                        RdActivity.start(getActivity());
                    }else {
                        String msg=jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(),code,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("register_code",SaveUtils.getString(KeyUtils.register_code));
                map.put("vision_type",register2Bean.getVision_type());
                map.put("left_vision",register2Bean.getLeft_vision());
                map.put("left_num",register2Bean.getLeft_num());
                map.put("right_vision",register2Bean.getRight_vision());
                map.put("right_num",register2Bean.getRight_num());
                map.put("double_vision",register2Bean.getDouble_vision());
                map.put("double_num",register2Bean.getDouble_num());
                MyUtils.Loge(TAG,"register2Bean:"+register2Bean.toString());
                return map;
            }
        };

        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
