package com.yxys365.smartglasses.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class VolleyUtils {
    private static VolleyUtils volleyUtils;
    private RequestQueue mRequestQueue;

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

        getRequestQueue().add(req);

    }

    public static synchronized VolleyUtils getInstance(Context context){

        if(volleyUtils == null){

            volleyUtils=new VolleyUtils(context);

        }

        return volleyUtils;

    }
}
