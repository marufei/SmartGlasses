package com.yxys365.smartglasses.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


/**
 * Created by Administrator on 2016-08-15.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    public String TAG = "BaseFragment";

    //setUserVisibleHint  adapter中的每个fragment切换的时候都会被调用，如果是切换到当前页，那么isVisibleToUser==true，否则为false
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            show();
        } else {
            hidden();
        }
        super.onHiddenChanged(hidden);
    }

    protected void show() {

    }

    protected void hidden() {

    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();


    @Override
    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

    }


    protected void revicedCast() {

    }

    class ResultDataReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            revicedCast();
        }
    }
}
