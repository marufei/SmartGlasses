package com.yxys365.smartglasses;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaRufei
 * on 2018/6/2.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class MyApplication extends Application {
    /**
     * 打开的activity
     **/
    private static List<Activity> activities;
    private static MyApplication mInstance;

    public static boolean isConnected;

    /**
     * 从硬件获取的设备唯一码
     */
    public static String ONE_CODE = "";
    /**
     * 更新内容
     */
    public static String update_content;
    /**
     * 更新地址
     */
    public static String update_url;


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.app_blue, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "4e79470d47", true);


    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public static void addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList();
        }
        activities.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activities.get(activities.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public static void finishActivity() {
        try {
            Activity activity = activities.get(activities.size() - 1);
            finishActivity(activity);
        } catch (Exception e) {
//            LogUtil.e(TAG,"出错了");
        }

    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */

    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (activities != null) {
            for (Activity activity : activities) {
                if (activity.getClass().equals(cls)) {
                    activities.remove(activity);
                    finishActivity(activity);
                    break;
                }
            }
        }
    }

    /**
     * 查询栈中是否有这个
     *
     * @param cls
     */
    public static boolean QueryActivity(Class<?> cls) {
        if (activities != null) {
            for (Activity activity : activities) {
                if (activity.getClass().equals(cls)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (null != activities.get(i)) {
                activities.get(i).finish();
            }
        }
        activities.clear();
    }

    /**
     * 退出应用程序
     */
    public static void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}

