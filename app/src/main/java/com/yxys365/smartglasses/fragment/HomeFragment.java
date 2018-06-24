package com.yxys365.smartglasses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.HomeLvAdapter;

/**
 * Created by MaRufei
 * on 2018/6/6.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private ListView home_lv;
    private HomeLvAdapter adapter;
    private LinearLayout home_ll_connecting;
    private LinearLayout home_ll_connected;
    private TextView home_tv_cancel;

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_home,null);
        initViews();
        initEvent();
        return view;
    }

    private void initEvent() {
        home_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                home_lv.setVisibility(View.GONE);
                home_ll_connecting.setVisibility(View.VISIBLE);
                home_ll_connected.setVisibility(View.GONE);

            }
        });
    }

    private void initViews() {

        home_lv=view.findViewById(R.id.home_lv);
        home_ll_connecting=view.findViewById(R.id.home_ll_connecting);
        home_ll_connected=view.findViewById(R.id.home_ll_connected);
        home_tv_cancel=view.findViewById(R.id.home_tv_cancel);
        home_tv_cancel.setOnClickListener(this);

        home_lv.setVisibility(View.VISIBLE);
        home_ll_connecting.setVisibility(View.GONE);
        home_ll_connected.setVisibility(View.GONE);

        adapter=new HomeLvAdapter(getActivity());
        home_lv.setAdapter(adapter);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_tv_cancel:
                home_lv.setVisibility(View.GONE);
                home_ll_connecting.setVisibility(View.GONE);
                home_ll_connected.setVisibility(View.VISIBLE);
                break;
        }
    }
}
