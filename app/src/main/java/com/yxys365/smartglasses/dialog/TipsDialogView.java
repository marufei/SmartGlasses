package com.yxys365.smartglasses.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.fragment.HomeFragment;

/**
 * Created by MaRufei on 2017/9/2.
 */

public class TipsDialogView extends Dialog implements View.OnClickListener {


    private Context context;
    private String url;
    private Button dialog_sure;
    private int type;
    private Fragment fragment;
    private int code;
    private String msg;

    public TipsDialogView(Context context, int type) {
        super(context);
        this.context = context;
        this.type=type;
        setContentView(R.layout.dialog_tips);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
    }
    public TipsDialogView(Context context,Fragment fragment, int type,int code,String msg) {
        super(context);
        this.context = context;
        this.fragment=fragment;
        this.code=code;
        this.msg=msg;
        this.type=type;
        setContentView(R.layout.dialog_tips);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
    }

    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);

        findViewById(R.id.dialog_tips).setOnClickListener(this);
        dialog_sure = findViewById(R.id.dialog_sure);
        TextView dialog_tips = findViewById(R.id.dialog_tips);
        switch (type){
            case 1:
                dialog_tips.setText("请先在首页连接设备");
                break;
            case 2:
                dialog_tips.setText("请先激活设备");

                break;
        }
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case 1:
                        if(fragment!=null&&fragment instanceof HomeFragment) {
                            ((HomeFragment) fragment).onActive(code,msg);
                        }
                        break;
                    case 2:
                        if(fragment!=null&&fragment instanceof HomeFragment) {
                            ((HomeFragment) fragment).onConnect();
                        }
                        break;
                }
                dismiss();
            }
        });
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sure:
                dismiss();
                break;
        }

    }

}
