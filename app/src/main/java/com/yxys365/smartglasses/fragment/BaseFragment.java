package com.yxys365.smartglasses.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yxys365.smartglasses.R;


/**
 * Created by Administrator on 2016-08-15.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    public String TAG = "BaseFragment";
    private AlertDialog dlg2;

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

    /**
     * 加载动画
     */
    public AlertDialog showLoad(Context context, String msg) {
        if(dlg2==null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            final View layout = inflater.inflate(R.layout.dialog_show, null);
            TextView dialog_msg = layout.findViewById(R.id.dialog_msg);
            dialog_msg.setText(msg);
            builder.setView(layout);
            dlg2 = builder.create();
            dlg2.setCanceledOnTouchOutside(false);
        }
        return dlg2;
    }

    public void finishShowLoad(){
        if(dlg2!=null){
            dlg2.dismiss();
        }
    }


    protected void revicedCast() {

    }

    class ResultDataReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            revicedCast();
        }
    }

    /**
     * 含有标题、内容、两个按钮的对话框
     **/
    public void showAlertDialog(String title, String message,
                                String positiveText,
                                DialogInterface.OnClickListener onClickListener,
                                String negativeText,
                                DialogInterface.OnClickListener onClickListener2) {
        new android.app.AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onClickListener)
                .setNegativeButton(negativeText, onClickListener2).setCancelable(false)
                .show();
    }

    /**
     * 含有一个标题、内容、一个按钮的对话框
     **/
    public void showAlertDialog2(String title, String message,
                                 String positiveText,
                                 DialogInterface.OnClickListener onClickListener) {
        new android.app.AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onClickListener).setCancelable(false)
                .show();
    }


}
