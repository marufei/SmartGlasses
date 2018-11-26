package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.BuyTimeActivity;
import com.yxys365.smartglasses.activity.UserInfo2Activity;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.entity.WearEntity;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.PicassoUtlis;
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
public class PdFragment extends BaseFragment {
    private View view;
    /**
     * 个人信息
     */
    private ImageView yc_header;
    private TextView yc_name, yc_birth, yc_start, yc_all_time, yc_once_time, yc_last_time;
    private TextView yc_left_vision, yc_right_vision, yc_double_vision;
    private TextView yc_left_num, yc_right_num, yc_double_num;
    private TextView yc_left_j_vision, yc_right_j_vision, yc_double_j_vision;
    private TextView yc_left_ax, yc_right_ax, yc_double_ax;
    private TextView yc_left_cyl, yc_right_cyl, yc_double_cyl;
    private TextView yc_left_sph, yc_right_sph, yc_double_sph;
    private TextView yc_vision_time,yc_vision_left,yc_vision_right,yc_vision_double;
    private TextView yc_status;
    private TextView yc_left_vision_num;
    private TextView yc_right_vision_num;
    private TextView yc_double_vision_num;
    private TextView yc_vision_left_num;
    private TextView yc_vision_right_num;
    private TextView yc_vision_double_num;

    @Override
    protected void lazyLoad() {
//        MyUtils.Loge(TAG,"PdFragment------1");
//        if(!TextUtils.isEmpty(MyApplication.ONE_CODE)){
//            MyUtils.Loge(TAG,"PdFragment------2");
//            yc_status.setVisibility(View.VISIBLE);
//            getWear();
//        }else {
//            if(view!=null){
//                yc_status.setVisibility(View.GONE);
//                yc_name.setText("--");
//                yc_birth.setText("--");
//                yc_start.setText("--");
//                yc_all_time.setText("--");
//                yc_once_time.setText("--");
//                yc_last_time.setText("--");
//
//
//                yc_left_vision.setText("--");
//                yc_right_vision.setText("--");
//                yc_double_vision.setText("--");
//                yc_left_vision_num.setText("--");
//                yc_right_vision_num.setText("--");
//                yc_double_vision_num.setText("--");
//
//                yc_left_sph.setText("--");
//                yc_right_sph.setText("--");
//                yc_double_sph.setText("--");
//
//                yc_left_cyl.setText("--");
//                yc_right_cyl.setText("--");
//                yc_double_cyl.setText("--");
//
//                yc_left_ax.setText("--");
//                yc_right_ax.setText("--");
//                yc_double_ax.setText("--");
//
//                yc_left_j_vision.setText("--");
//                yc_right_j_vision.setText("--");
//                yc_double_j_vision.setText("--");
//
//                yc_left_num.setText("--");
//                yc_right_num.setText("--");
//                yc_double_num.setText("--");
//
//                yc_vision_time.setText("--");
//                yc_vision_left.setText("--");
//                yc_vision_right.setText("--");
//                yc_vision_double.setText("--");
//                yc_vision_left_num.setText("--");
//                yc_vision_right_num.setText("--");
//                yc_vision_double_num.setText("--");
//
//            }
//        }
        getWear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_yc, null);
        initViews();
        initData();
        return view;
    }

    private void initViews() {
        yc_header = view.findViewById(R.id.yc_header);
        yc_name = view.findViewById(R.id.yc_name);
        yc_birth = view.findViewById(R.id.yc_birth);
        yc_start = view.findViewById(R.id.yc_start);
        yc_all_time = view.findViewById(R.id.yc_all_time);
        yc_once_time = view.findViewById(R.id.yc_once_time);
        yc_last_time = view.findViewById(R.id.yc_last_time);

        yc_left_vision = view.findViewById(R.id.yc_left_vision);
        yc_right_vision = view.findViewById(R.id.yc_right_vision);
        yc_double_vision = view.findViewById(R.id.yc_double_vision);
        yc_left_vision_num=view.findViewById(R.id.yc_left_vision_num);
        yc_right_vision_num=view.findViewById(R.id.yc_right_vision_num);
        yc_double_vision_num=view.findViewById(R.id.yc_double_vision_num);



        yc_left_num = view.findViewById(R.id.yc_left_num);
        yc_right_num = view.findViewById(R.id.yc_right_num);
        yc_double_num = view.findViewById(R.id.yc_double_num);

        yc_left_j_vision = view.findViewById(R.id.yc_left_j_vision);
        yc_right_j_vision = view.findViewById(R.id.yc_right_j_vision);
        yc_double_j_vision = view.findViewById(R.id.yc_double_j_vision);

        yc_left_ax = view.findViewById(R.id.yc_left_ax);
        yc_right_ax = view.findViewById(R.id.yc_right_ax);
        yc_double_ax = view.findViewById(R.id.yc_double_ax);

        yc_left_cyl = view.findViewById(R.id.yc_left_cyl);
        yc_right_cyl = view.findViewById(R.id.yc_right_cyl);
        yc_double_cyl = view.findViewById(R.id.yc_double_cyl);

        yc_left_sph = view.findViewById(R.id.yc_left_sph);
        yc_right_sph = view.findViewById(R.id.yc_right_sph);
        yc_double_sph = view.findViewById(R.id.yc_double_sph);

        yc_vision_time=view.findViewById(R.id.yc_vision_time);
        yc_vision_left=view.findViewById(R.id.yc_vision_left);
        yc_vision_right=view.findViewById(R.id.yc_vision_right);
        yc_vision_double=view.findViewById(R.id.yc_vision_double);
        yc_vision_left_num=view.findViewById(R.id.yc_vision_left_num);
        yc_vision_right_num=view.findViewById(R.id.yc_vision_right_num);
        yc_vision_double_num=view.findViewById(R.id.yc_vision_double_num);

        yc_status=view.findViewById(R.id.yc_status);
    }

    private void initData() {
//        getWear();
    }

    /**
     * 获取佩戴情况表
     */
    public void getWear() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.DEVICE_WEAR+"?device_code="+ MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);

                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    if(jsonObject1!=null){
                        Gson gson = new Gson();
                        WearEntity wearEntity = gson.fromJson(response, WearEntity.class);
                        if (wearEntity.getCode() == 0) {

                            yc_name.setText(wearEntity.getUser().getName());
                            yc_birth.setText(wearEntity.getUser().getBirthday());
                            yc_start.setText(wearEntity.getUser().getActive_date());
                            yc_all_time.setText(wearEntity.getUser().getTotal_exercise_time());
                            yc_once_time.setText(wearEntity.getUser().getLast_exercise_time());
                            yc_last_time.setText(String.valueOf(wearEntity.getUser().getLeft_days()) + "天");


                            yc_left_vision.setText(wearEntity.getVision().getLeft_vision());
                            yc_right_vision.setText(wearEntity.getVision().getRight_vision());
                            yc_double_vision.setText(wearEntity.getVision().getDouble_vision());
                            yc_left_vision_num.setText(wearEntity.getVision().getLeft_num());
                            yc_right_vision_num.setText(wearEntity.getVision().getRight_num());
                            yc_double_vision_num.setText(wearEntity.getVision().getDouble_num());

                            yc_left_sph.setText(wearEntity.getRefraction().getLeft_sph());
                            yc_right_sph.setText(wearEntity.getRefraction().getRight_sph());
                            yc_double_sph.setText(wearEntity.getRefraction().getDouble_sph());

                            yc_left_cyl.setText(wearEntity.getRefraction().getLeft_cyl());
                            yc_right_cyl.setText(wearEntity.getRefraction().getRight_cyl());
                            yc_double_cyl.setText(wearEntity.getRefraction().getDouble_cyl());

                            yc_left_ax.setText(wearEntity.getRefraction().getLeft_ax());
                            yc_right_ax.setText(wearEntity.getRefraction().getRight_ax());
                            yc_double_ax.setText(wearEntity.getRefraction().getDouble_ax());

                            yc_left_j_vision.setText(wearEntity.getRefraction().getLeft_vision());
                            yc_right_j_vision.setText(wearEntity.getRefraction().getRight_vision());
                            yc_double_j_vision.setText(wearEntity.getRefraction().getDouble_vision());

                            yc_left_num.setText(wearEntity.getRefraction().getLeft_num());
                            yc_right_num.setText(wearEntity.getRefraction().getRight_num());
                            yc_double_num.setText(wearEntity.getRefraction().getDouble_num());

                            if (wearEntity.getCurrent_vision() != null) {
                                yc_vision_time.setText(wearEntity.getCurrent_vision().getCreated_at());
                                yc_vision_left.setText(wearEntity.getCurrent_vision().getLeft_vision());
                                yc_vision_right.setText(wearEntity.getCurrent_vision().getRight_vision());
                                yc_vision_double.setText(wearEntity.getCurrent_vision().getDouble_vision());
                                yc_vision_left_num.setText(wearEntity.getCurrent_vision().getLeft_num());
                                yc_vision_right_num.setText(wearEntity.getCurrent_vision().getRight_num());
                                yc_vision_double_num.setText(wearEntity.getCurrent_vision().getDouble_num());
                            }

                            PicassoUtlis.img(wearEntity.getUser().getAvatar_url(), yc_header);
                        } else {
                            VolleyUtils.dealErrorStatus(getActivity(), wearEntity.getCode(), wearEntity.getMsg());
                        }
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG,"e:"+e.getMessage());
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
}
