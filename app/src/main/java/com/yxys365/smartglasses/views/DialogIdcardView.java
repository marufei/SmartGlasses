package com.yxys365.smartglasses.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.DialogLvAdapter;
import com.yxys365.smartglasses.entity.AutoPlanEntity;
import com.yxys365.smartglasses.utils.MyUtils;

import java.util.List;


/**
 * Created by MaRufei on 2017/9/2.
 */

/**
 * @author: MaRufei
 * @date: 2017/11/20.
 * @Email: 867814102@qq.com
 * @Phone: 132 1358 0912
 * TODO:中间弹窗
 */


public class DialogIdcardView extends Dialog {
    private Context context;
    private List<AutoPlanEntity.PlansBean> list;

    private OnCardCallback onCardCallback;


    public DialogIdcardView(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_id_card);
        //设置点击布局外Dialog是否消失
        setCanceledOnTouchOutside(false);
    }

    public void setOnCardCallback(OnCardCallback onCardCallback) {
        this.onCardCallback = onCardCallback;
    }

    public void setData(List<AutoPlanEntity.PlansBean> list) {
        this.list = list;
    }


    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.width = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
        Button dialog_card_save = findViewById(R.id.dialog_card_save);
        final EditText dialog_card_number = findViewById(R.id.dialog_card_number);
        dialog_card_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dialog_card_number.getText().toString())){
                    MyUtils.showToast(context,"请先输入身份证号");
                    return;
                }
               onCardCallback.onCardclick(dialog_card_number.getText().toString().trim());
                dismiss();
            }
        });

    }

    public interface OnCardCallback {
        void onCardclick(String planId);
    }
}
