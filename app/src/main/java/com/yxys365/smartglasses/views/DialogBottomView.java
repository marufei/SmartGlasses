package com.yxys365.smartglasses.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxys365.smartglasses.R;

import java.util.Arrays;

/**
 * @author: MaRufei
 * @date: 2017/11/20.
 * @Email: 867814102@qq.com
 * @Phone: 132 1358 0912
 * TODO:底部弹窗
 */


public class DialogBottomView extends Dialog {
    private String TAG = "MyAdvertisementView";
    public OnEventClickListenner onEventClickListenner;
    private Context context;
    private String title;
    private int type;

    /**
     * 确定后选择的第一个条目数据
     */
    private String itemData1;
    /**
     * 确定后选择的第二个条目数据
     */
    private String itemData2;
    /**
     * 确定后选择的第三个条目数据
     */
    private String itemData3;
    private String[] str2;
    private String[] str1;
    private String[] str3;


    public DialogBottomView(Context context, int layoutId, String title,int type) {
        super(context);
        this.context = context;
        this.title = title;
        this.type=type;
        setContentView(layoutId);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
    }

    public void setData(String [] str2){
        this.str2=str2;
    }
    public void setData(String [] str1,String[] str2,String[] str3){
        this.str1=str1;
        this.str2=str2;
        this.str3=str3;
    }

    public void setOnEventClickListenner(OnEventClickListenner onEventClickListenner) {
        this.onEventClickListenner = onEventClickListenner;
    }

    public void showDialog() {
        Window window = getWindow();

        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.BOTTOM;
        window.setAttributes(wl);
        show();
        WheelView dialog_wv1 = findViewById(R.id.dialog_wv1);
        WheelView dialog_wv2 = findViewById(R.id.dialog_wv2);
        WheelView dialog_wv3 = findViewById(R.id.dialog_wv3);
        LinearLayout ll_wv1=findViewById(R.id.ll_wv1);
        LinearLayout ll_wv2=findViewById(R.id.ll_wv2);
        LinearLayout ll_wv3=findViewById(R.id.ll_wv3);
        if(type==1){
            ll_wv2.setVisibility(View.VISIBLE);
            ll_wv1.setVisibility(View.GONE);
            ll_wv3.setVisibility(View.GONE);
        }
        if(type==2){
            ll_wv2.setVisibility(View.VISIBLE);
            ll_wv1.setVisibility(View.VISIBLE);
            ll_wv3.setVisibility(View.VISIBLE);
        }

        TextView dialog_wv_cancel = findViewById(R.id.dialog_wv_cancel);
        TextView dialog_wv_sure = findViewById(R.id.dialog_wv_sure);
        TextView dialog_wv_title = findViewById(R.id.dialog_wv_title);
        if (!TextUtils.isEmpty(title)) {
            dialog_wv_title.setText(title);
        }
        if(str1!=null){
            dialog_wv1.setOffset(1);
            dialog_wv1.setItems(Arrays.asList(str1));
            dialog_wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    Log.e(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                    itemData1=item;
                }
            });
        }

        if(str2!=null) {
            dialog_wv2.setOffset(1);
            dialog_wv2.setItems(Arrays.asList(str2));
            dialog_wv2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    Log.e(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                    itemData2 = item;
                }
            });
        }
        if(str3!=null) {
            dialog_wv3.setOffset(1);
            dialog_wv3.setItems(Arrays.asList(str3));
            dialog_wv3.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    Log.e(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                    itemData3 = item;
                }
            });
        }

        dialog_wv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onEventClickListenner.onCancel();
            }
        });
        dialog_wv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onEventClickListenner.onSure(itemData1,itemData2,itemData3);
            }
        });


    }

    public interface OnEventClickListenner {
        void onSure(String item1,String item2,String item3);

        void onCancel();
    }
}
