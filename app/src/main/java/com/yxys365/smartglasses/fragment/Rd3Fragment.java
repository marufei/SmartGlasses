package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.RdActivity;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.entity.RegulationBean;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;

/**
 * Created by MaRufei
 * on 2018/6/4.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class Rd3Fragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private Button fragment_rd3_next;
    /**
     * 正向精调，负向精调，正向调节，负向调节
     */
    private TextView fragment_rd3_zjing,fragment_rd3_fjing,fragment_rd3_ztj,fragment_rd3_ftj;
    private String single_data;

    private RegulationBean regulationBean=new RegulationBean();

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_rd3,null);
        initView();
        return view;
    }

    private void initView() {
        fragment_rd3_next=view.findViewById(R.id.fragment_rd3_next);
        fragment_rd3_next.setOnClickListener(this);

        fragment_rd3_zjing=view.findViewById(R.id.fragment_rd3_zjing);
        fragment_rd3_zjing.setOnClickListener(this);
        fragment_rd3_fjing=view.findViewById(R.id.fragment_rd3_fjing);
        fragment_rd3_fjing.setOnClickListener(this);
        fragment_rd3_ztj=view.findViewById(R.id.fragment_rd3_ztj);
        fragment_rd3_ztj.setOnClickListener(this);
        fragment_rd3_ftj=view.findViewById(R.id.fragment_rd3_ftj);
        fragment_rd3_ftj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_rd3_next:
                RdActivity.start(getActivity());
                break;
            case R.id.fragment_rd3_zjing:
                showDialog1(3,3);
                break;
            case R.id.fragment_rd3_fjing:
                showDialog1(4,4);
                break;
            case R.id.fragment_rd3_ztj:
                showDialog1(1,1);
                break;
            case R.id.fragment_rd3_ftj:
                showDialog1(2,2);
                break;
        }
    }

    /**
     * 单个选项
     */
    public void showDialog1(int type, final int side) {
        String title = "";
        if (type == 1) {
            title = "正向调节";

        } else if (type == 2) {
            title = "负向调节";

        } else if(type==3){
            title = "正向精调";

        }else {
            title="负向精调";
        }
        DialogBottomView dialogBottomView = new DialogBottomView(getActivity(), R.layout.dialog_wheel_one, title, 1);
        dialogBottomView.setData(Const.qz_tj);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1, String item2, String item3) {
                if (!TextUtils.isEmpty(item2)) {
                    single_data = item2;
                } else {
                    single_data = Const.qz_tj[0];
                }
                switch (side) {
                    case 1:
                        fragment_rd3_ztj.setText(single_data);
                        regulationBean.setPositive_regulation(single_data);
                        break;
                    case 2:
                        fragment_rd3_ftj.setText(single_data);
                        regulationBean.setNegative_regulation(single_data);
                        break;
                    case 3:
                        fragment_rd3_zjing.setText(single_data);
                        regulationBean.setPositive_fine_regulation(single_data);
                        break;
                    case 4:
                        fragment_rd3_fjing.setText(single_data);
                        regulationBean.setNegative_fine_regulation(single_data);
                        break;
                }

                MyUtils.Loge(TAG, "regulationBean:::" + regulationBean.toString());

                RdActivity.regulation=regulationBean.toString();
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }
}
