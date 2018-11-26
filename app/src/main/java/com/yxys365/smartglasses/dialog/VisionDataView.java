package com.yxys365.smartglasses.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.entity.Const;
import com.yxys365.smartglasses.fragment.HomeFragment;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.views.DialogBottomView;

/**
 * Created by MaRufei on 2017/9/2.
 */

public class VisionDataView extends Dialog {


    private Context context;
    private String url;
    private Button dialog_sure;

    private Fragment fragment;
    private int code;
    private String msg;
    private OnVisionListener onVisionListener;
    private TextView dialog_version_left;
    private TextView dialog_version_right;
    private TextView dialog_version_both;
    private TextView dialog_bs_left;
    private TextView dialog_bs_right;
    private TextView dialog_bs_both;
    private Button dialog_vision_cancel;
    private Button dialog_vision_sure;
    /**
     * 标识 左右双 数值
     */
    private String bs_left,bs_right,bs_both;

    /**
     * 视力 左右双 数值
     */
    private String s_left,s_right,s_both;

    public VisionDataView(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_vision_data);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
        OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };

        setOnKeyListener(keylistener);
        setCancelable(false);
    }

    public void setOnVisionListener(OnVisionListener onVisionListener) {
        this.onVisionListener = onVisionListener;
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

       dialog_version_left = findViewById(R.id.dialog_version_left);
       dialog_version_right = findViewById(R.id.dialog_version_right);
       dialog_version_both = findViewById(R.id.dialog_version_both);
       dialog_bs_left = findViewById(R.id.dialog_bs_left);
       dialog_bs_right = findViewById(R.id.dialog_bs_right);
       dialog_bs_both = findViewById(R.id.dialog_bs_both);
       dialog_vision_cancel=findViewById(R.id.dialog_vision_cancel);
       dialog_vision_sure=findViewById(R.id.dialog_vision_sure);
        dialog_version_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onVersionLeft();
                showViewDialog(1,4);
            }
        });
        dialog_version_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onVersionRight();
                showViewDialog(1,5);
            }
        });
        dialog_version_both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onVersionBoth();
                showViewDialog(1,6);
            }
        });
        dialog_bs_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onBsLeft();
                showViewDialog(2,1);
            }
        });
        dialog_bs_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onBsRight();
                showViewDialog(2,2);
            }
        });
        dialog_bs_both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onBsBoth();
                showViewDialog(2,3);
            }
        });

        dialog_vision_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onVisionListener.onCancel();
                dismiss();
            }
        });

        dialog_vision_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(s_left)){
                    MyUtils.showToast(context,"请先选择左眼视力");
                    return;
                }
                if(TextUtils.isEmpty(s_right)){
                    MyUtils.showToast(context,"请先选择右眼视力");
                    return;
                }
                if(TextUtils.isEmpty(s_both)){
                    MyUtils.showToast(context,"请先选择双眼视力");
                    return;
                }
                if(TextUtils.isEmpty(bs_left)){
                    MyUtils.showToast(context,"请先选择左眼标识数量");
                    return;
                }
                if(TextUtils.isEmpty(bs_right)){
                    MyUtils.showToast(context,"请先选择右眼标识数量");
                    return;
                }
                if(TextUtils.isEmpty(bs_both)){
                    MyUtils.showToast(context,"请先选择双眼标识数量");
                    return;
                }
                dismiss();
//                String vision="{\"left_vision\":\""+s_left+"\",\"left_num\":\""+bs_left+"\",\"right_vision\":\""
//                        +s_right+"\",\"right_num\":\""+bs_right+"\",\"double_vision\":\""+s_both+"\",\"double_num\":\""+bs_both+"\"}";
                VisionBean vision=new VisionBean();
                vision.setLeft_vision(s_left);
                vision.setLeft_num(bs_left);
                vision.setRight_vision(s_right);
                vision.setRight_num(bs_right);
                vision.setDouble_vision(s_both);
                vision.setDouble_num(bs_both);
                onVisionListener.onSure(vision);

            }
        });
        show();
    }


    public interface OnVisionListener {
        void onSure(VisionBean vision);
    }

    /**
     * 选择视力标识数
     * @param type
     * @param side
     */
    public void showViewDialog(int type, final int side){
        int layoutId=R.layout.dialog_wheel_one;
        String title="";
        String[] data=null;
        if(type==1){
            title="裸眼视力";
            data = Const.s_data;
        }else {
            title="标识数量";
            data=Const.bs_data;
        }

        DialogBottomView dialogBottomView = new DialogBottomView(context, layoutId,title,1);
        dialogBottomView.setData(data);
        dialogBottomView.setOnEventClickListenner(new DialogBottomView.OnEventClickListenner() {
            @Override
            public void onSure(String item1,String item2,String item3) {
                if(!TextUtils.isEmpty(item2)){
                    switch (side){
                        case 1:
                            bs_left=item2;
                            dialog_bs_left.setText(bs_left);

                            break;
                        case 2:
                            bs_right=item2;
                            dialog_bs_right.setText(bs_right);

                            break;
                        case 3:
                            bs_both=item2;
                            dialog_bs_both.setText(bs_both);

                            break;
                        case 4:
                            s_left=item2;
                            dialog_version_left.setText(s_left);

                            break;
                        case 5:
                            s_right=item2;
                            dialog_version_right.setText(s_right);

                            break;
                        case 6:
                            s_both=item2;
                            dialog_version_both.setText(s_both);

                            break;
                    }
                }else {
                    switch (side){
                        case 1:
                            bs_left=Const.bs_data[0];
                            dialog_bs_left.setText(bs_left);

                            break;
                        case 2:
                            bs_right=Const.bs_data[0];
                            dialog_bs_right.setText(bs_right);

                            break;
                        case 3:
                            bs_both=Const.bs_data[0];
                            dialog_bs_both.setText(bs_both);

                            break;
                        case 4:
                            s_left=Const.s_data[0];
                            dialog_version_left.setText(s_left);

                            break;
                        case 5:
                            s_right=Const.s_data[0];
                            dialog_version_right.setText(s_right);

                            break;
                        case 6:
                            s_both=Const.s_data[0];
                            dialog_version_both.setText(s_both);

                            break;
                    }

                }
            }

            @Override
            public void onCancel() {

            }
        });
        dialogBottomView.showDialog();
    }

    public static class VisionBean{
        private String left_vision;
        private String left_num;
        private String right_vision;
        private String right_num;
        private String double_vision;
        private String double_num;

        public String getLeft_vision() {
            return left_vision;
        }

        public void setLeft_vision(String left_vision) {
            this.left_vision = left_vision;
        }

        public String getLeft_num() {
            return left_num;
        }

        public void setLeft_num(String left_num) {
            this.left_num = left_num;
        }

        public String getRight_vision() {
            return right_vision;
        }

        public void setRight_vision(String right_vision) {
            this.right_vision = right_vision;
        }

        public String getRight_num() {
            return right_num;
        }

        public void setRight_num(String right_num) {
            this.right_num = right_num;
        }

        public String getDouble_vision() {
            return double_vision;
        }

        public void setDouble_vision(String double_vision) {
            this.double_vision = double_vision;
        }

        public String getDouble_num() {
            return double_num;
        }

        public void setDouble_num(String double_num) {
            this.double_num = double_num;
        }
    }

}
