package com.yxys365.smartglasses.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.LoginActivity;
import com.yxys365.smartglasses.dialog.TipsDialogView;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class VolleyUtils {
    private static VolleyUtils volleyUtils;
    private RequestQueue mRequestQueue;
    private static String TAG="VolleyUtils";



    private static Context mCtx;
    private static final int TIME_OUT = 10 * 1000;//设置超时时间

    private VolleyUtils(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQueue = getRequestQueue();
    }

    public static void setTimeOut(StringRequest stringRequest) {
        if (stringRequest != null) {
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().getCache().clear();
        getRequestQueue().add(req);

    }

    public static synchronized VolleyUtils getInstance(Context context) {

        if (volleyUtils == null) {

            volleyUtils = new VolleyUtils(context);

        }

        return volleyUtils;

    }

    public static void dealErrorStatus(Context context, int code, String msg) {
        switch (code) {
            case 401:
                LoginActivity.start(context);
                MyApplication.finishAllActivity();
                break;
            case 902:
                new TipsDialogView(context, 1).showDialog();
                break;
            case 901:
                new TipsDialogView(context, 2).showDialog();
                break;
            default:
                MyUtils.Loge(TAG,"msg::"+msg);
                MyUtils.showToast(context,msg);
                break;
        }

    }


}
