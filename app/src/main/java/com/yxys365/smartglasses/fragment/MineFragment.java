package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.BuyTimeActivity;
import com.yxys365.smartglasses.activity.ExpreciseActivity;
import com.yxys365.smartglasses.activity.NvFormActivity;
import com.yxys365.smartglasses.activity.NvListActivity;
import com.yxys365.smartglasses.activity.OcularActivity;
import com.yxys365.smartglasses.activity.UseDeviceActivity;

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

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_mine,null);
        initViews();
        return view;
    }

    private void initViews() {
        mine_ll1=view.findViewById(R.id.mine_ll1);
        mine_ll1.setOnClickListener(this);

        mine_ll2=view.findViewById(R.id.mine_ll2);
        mine_ll2.setOnClickListener(this);

        mine_ll3=view.findViewById(R.id.mine_ll3);
        mine_ll3.setOnClickListener(this);

        mine_ll4=view.findViewById(R.id.mine_ll4);
        mine_ll4.setOnClickListener(this);

        mine_ll5=view.findViewById(R.id.mine_ll5);
        mine_ll5.setOnClickListener(this);

        mine_ll6=view.findViewById(R.id.mine_ll6);
        mine_ll6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        }
    }
}
