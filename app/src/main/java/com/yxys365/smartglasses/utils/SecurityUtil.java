package com.yxys365.smartglasses.utils;

public class SecurityUtil {
    static {
        System.loadLibrary("security");
    }

    //加密
    public static native byte[] encryptJNI(byte[] buf);

    //解密
    public static native byte[] decrpytJNI(byte[] buf);

    //置换 direction 1正向 0逆向
    public static native byte[] desIpTransformJNI(byte[] buf, int direction);
}
