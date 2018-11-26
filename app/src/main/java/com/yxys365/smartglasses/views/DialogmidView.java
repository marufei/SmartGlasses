package com.yxys365.smartglasses.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.DialogLvAdapter;
import com.yxys365.smartglasses.entity.AutoPlanEntity;
import com.yxys365.smartglasses.fragment.HomeFragment;

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


public class DialogmidView extends Dialog {
    private Context context;
    private List<AutoPlanEntity.PlansBean> list;

    private MyPlanCallback myPlanCallback;
    /**
     * 自动方案ID
     */
    private String planId;


    public DialogmidView(Context context, int layoutId) {
        super(context);
        this.context = context;
        setContentView(layoutId);
        //设置点击布局外Dialog是否消失
        setCanceledOnTouchOutside(false);
    }

    public void setMyPlanCallback(MyPlanCallback myPlanCallback) {
        this.myPlanCallback = myPlanCallback;
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
        TextView dialog_plan_sure = findViewById(R.id.dialog_plan_sure);
        TextView dialog_plan_close = findViewById(R.id.dialog_plan_close);
        ListView dialog_plan_lv = findViewById(R.id.dialog_plan_lv);
        final DialogLvAdapter adapter = new DialogLvAdapter(context, list);
        dialog_plan_lv.setAdapter(adapter);

        dialog_plan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(list.get(i).isSeclct()){
                    list.get(i).setSeclct(false);
                }else {

                    for (int j=0;j<list.size();j++){
                        if(i==j){
                            list.get(j).setSeclct(true);
                            planId = list.get(i).getId();
                        }else {
                            list.get(j).setSeclct(false);
                        }
                    }
                }

//
//                for (int j = 0; j < list.size(); j++) {
//                    if (i == j) {
//                        list.get(i).setSeclct(true);
//                        planId = list.get(i).getId();
//                    } else {
//                        list.get(i).setSeclct(false);
//                    }
//                }
                adapter.notifyDataSetChanged();
            }
        });
        dialog_plan_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(planId)) {
                    dismiss();
                }
            }
        });
        dialog_plan_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(planId)) {
                    dismiss();
                    myPlanCallback.planCallback(planId);
                }
            }
        });
    }

    public interface MyPlanCallback {
        void planCallback(String planId);
    }
}
