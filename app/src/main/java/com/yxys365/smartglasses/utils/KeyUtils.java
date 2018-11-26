package com.yxys365.smartglasses.utils;

import android.content.Intent;

/**
 * Created by MaRufei
 * on 2018/6/24.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class KeyUtils {
    private static String TAG="KeyUtils";
    public static final String register_code="register_code";
    public static final String access_token="access_token";
    public static final String token_type="token_type";
    public static final String expires_in="expires_in";
    public static final String tel="phone";
    public static final String pwd="pwd";
    /**
     * 当前设备的计划id,用于异常操作，再一次打开APP的时候返回数据的时候上传给服务器
     */
    public static final String plan_id="plan_id";

    public static final String plan_index="plan_index";

    /**
     * 自动方案需要传的，用于异常断开，重新连接后上传服务
     */
    public static final String section_id="section_id";





    /**
     * 获取唯一码
     */
//    public static final String DEVICE_00EE="5a5a00ee001314151617202122232425262727";
    public static final String DEVICE_00EE="5a5a00ee00"+ToolUtils.randomHexString(28);

    /**
     * 确认成功操作
     */
//    public static final String DEVICE_0066="5a5a006600131415161720212223242526279f";
    public static final String DEVICE_0066="5a5a006600"+ToolUtils.randomHexString(28);

    /**
     * 确认取消操作
     */
//    public static final String DEVICE_0055="5a5a005500131415161720212223242526278e";
    public static final String DEVICE_0055="5a5a005500"+ToolUtils.randomHexString(28);

    /**
     * 申请启动电机
     */
//    public static final String DEVICE_00E0="5a5a00e00213141516170100222324252627db";
    public static final String DEVICE_00E0="5a5a00e00213141516170100222324252627db";


    /**
     * 申请停止电机
     */
//    public static final String DEVICE_00E1="5a5a00e100131415161720212223242526271a";
    public static final String DEVICE_00E1="5a5a00e100"+ToolUtils.randomHexString(28);


    /**
     * 电机加速
     */
//    public static final String DEVICE_00E2="5a5a00e200131415161720212223242526271b";
    public static final String DEVICE_00E2="5a5a00e200"+ToolUtils.randomHexString(28);


    /**
     * 电机减速
     */
//    public static final String DEVICE_00E3="5a5a00e300131415161720212223242526271c";
    public static final String DEVICE_00E3="5a5a00e300"+ToolUtils.randomHexString(28);


    /**
     * 时间到申请机器停止指令
     */
//    public static final String DEVICE_00E4="5a5a00e400131415161720212223242526271d";
    public static final String DEVICE_00E4="5a5a00e400"+ToolUtils.randomHexString(28);


    /**
     * 申请裸眼视力报表
     */
    public static final String DEVICE_00E5="5a5a00e500131415161720212223242526271d";


    /**
     * 选择方案之后，申请电机启动
     * @param id
     * @return
     */
    public static String send_OOEO(String id){
        id= Integer.toHexString(Integer.valueOf(id));
        if(id.length()==1){
            id="0"+id;
        }
//        MyUtils.Loge(TAG,"id:"+id);
        String device_id="5a5a00e0021314151617"+id+"00222324252627db";
//        String device_id="5a5a00e002"+ToolUtils.randomHexString(10)+id.toUpperCase()+ToolUtils.randomHexString(16);
//        MyUtils.Loge(TAG,"device_id:"+device_id);
//        String device_id="5a5a00e002 1314151617 01 00222324252627db";5a5a00e002 082fa50c9a 02 edcc6ae3e01158a1
        return device_id;
    }
}
