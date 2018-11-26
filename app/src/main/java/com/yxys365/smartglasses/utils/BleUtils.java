package com.yxys365.smartglasses.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.yxys365.smartglasses.activity.BLEActivity;
import com.yxys365.smartglasses.adapter.LvAdapter;
import com.yxys365.smartglasses.fragment.HomeFragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by MaRufei
 * on 2018/6/25.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class BleUtils {
    private static String TAG = "BleUtils";

    private static UUID[] serviceUuids;  // 只扫描指定的服务的设备，可选
    private static String names;   // 只扫描指定广播名的设备，可选
    private static String mac;  // 只扫描指定mac的设备，可选
    private static boolean isAutoConnect;  // 连接时的autoConnect参数，可选，默认false

    public static boolean BoothIsOpen(final HomeFragment activity) {
        initBle(activity.getActivity());
        setScanRule();
        //判断是否支持ble
        if (BleManager.getInstance().isSupportBle()) {
            //判断是否打开了蓝牙
            if (BleManager.getInstance().isBlueEnable()) {
//                if (checkGPSIsOpen(activity.getActivity())) {
//                    return true;
//                }
// else {
//                    showAlertDialog2(activity.getActivity(), "提示", "请您先打开GPS定位服务", "确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                            openGPSSettings(activity.getActivity());
//                        }
//                    });
                    return true;
//                }
            } else {
                //1.通过蓝牙适配器直接打开蓝牙
//                        BleManager.getInstance().enableBluetooth();

                //2.通过startActivityForResult引导界面引导用户打开蓝牙
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, 0x01);
            }
        } else {
            MyUtils.showToast(activity.getActivity(), "当前设备不支持BLE");
        }
        return false;
    }

    /**
     * 判断GPS是否打开
     *
     * @return
     */
    public static boolean checkGPSIsOpen(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 配置扫描规则
     */
    public static void setScanRule() {

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
                .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    public static void initBle(Activity activity){
        //初始化蓝牙
        BleManager.getInstance().init(activity.getApplication());
        //是否显示框架内部日志，重连次数和重连时间间隔，以及操作超时时间。
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);
    }

    private static void openGPSSettings(Activity activity) {
        LocationManager alm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
        {
            MyUtils.Loge(TAG,"定位已打开");
        }

//        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        activity.startActivityForResult(intent,0); //此为设置完成后返回到获取界面
    }

    /**
     * 加密数据
     * @param str
     */
    public static String jiamiData(String str){
        //TODO 设置测试的加密数据
//        Log.e(TAG, "----加密之前的数据:" + str);
        str = str.substring(4, str.length() - 2);
        String str1 = str.substring(0, str.length() / 2);
        String str2 = str.substring(str.length() / 2, str.length());

        String s = "5A5A" + Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str1))) +
                Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str2)));
        s = s + Codeutil.getNum(s);
//        Log.e(TAG, "----加密后的数据:" + s);
        return s;

    }
    /**
     * 含有一个标题、内容、一个按钮的对话框
     **/
    public static void showAlertDialog2(Activity activity,String title, String message,
                                 String positiveText,
                                 DialogInterface.OnClickListener onClickListener) {
        new android.app.AlertDialog.Builder(activity).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onClickListener).setCancelable(false)
                .show();
    }
}
