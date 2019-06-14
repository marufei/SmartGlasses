package com.yxys365.smartglasses.fragment;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.google.gson.Gson;
import com.yxys365.smartglasses.MyApplication;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.activity.GlassesReplaceActivity;
import com.yxys365.smartglasses.activity.LoginActivity;
import com.yxys365.smartglasses.activity.MainActivity;
import com.yxys365.smartglasses.adapter.HomeLvAdapter;
import com.yxys365.smartglasses.configs.HttpsAddress;
import com.yxys365.smartglasses.dialog.VisionDataView;
import com.yxys365.smartglasses.entity.AutoPlanEntity;
import com.yxys365.smartglasses.entity.ErrorEntity;
import com.yxys365.smartglasses.entity.PlanDetailsEntity;
import com.yxys365.smartglasses.entity.PlanEntity;
import com.yxys365.smartglasses.entity.RemindEntity;
import com.yxys365.smartglasses.entity.UpdateVersionEntity;
import com.yxys365.smartglasses.utils.BleUtils;
import com.yxys365.smartglasses.utils.ClickUtils;
import com.yxys365.smartglasses.utils.Codeutil;
import com.yxys365.smartglasses.utils.KeyUtils;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SaveUtils;
import com.yxys365.smartglasses.utils.SecurityUtil;
import com.yxys365.smartglasses.utils.ToolUtils;
import com.yxys365.smartglasses.utils.UpdateManger;
import com.yxys365.smartglasses.utils.VolleyUtils;
import com.yxys365.smartglasses.views.DialogmidView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by MaRufei
 * on 2018/6/6.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ListView home_lv;
    private HomeLvAdapter adapter;
    private LinearLayout home_ll_connecting;
    private LinearLayout home_ll_connected;
    private TextView home_tv_cancel;


    private BluetoothGatt gattBLE;

    /**
     * 搜索到的蓝牙设备
     */
    private List<BleDevice> list = new ArrayList<>();

    /**
     * 搜索到的蓝牙Mac地址
     */
    private List<String> listMac = new ArrayList<>();
    private UUID uuid_service;
    private UUID uuid_chara;
    private UUID notifyChara;
    /**
     * 连接成功时显示的 设备mac地址,连接状态
     */
    private TextView fragment_ble_mac, fragment_ble_type;
    private UUID sendChara;

    private BleDevice myBleDevice;
    private ImageView home_player;
    /**
     * 电机是否在转
     */
    private boolean isRunning = false;
    private ImageView home_add, home_reduce;
    private ImageView home_privous, home_next;
    private ImageView home_stop;
    private TextView home_error_connect;
    private RelativeLayout home_error;
    /**
     * 方案名字，方案时间，倒计时时间
     */
    private TextView home_plan_name, home_plan_time, home_plan_timer;
    /**
     * 普通方案集合
     */
    private List<PlanEntity.CommonPlansBean> listPlan = new ArrayList<>();
    /**
     * 普通方案索引
     */
    private int planIndex = 0;
    /**
     * 是否是第一次激活设备
     */
    private boolean isFirstActive = true;

    private BleDevice device;

    private Vibrator vibrator;
    private long VIBRATOR_TIME = 100;

    /**
     * 倒计时
     */
    private CountDownTimer timer;
    /**
     * 倒计时时间
     */
    private long leftTime;
    /**
     * 自动方案各阶段集合
     */
    private List<PlanEntity.AutoPlanBean.SectionsBean> autoListPlan = new ArrayList<>();


    /**
     * 自动方案角标
     */
    private int autoIndex = 0;
    /**
     * 自动方案名字
     */
    private String auto_plan_name;
    /**
     * 是否是自动方案的自动暂停
     */
    private boolean isAutoCancel = false;

    /**
     * 上传视力报表view
     */
    private VisionDataView visionDataView;
    /**
     * 是否是关闭蓝牙
     */
    private boolean isClose = false;
    /**
     * 是否点击开始连接蓝牙
     */
    private boolean isTouch = false;


    @Override
    protected void lazyLoad() {
        getVersion();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_home, null);
        initViews();
        initEvent();
        initData();
        initBle();
        return view;
    }

    private void initData() {

        if (BleUtils.BoothIsOpen(this)) {
            home_lv.setVisibility(View.VISIBLE);
            home_ll_connecting.setVisibility(View.GONE);
            home_ll_connected.setVisibility(View.GONE);
            home_error.setVisibility(View.GONE);
        }
//        getPlan();

        //获取剩余时间，更换目镜提示
        remind();

        //todo 上传裸眼视力弹窗 没有上传就上传一下 上传过就跳过此步
        if (SaveUtils.getInt(KeyUtils.vision_upload) == 0) {
            showVisionDialog();
        }


    }

    private void initViews() {

        vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);


        home_lv = view.findViewById(R.id.home_lv);
        adapter = new HomeLvAdapter(getActivity());
        home_lv.setAdapter(adapter);
        home_ll_connecting = view.findViewById(R.id.home_ll_connecting);
        home_ll_connected = view.findViewById(R.id.home_ll_connected);
        home_tv_cancel = view.findViewById(R.id.home_tv_cancel);
        home_tv_cancel.setOnClickListener(this);

        fragment_ble_mac = view.findViewById(R.id.fragment_ble_mac);
        fragment_ble_type = view.findViewById(R.id.fragment_ble_type);

        home_player = view.findViewById(R.id.home_player);
        home_player.setOnClickListener(this);
        home_add = view.findViewById(R.id.home_add);
        home_add.setOnClickListener(this);
        home_reduce = view.findViewById(R.id.home_reduce);
        home_reduce.setOnClickListener(this);

        home_privous = view.findViewById(R.id.home_privous);
        home_privous.setOnClickListener(this);
        home_next = view.findViewById(R.id.home_next);
        home_next.setOnClickListener(this);

        home_stop = view.findViewById(R.id.home_stop);
        home_stop.setOnClickListener(this);

        home_error_connect = view.findViewById(R.id.home_error_connect);
        home_error_connect.setOnClickListener(this);
        home_error = view.findViewById(R.id.home_error);

        home_plan_name = view.findViewById(R.id.home_plan_name);
        home_plan_time = view.findViewById(R.id.home_plan_time);
        home_plan_timer = view.findViewById(R.id.home_plan_timer);

        home_add.setSelected(false);
        home_reduce.setSelected(false);
        home_next.setSelected(true);
        home_privous.setSelected(true);

    }

    /**
     * step1:初始化蓝牙
     */
    public void initBle() {

        if (BleUtils.BoothIsOpen(this)) {

            //如果保存的有蓝牙设备，就直接从缓存里取，没有保存的话，就搜索
//            if (SpUtils.getObject(getActivity(), new TypeToken<BleDevice>() {
//            }.getType()) == null) {
//                showLoad(getActivity(), "正在初始化蓝牙").show();
            startScan();
//            } else {
//                home_lv.setVisibility(View.VISIBLE);
//                home_ll_connecting.setVisibility(View.GONE);
//                home_ll_connected.setVisibility(View.GONE);
//                home_error.setVisibility(View.GONE);
//
//                list.clear();
//                list.add((BleDevice) SpUtils.getObject(getActivity(), new TypeToken<BleDevice>() {
//                }.getType()));
//                adapter.setList(list);
//                adapter.notifyDataSetChanged();
//            }
        }
    }

    /**
     * step2:扫描蓝牙设备
     */
    private void startScan() {
        list.clear();
        listMac.clear();
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {

            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
                MyUtils.Loge(TAG, "执行onLeScan:" + bleDevice.getMac());
                finishShowLoad();
                if (listMac.size() > 0) {
                    for (int i = 0; i < listMac.size(); i++) {
                        if (listMac.contains(bleDevice.getMac())) {
//                             MyUtils.Loge(TAG, "listMac.size():" + listMac.size());
                        } else {
                            if (!TextUtils.isEmpty(bleDevice.getName()) && bleDevice.getName().contains("ALLVIEW_")) {
                                listMac.add(bleDevice.getMac());
                                list.add(bleDevice);
                            }

                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(bleDevice.getName()) && bleDevice.getName().contains("ALLVIEW_")) {
                        listMac.add(bleDevice.getMac());
                        list.add(bleDevice);
                    }
                }
                if (list.size() > 0) {
                    home_lv.setVisibility(View.VISIBLE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.GONE);
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();


                } else {
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                MyUtils.Loge(TAG, "bleDevice:" + bleDevice.getMac());
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {

                MyUtils.Loge(TAG, "最终搜索到的蓝牙设备:" + list.size());

                if (list.size() > 0) {
                    home_lv.setVisibility(View.VISIBLE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.GONE);
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();


                } else {
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * step3:点击连接蓝牙操作
     */
    private void initEvent() {
        home_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showLoad(getActivity(), "正在连接蓝牙...").show();
//                home_lv.setVisibility(View.GONE);
//                home_ll_connecting.setVisibility(View.VISIBLE);
//                home_ll_connected.setVisibility(View.GONE);
//                home_error.setVisibility(View.GONE);
                if (!isTouch) {
                    //连接蓝牙
                    if (!BleManager.getInstance().isConnected(list.get(i))) {
//                    BleManager.getInstance().cancelScan();
                        connectBLE(list.get(i));
                        //连接到的设备
                        device = list.get(i);
                    }
                    isTouch = true;
                }


            }
        });
    }

    /**
     * step4:连接蓝牙设备
     *
     * @param bleDevice
     */
    public void connectBLE(BleDevice bleDevice) {
        if (!BleManager.getInstance().isConnected(bleDevice)) {
            BleManager.getInstance().cancelScan();
            BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    MyUtils.Loge(TAG, "onStartConnect");
                }

                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {
                    MyUtils.Loge(TAG, "onConnectFail");

                    MyApplication.isConnected = false;
                    isTouch = false;
                    Toast.makeText(getActivity(), "连接蓝牙失败", Toast.LENGTH_LONG).show();
                    finishShowLoad();
                    fragment_ble_type.setText("未连接");
                    fragment_ble_type.setTextColor(getActivity().getResources().getColor(R.color.gray_ee));

                    //设置断开连接
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.VISIBLE);

                    //首页连接的数据
                    fragment_ble_mac.setText("设备：--");
                    fragment_ble_type.setText("未连接");
                }

                @Override
                public void onConnectSuccess(final BleDevice bleDevice, BluetoothGatt gatt, int status) {

                    //上次异常保存的planIndex;
                    planIndex = SaveUtils.getInt(KeyUtils.plan_index);
                    MyUtils.Loge(TAG, "异常保存的角标：" + planIndex);
                    MyUtils.Loge(TAG, "异常保存的方案ID：" + SaveUtils.getString(KeyUtils.plan_id));

                    finishShowLoad();
                    isTouch = false;
                    Toast.makeText(getActivity(), "连接蓝牙成功", Toast.LENGTH_LONG).show();
                    //告诉首页连接成功
                    Message msg = Message.obtain();
                    msg.what = 1;
                    MainActivity.mHandler.sendMessage(msg);
                    MyApplication.isConnected = true;
                    myBleDevice = bleDevice;
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.VISIBLE);
                    home_error.setVisibility(View.GONE);
                    fragment_ble_mac.setText("设备：" + bleDevice.getMac());
                    fragment_ble_type.setText("已连接");
                    fragment_ble_type.setTextColor(getActivity().getResources().getColor(R.color.gray_3));


                    gattBLE = gatt;
                    List<BluetoothGattService> serviceList = gatt.getServices();
                    for (BluetoothGattService service : serviceList) {
                        uuid_service = service.getUuid();

//                         MyUtils.Loge(TAG, "uuid_service:" + uuid_service);
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (BluetoothGattCharacteristic characteristic : characteristicList) {
                            uuid_chara = characteristic.getUuid();
                            if (uuid_chara.toString().equals("6e400003-b5a3-f393-e0a9-e50e24dcca9e")) {
                                notifyChara = uuid_chara;
                            }
                            if (uuid_chara.toString().equals("6e400002-b5a3-f393-e0a9-e50e24dcca9e")) {
                                sendChara = uuid_chara;
                            }
//                             MyUtils.Loge(TAG, "notifyChara:" + notifyChara);
//                             MyUtils.Loge(TAG, "sendChara:" + sendChara);
                        }

                    }
                    if (myBleDevice != null && uuid_service != null && notifyChara != null) {

                        try {
                            Thread.currentThread().sleep(200);//阻断100毫秒
                            nitifyData();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    MyUtils.Loge(TAG, "onDisConnected");
                    //将当前计划id保存到缓存
                    if (listPlan != null && listPlan.size() > planIndex && listPlan.get(planIndex) != null) {
                        SaveUtils.setInt(KeyUtils.plan_index, planIndex);
                        if (TextUtils.isEmpty(listPlan.get(planIndex).getId())) {
                            SaveUtils.setString(KeyUtils.plan_id, listPlan.get(planIndex).getId());
                        }
                    }


                    //告诉首页连接断开
                    Message msg = Message.obtain();
                    msg.what = 0;
                    MainActivity.mHandler.sendMessage(msg);

                    //如果断开蓝牙就 先暂停上传时间，然后
                    isClose = true;
                    updateLeftTime();
                    //设置断开连接
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.GONE);
                    home_error.setVisibility(View.VISIBLE);

                    //首页连接的数据
                    fragment_ble_mac.setText("设备：--");
                    fragment_ble_type.setText("未连接");


                }
            });
        }
    }


    /**
     * step5:发送特定指令
     */
    public void sendData(final String code) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                    if (myBleDevice != null && uuid_service != null && sendChara != null) {
                        writeData(BleUtils.jiamiData(code));
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * step6:APP通过write传送数据给蓝牙
     */
    public void writeData(String s) {
//        MyUtils.Loge(TAG, "send_chara:" + sendChara.toString() + "---s:" + s);
        if (!TextUtils.isEmpty(s)) {
            BleManager.getInstance().write(
                    myBleDevice,
                    uuid_service.toString(),
                    sendChara.toString(),
                    Codeutil.hexStringToByte(s),
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            // 发送数据到设备成功（分包发送的情况下，可以通过方法中返回的参数可以查看发送进度）
//                            Toast.makeText(getActivity(), "发送数据成功", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            // 发送数据到设备失败
                            MyUtils.Loge(TAG, "onWriteFailure-------------");
//                            Toast.makeText(getActivity(), "发送数据失败：" + exception.getDescription(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            MyUtils.Loge(TAG, "s是空值");
        }
    }

    /**
     * step7：APP通过notify接收蓝牙数据
     */
    public void nitifyData() {
        BleManager.getInstance().notify(
                myBleDevice,
                uuid_service.toString(),
                notifyChara.toString(),
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
//                         MyUtils.Loge(TAG, "---onNotifySuccess");
//                        Toast.makeText(getActivity(), "接收数据成功", Toast.LENGTH_LONG).show();
                        //发送申请机器码
                        sendData(KeyUtils.DEVICE_00EE);
                        showLoad(getActivity(), "初始化蓝牙");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
//                         MyUtils.Loge(TAG, "---onNotifyFailure:" + exception.getDescription());
                        Toast.makeText(getActivity(), "接收数据失败", Toast.LENGTH_LONG).show();
                        //如果notify失败就断开蓝牙
                        if (BleManager.getInstance().isConnected(device)) {
                            BleManager.getInstance().disconnect(device);
                        }
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
//                         MyUtils.Loge(TAG, "---onCharacteristicChanged");
                        if (data != null) {
//                             MyUtils.Loge(TAG, "接收的数据：" + Codeutil.bytesToHexString(data));
                            //TODO 解密接收到的数据
                            String ss = Codeutil.bytesToHexString(data);
                            ss = ss.substring(4, ss.length() - 2);
                            String str11 = ss.substring(0, ss.length() / 2);
                            String str22 = ss.substring(ss.length() / 2, ss.length());
                            String notify_data = Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str11))) +
                                    Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str22)));
                            MyUtils.Loge(TAG, "----解密后的数据notify_data:" + notify_data);

//                            Toast.makeText(getActivity(), "接收到解密后的数据notify_data：" + notify_data, Toast.LENGTH_LONG).show();
                            operateOrder(notify_data);
                        } else {
//                            Toast.makeText(getActivity(), "接收数据为空值", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    /**
     * step8:根据返回的数据进行操作
     *
     * @param notify_data
     */
    public void operateOrder(String notify_data) {
        try {
            Thread.currentThread().sleep(100);//阻断100毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(notify_data)) {

            //申请机器码成功
            if (notify_data.startsWith("00FF")) {
                String notifyData = notify_data.substring(4, 6);
                if (notify_data.startsWith("0")) {
                    notifyData = notifyData.substring(1, 2);
                }
                MyUtils.Loge(TAG, "申请机器码的接收" + notifyData);

                String notifyData2 = notify_data.substring(notify_data.length() / 2, notify_data.length() / 2 + Integer.valueOf(notifyData) * 2);
                MyUtils.Loge(TAG, "设备唯一码：" + notifyData2);
                //保存设备唯一码
                MyApplication.ONE_CODE = notifyData2;

                deviceConnect();
            }

            //启动电机成功
            if (notify_data.startsWith("006601")) {
                isRunning = true;
                home_player.setImageResource(R.drawable.vector_drawable_pause);
                home_add.setSelected(true);
                home_reduce.setSelected(true);
                home_next.setSelected(false);
                home_privous.setSelected(false);
                runTimer(leftTime);
            }

            if (notify_data.startsWith("00FE")) {
                if (notify_data.startsWith("00FE04")) {
                    //发送0066确认
                    MyUtils.Loge(TAG, "发送0066确认");
                    isRunning = false;
                    home_player.setImageResource(R.drawable.vector_drawable_stop);
                    home_add.setSelected(false);
                    home_reduce.setSelected(false);
                    home_next.setSelected(true);
                    home_privous.setSelected(true);
                    //发送0066确认
                    sendData(KeyUtils.DEVICE_0066);
                    //时间到接收到电机返回的指令，然后手动输入视力数据
//                    showVisionDialog();
                    updateData();
                    return;
                }
                //上次异常返回的数据
                if (notify_data.startsWith("00FE06")) {
                    //是否收到00FE06 或者00FE05

                    String deivce_stauts = notify_data.substring(20, 22);
                    MyUtils.Loge(TAG, "机器状态：" + deivce_stauts);

                    String min_data = notify_data.substring(24, 26);
//                    MyUtils.Loge(TAG, "分钟数：" + min_data);
//                    MyUtils.Loge(TAG, "原始数据：" + notify_data);
                    String sec_data = notify_data.substring(26, 28);
//                    MyUtils.Loge(TAG, "秒数：" + sec_data);
//                    MyUtils.Loge(TAG, "原始数据：" + notify_data);
                    Integer.valueOf(min_data, 16);
                    Integer.valueOf(sec_data, 16);
//                    MyUtils.Loge(TAG, "十进制分钟数：" + Integer.valueOf(min_data, 16));
//                    MyUtils.Loge(TAG, "十进制秒数：" + Integer.valueOf(sec_data, 16));
                    int left_error_time = Integer.valueOf(min_data, 16) * 60 + Integer.valueOf(sec_data, 16);
                    MyUtils.Loge(TAG, "方案id:" + SaveUtils.getString(KeyUtils.plan_id));
                    MyUtils.Loge(TAG, "运行时间：" + left_error_time);
                    if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.plan_id)) && deivce_stauts.equals("01")) {
                        updateErrorLeftTime(SaveUtils.getString(KeyUtils.plan_id), String.valueOf(left_error_time), deivce_stauts, notify_data);
                    }

                }
                if (notify_data.startsWith("00FE05")) {
                    MyUtils.Loge(TAG, "接收到的05数据：" + notify_data);
                    String deivce_stauts = notify_data.substring(20, 22);
                    MyUtils.Loge(TAG, "机器状态：" + deivce_stauts);
                    String time_data = notify_data.substring(22, 24);
                    MyUtils.Loge(TAG, "机器方案总时长-16进制：" + time_data);
                    int run_time = Integer.valueOf(time_data, 16) * 60;
                    MyUtils.Loge(TAG, "方案id:" + SaveUtils.getString(KeyUtils.plan_id));
                    MyUtils.Loge(TAG, "上传的方案总时长-10进制：" + run_time);
                    if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.plan_id))) {
                        updateErrorLeftTime(SaveUtils.getString(KeyUtils.plan_id), String.valueOf(run_time), deivce_stauts, notify_data);
                    }
                }
                sendData(KeyUtils.DEVICE_0066);
            }

            if (notify_data.startsWith("0055")) {
                MyUtils.showToast(getActivity(), "本次操作失败");
            }

            if (notify_data.startsWith("006600")) {

                //TODO 如果是自动方案且是某一阶段结束后发出的暂停，就再发送开启电机的指令
                if (planIndex == 0 && isAutoCancel) {
                    sendData(KeyUtils.send_OOEO(autoListPlan.get(autoIndex).getSection_id()));

                }
            }

            // TODO 由于电机返回的数据不稳定，故，自行上传
           /* if (notify_data.startsWith("00FC03")) {
                MyUtils.Loge(TAG, "接收裸眼视力");
                String notifyData = notify_data.substring(4, 6);
                if (notify_data.startsWith("0")) {
                    notifyData = notifyData.substring(1, 2);
                }
                MyUtils.Loge(TAG, "申请机器码的接收" + notifyData);

                String notifyData2 = notify_data.substring(notify_data.length() / 2, notify_data.length() / 2 + Integer.valueOf(notifyData) * 2);
                String left_data = notifyData2.substring(0, 2);
                left_data = MyUtils.insertStringInParticularPosition(left_data, ",", 1);
                MyUtils.Loge(TAG, "左眼数据：" + left_data);
                String right_data = notifyData2.substring(0, 2);
                right_data = MyUtils.insertStringInParticularPosition(right_data, ",", 1);
                MyUtils.Loge(TAG, "右眼数据：" + right_data);
                String both_data = notifyData2.substring(0, 2);
                both_data = MyUtils.insertStringInParticularPosition(both_data, ",", 1);
                MyUtils.Loge(TAG, "双眼数据：" + both_data);
                String viewData = "{\"left_num\":\"+1\",\"right_num\":\"+1\",\"double_num\":\"+1\"" +
                        ",\"left_vision\":\"" + left_data + "\",\"right_vision\":\"" + right_data + "\",\"double_vision\":\"" + both_data + "\"}";
                //上传到服务器
                MyUtils.Loge(TAG, "viewData:" + viewData);
                updateData(viewData);

            }*/


        }

//        if()
    }

    /**
     * step9:设备连接接口  操作入口
     */
    public void deviceConnect() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.DEVICE_CONNECT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "deviceConnect()--response:" + response);
                finishShowLoad();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        int left_days = jsonObject.getInt("left_days");
                        if (left_days <= 5) {
                            if (left_days == 0) {
                                //TODO 去掉提示
                                MyUtils.showToast(getActivity(), "请联系经销商或客服，添加购置时间");
                                return;
                            }
                            //TODO 去掉提示
                            MyUtils.showToast(getActivity(), "请及时与经销商或客服进行续锻炼时间，以免影响正常训练");
                            //TODO 连接设备成功 开始操作
                            sendData(KeyUtils.DEVICE_0066);
                        } else {
                            //TODO 连接设备成功 开始操作
                            sendData(KeyUtils.DEVICE_0066);
                        }
                        getPlan();

                    } else if (code == 901) { //未绑定状态
                        isFirstActive = false;
                        getAutoPlan();
                    } else {
                        String msg = jsonObject.getString("msg");
//                        new TipsDialogView(getActivity(), HomeFragment.this, 1, code, msg).showDialog();
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
                finishShowLoad();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_code", MyApplication.ONE_CODE);
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 获取方案列表
     */
    public void getPlan() {
        listPlan.clear();
        autoListPlan.clear();
        showLoad(getActivity(), "初始化方案列表");
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.PLAN + "?device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishShowLoad();
                MyUtils.Loge(TAG, "getPlan()--response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Gson gson = new Gson();
                        PlanEntity planEntity = gson.fromJson(response, PlanEntity.class);
                        //将自动方案里面的各种阶段也放到listPlan里面
                        if (planEntity != null && planEntity.getAuto_plan() != null
                                && planEntity.getAuto_plan().getSections() != null && planEntity.getAuto_plan().getSections().size() > 0) {
                            PlanEntity.CommonPlansBean commonPlansBean = new PlanEntity.CommonPlansBean();
                            commonPlansBean.setIs_auto(planEntity.getAuto_plan().getIs_auto());
                            commonPlansBean.setDuration(planEntity.getAuto_plan().getDuration());
                            commonPlansBean.setLeft_time(planEntity.getAuto_plan().getLeft_time());
                            commonPlansBean.setName(planEntity.getAuto_plan().getName());
                            commonPlansBean.setId(planEntity.getAuto_plan().getId());
                            commonPlansBean.setDuration_display(planEntity.getAuto_plan().getDuration_display());
                            commonPlansBean.setLeft_time_display(planEntity.getAuto_plan().getLeft_time_display());
                            commonPlansBean.setSection_id(planEntity.getAuto_plan().getSection_id());
                            commonPlansBean.setSection_name(planEntity.getAuto_plan().getSection_name());
                            listPlan.add(commonPlansBean);

                        }
                        if (planEntity != null && planEntity.getCommon_plans() != null && planEntity.getCommon_plans().size() > 0) {
                            listPlan.addAll(planEntity.getCommon_plans());
//                            MyUtils.Loge(TAG, "planIndex:" + planIndex);
//                            MyUtils.Loge(TAG, "planIndex:--3--" + listPlan.size());
                        }

                        if (listPlan.size() > 0 && listPlan.get(planIndex).getIs_auto() == 1) {
                            // 自动方案时 设置显示的时间倒计时
                            home_plan_name.setText(listPlan.get(planIndex).getName() + "-" + listPlan.get(planIndex).getSection_name());
                        } else if ((listPlan.size() > 0 && listPlan.get(planIndex).getIs_auto() == 0)) {
                            //普通方案
                            home_plan_name.setText(listPlan.get(planIndex).getName());
                        }
                        leftTime = listPlan.get(planIndex).getLeft_time();
                        home_plan_time.setText(timeFormat(listPlan.get(planIndex).getDuration() * 1000));
                        MyUtils.Loge(TAG, "BBB-获取列表时间:" + leftTime);
                        home_plan_timer.setText(timeFormat(leftTime * 1000));

                        //TODO 保存计划id到缓存
//                        //获取计划成功之后，将当前计划id保存到缓存
                        SaveUtils.setString(KeyUtils.section_id, listPlan.get(planIndex).getSection_id());

                        //如果是第一次激活设备 就告诉设备已经准备好了
                        if (!isFirstActive) {
                            MyUtils.Loge(TAG, "isFirstActive=" + isFirstActive);
                            //TODO 连接设备成功 开始操作
                            sendData(KeyUtils.DEVICE_0066);
                        }

                        //如果是有异常指令的话就暂停机器


                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
                finishShowLoad();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * step11:获取自动方案
     */
    public void getAutoPlan() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.AUTO_PLANS;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "getAutoPlan()--response:" + response);
                try {
                    Gson gson = new Gson();
                    AutoPlanEntity autoPlanEntity = gson.fromJson(response, AutoPlanEntity.class);
                    if (autoPlanEntity.getCode() == 0) {
                        //展示自动方案
                        showPlan(autoPlanEntity.getPlans());
                    } else {
                        MyUtils.Loge(TAG, "getAutoPlan()");
                        VolleyUtils.dealErrorStatus(getActivity(), autoPlanEntity.getCode(), autoPlanEntity.getMsg());
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        });
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * step12 ：展示自动方案列表
     */
    public void showPlan(final List<AutoPlanEntity.PlansBean> list) {
        DialogmidView dialogmidView = new DialogmidView(getActivity(), R.layout.dialog_plan);
        dialogmidView.setData(list);
        dialogmidView.setMyPlanCallback(new DialogmidView.MyPlanCallback() {
            @Override
            public void planCallback(String planId) {
                //请求设备激活

                deviceActive(planId);
            }
        });
        dialogmidView.showDialog();


    }

    /**
     * step13:设备激活  操作入口
     */
    public void deviceActive(final String planId) {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.DEVICE_ACTIVE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "deviceActive()--response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        //TODO 开始操作
                        //获取所有计划列表
                        MyUtils.Loge(TAG, "plan--deviceActive()");
                        getPlan();
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e::" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_code", MyApplication.ONE_CODE);
                map.put("plan_id", planId);
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tv_cancel:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    home_lv.setVisibility(View.GONE);
                    home_ll_connecting.setVisibility(View.GONE);
                    home_ll_connected.setVisibility(View.VISIBLE);
                    home_error.setVisibility(View.GONE);
                }

                break;
            case R.id.home_player:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    if (MyApplication.isConnected) {
                        MyUtils.Loge(TAG, "申请启动电机");
                        if (isRunning) {          //停止电机
                            if (listPlan != null && listPlan.size() > 0) {
                                //上传暂停数据
                                updateLeftTime();
                            }
                        } else {
                            if (listPlan != null && listPlan.size() > 0) {
                                //启动电机
                                getLeftTime();
                            }
                        }

                    }
                }
                break;
            case R.id.home_add:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    if (MyApplication.isConnected) {
                        if (isRunning) {
                            sendData(KeyUtils.DEVICE_00E2);
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);
                        } else {
                            MyUtils.showToast(getActivity(), "机器还没运行");
                        }
                    }
                }

                break;
            case R.id.home_reduce:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    if (MyApplication.isConnected) {
                        if (isRunning) {
                            sendData(KeyUtils.DEVICE_00E3);
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);

                        } else {
                            MyUtils.showToast(getActivity(), "机器还没运行");
                        }
                    }
                }
                break;

            case R.id.home_privous: //上一个普通方案
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    MyUtils.Loge(TAG, "上一个普通方案----" + planIndex);
                    if (!isRunning) {
                        if (listPlan != null && listPlan.size() > 0 && planIndex > 0) {
                            MyUtils.Loge(TAG, "listPlan.size():" + listPlan.size() + "planIndex:--11----" + planIndex);
                            planIndex--;
                            leftTime = listPlan.get(planIndex).getLeft_time();
                            //将当前计划id保存到缓存
                            SaveUtils.setString(KeyUtils.plan_id, listPlan.get(planIndex).getId());
                            SaveUtils.setInt(KeyUtils.plan_index, planIndex);
                            getPlan();
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);
                            home_next.setSelected(true);
                            home_privous.setSelected(true);


                        } else {
                            MyUtils.showToast(getActivity(), "当前已经是第一个方案");
                            home_next.setSelected(true);
                            home_privous.setSelected(false);
                        }
                    } else {
                        MyUtils.showToast(getActivity(), "运行中，不能选择其他方案");
                    }
                }
                break;
            case R.id.home_next:    //下一个普通方案
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    MyUtils.Loge(TAG, "下一个普通方案----" + planIndex);
                    if (!isRunning) {
                        if (listPlan != null && listPlan.size() > 0 && planIndex < listPlan.size() - 1) {
                            MyUtils.Loge(TAG, "listPlan.size():" + listPlan.size() + "planIndex:--22----" + planIndex);
                            planIndex++;
                            leftTime = listPlan.get(planIndex).getLeft_time();
                            //将当前计划id保存到缓存
                            SaveUtils.setString(KeyUtils.plan_id, listPlan.get(planIndex).getId());
                            SaveUtils.setInt(KeyUtils.plan_index, planIndex);
                            getPlan();
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);
                            home_next.setSelected(true);
                            home_privous.setSelected(true);
                        } else {
                            MyUtils.showToast(getActivity(), "当前已经是最后一个方案");
                            home_next.setSelected(false);
                            home_privous.setSelected(true);
                        }
                    } else {
                        MyUtils.showToast(getActivity(), "运行中，不能选择其他方案");
                    }
                }
                break;
            case R.id.home_stop:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    isClose = true;
                    updateLeftTime();
                }
                break;
            case R.id.home_error_connect:
                if (ClickUtils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    planIndex = 0;
                    initBle();
                }

                break;
        }
    }

    /**
     * 启动电机---获取方案详情
     */
    public void getLeftTime() {
        MyUtils.Loge(TAG, "getLeftTime()--id:" + listPlan.get(planIndex).getId());
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.LEFT_TIME + listPlan.get(planIndex).getId() + "?device_code=" + MyApplication.ONE_CODE;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "getLeftTime()--response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Gson gson = new Gson();
                        PlanDetailsEntity planDetailsEntity = gson.fromJson(response, PlanDetailsEntity.class);
                        if (planDetailsEntity != null && planDetailsEntity.getPlan() != null) {
                            leftTime = planDetailsEntity.getPlan().getLeft_time();
                            MyUtils.Loge(TAG, "BBB-启动电机获取时间:" + leftTime);
                            home_plan_timer.setText(timeFormat(leftTime * 1000));

                            if (listPlan.get(planIndex).getIs_auto() == 1) {//如果是自动方案，就传section_id
                                if (listPlan.get(planIndex).getId().length() == 2) {
                                    sendData(KeyUtils.send_OOEO(listPlan.get(planIndex).getSection_id()));
                                }
                                if (listPlan.get(planIndex).getId().length() == 1) {
                                    sendData(KeyUtils.send_OOEO("0" + listPlan.get(planIndex).getSection_id()));
                                }
                            } else {//普通方案
                                if (listPlan.get(planIndex).getId().length() == 2) {
                                    sendData(KeyUtils.send_OOEO(listPlan.get(planIndex).getId()));
                                }
                                if (listPlan.get(planIndex).getId().length() == 1) {
                                    sendData(KeyUtils.send_OOEO("0" + listPlan.get(planIndex).getId()));
                                }
                            }
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);

                        }
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 暂停电机---暂停的时候上传剩余时间
     */
    public void updateLeftTime() {
        MyUtils.Loge(TAG, "暂停操作--leftTime:" + leftTime);
        //上传时间的同时，暂停倒计时（如果放在接口回调的话，会延迟一秒，所以得在发送接口的同时暂停倒计时）
        if (timer != null) {
            timer.cancel();
        }
        if (listPlan.size() - 1 > planIndex) {
            String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_TIME;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    MyUtils.Loge(TAG, "暂停电机--response:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");
                        if (code == 0) {
                            sendData(KeyUtils.DEVICE_00E1);
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);
                            isRunning = false;
                            home_player.setImageResource(R.drawable.vector_drawable_stop);
                            home_add.setSelected(false);
                            home_reduce.setSelected(false);
                            home_next.setSelected(true);
                            home_privous.setSelected(true);

                            //如果是关闭蓝牙的话，就关闭蓝牙
                            if (isClose) {
                                //设置唯一码为空
                                MyApplication.ONE_CODE = "";
                                if (MyApplication.isConnected) {
                                    if (isRunning) {
                                        sendData(KeyUtils.DEVICE_00E1);
                                    }
                                    showLoad(getActivity(), "关闭中...");
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            try {
                                                //延时两秒断开蓝牙
                                                sleep(2000);
                                                if (BleManager.getInstance().isConnected(device)) {
                                                    BleManager.getInstance().disconnect(device);
                                                }
                                                finishShowLoad();
                                            } catch (InterruptedException e) {

                                            }
                                        }
                                    }.start();
                                }
                            }
                            isAutoCancel = false;
                            isClose = false;
                            MyUtils.showToast(getActivity(), "已暂停");
                        } else {
                            //震动
                            vibrator.vibrate(VIBRATOR_TIME);
                            isRunning = false;
                            home_player.setImageResource(R.drawable.vector_drawable_stop);
                            home_add.setSelected(false);
                            home_reduce.setSelected(false);
                            home_next.setSelected(true);
                            home_privous.setSelected(true);
                            if (timer != null) {
                                timer.cancel();
                            }
                            isClose = false;
                            String msg = jsonObject.getString("msg");
                            VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                        }
                    } catch (Exception e) {
                        isClose = false;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MyUtils.showToast(getActivity(), "网络有问题");
                    isClose = false;
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                    return map;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("device_code", MyApplication.ONE_CODE);
                    map.put("plan_id", listPlan.get(planIndex).getId());
                    map.put("section_id", listPlan.get(planIndex).getSection_id());
                    map.put("record_type", "1");
                    map.put("left_time", String.valueOf(leftTime));
                    return map;
                }
            };
            VolleyUtils.setTimeOut(stringRequest);
            VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }

    /**
     * 上传上次异常的一条数据记录
     */
    public void updateErrorLeftTime(final String plan_id, final String runTime, final String device_status, final String notify_data) {
        MyUtils.Loge("AAA", "上传上次异常的一条数据记录--的运行时间：" + runTime);
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_TIME;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                MyUtils.Loge(TAG, "上传上次异常的一条数据记录--response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Gson gson = new Gson();
                        ErrorEntity errorEntity = gson.fromJson(response, ErrorEntity.class);
                        home_plan_timer.setText(timeFormat(errorEntity.getPlan().getLeft_time() * 1000));
                        sendData(KeyUtils.DEVICE_00E1);
                        MyUtils.Loge(TAG, "上传上次异常数据成功");

                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_code", MyApplication.ONE_CODE);
                map.put("plan_id", plan_id);
                map.put("section_id", SaveUtils.getString(KeyUtils.section_id));
                map.put("run_time", runTime);
                map.put("record_type", "2");
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 时间到停止设备传送数据给服务器保存
     */
    public void updateData() {
        MyUtils.Loge(TAG, "选择的方案id：" + listPlan.get(planIndex).getName());
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_EXPRECISE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "时间到停止设备传送数据给服务器--response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        MyUtils.Loge(TAG, "时间到停止设备传送数据给服务器--上传成功");
                        getPlan();
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_code", MyApplication.ONE_CODE);
                map.put("plan_id", listPlan.get(planIndex).getId());
                map.put("evaluation", "佩戴正常");
                map.put("glass_number", "2");
                map.put("section_id", listPlan.get(planIndex).getSection_id());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x01:
                MyUtils.Loge(TAG, "蓝牙已经打开------");
                if (BleUtils.checkGPSIsOpen(getActivity())) {
                    showLoad(getActivity(), "正在初始化蓝牙").show();
                    BleUtils.setScanRule();
                    startScan();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 激活设备
     */
    public void onActive(int code, String msg) {
        getAutoPlan();

        code = -1;
        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
    }


    /**
     * 连接设备
     */
    public void onConnect() {
        MyUtils.Loge(TAG, "TipsDialogView---点击了连接");
        initBle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (BleManager.getInstance().isConnected(device)) {
            BleManager.getInstance().disconnect(device);
        }
    }


    /**
     * 倒计时
     *
     * @param time
     */
    public void runTimer(final long time) {
        MyUtils.Loge(TAG, "runTimer-------time:" + time);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                leftTime = millisUntilFinished / 1000;
                MyUtils.Loge("AAA", "倒计时:" + leftTime);
                MyUtils.Loge(TAG, "BBB-倒计时获取时间:" + leftTime);
                home_plan_timer.setText(timeFormat(millisUntilFinished));
                MyUtils.Loge("CountDown", millisUntilFinished + "");
                //点亮屏幕
                if (leftTime < 5) {
                    MyUtils.Loge(TAG, "倒计时--屏幕点亮");
                    ToolUtils.wakeUpAndUnlock();
                }
            }

            @Override
            public void onFinish() {
                home_plan_timer.setText("00:00:00");
                MyUtils.showToast(getContext(), "倒计时结束");
                //发送时间到停止电机指令
                sendData(KeyUtils.DEVICE_00E4);

                //自动方案阶段归零
                if (planIndex == 0 && isAutoCancel) {
                    autoIndex = 0;
                }
            }
        };
        timer.start();
    }

    /**
     * 毫秒转换成时分秒
     *
     * @param millisUntilFinished
     */
    public String timeFormat(long millisUntilFinished) {
        String str_hour = "";
        String str_minute = "";
        String str_second = "";
        long day = millisUntilFinished / (1000 * 60 * 60 * 24); //天
        long hour = (millisUntilFinished - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60); //小时
        long minute = ((millisUntilFinished - day * 1000 * 60 * 60 * 24) - hour * 1000 * 60 * 60) / (1000 * 60);  //分钟
        long second = ((millisUntilFinished - day * 1000 * 60 * 60 * 24) - hour * 1000 * 60 * 60 - minute * 1000 * 60) / 1000;  //秒
        if (hour < 10) {
            str_hour = "0" + hour;
        } else {
            str_hour = String.valueOf(hour);
        }
        if (minute < 10) {
            str_minute = "0" + minute;
        } else {
            str_minute = String.valueOf(minute);
        }
        if (second < 10) {
            str_second = "0" + second;
        } else {
            str_second = String.valueOf(second);
        }
        return str_hour + ":" + str_minute + ":" + str_second;
    }


    /**
     * 填写 视力 标识数
     */
    public void showVisionDialog() {
        visionDataView = new VisionDataView(getActivity());
        visionDataView.setOnVisionListener(new VisionDataView.OnVisionListener() {
            @Override
            public void onSure(VisionDataView.VisionBean vision) {
                //TODO 上传数据
                MyUtils.Loge(TAG, "vision:::::" + vision);
                updateVesion(vision);
                visionDataView.dismiss();
            }
        });
        visionDataView.showDialog();
    }

    /**
     * 获取版本信息
     */
    private void getVersion() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_VERSION + "?version_type=1";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "getVersion()--response:" + response);
                try {
                    Gson gson = new Gson();
                    UpdateVersionEntity updateVersionEntity = gson.fromJson(response, UpdateVersionEntity.class);
                    if (updateVersionEntity.getCode() == 0) {
                        if (updateVersionEntity.getVersion() != null) {
                            MyApplication.update_url = updateVersionEntity.getVersion().getUrl();
                            MyApplication.update_content = updateVersionEntity.getVersion().getContent();
                            if (Double.valueOf(updateVersionEntity.getVersion().getVersion_code()) > MyUtils.getVersionCode(getActivity()) && !TextUtils.isEmpty(updateVersionEntity.getVersion().getUrl())) {
                                // TODO 下载
                                downloadApk();
                            }
                        }

                    } else {
                        VolleyUtils.dealErrorStatus(getActivity(), updateVersionEntity.getCode(), updateVersionEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 下载APP
     */
    private void downloadApk() {

        new UpdateManger(getActivity(), 1).checkUpdateInfo();
    }

    /**
     * 提示信息
     */
    public void remind() {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.REMIND;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "getVersion()--response:" + response);
                try {
                    Gson gson = new Gson();
                    RemindEntity remindEntity = gson.fromJson(response, RemindEntity.class);
                    if (remindEntity.getCode() == 0) {
                        if (remindEntity.getGlass_remind() == 1) {
                            showAlertDialog("提示", "请及时更换目镜", "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    GlassesReplaceActivity.start(getActivity(), 2);
                                    dialogInterface.dismiss();
                                }
                            }, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }

                        if (remindEntity.getDevice_time_remind() == 1) {  //请联系经销商或客服，添加购置时间
                            if (remindEntity.getLeft_days() == 0) {
                                showAlertDialog2("提示", "请联系经销商或客服，添加购置时间", "我知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                            } else {
                                showAlertDialog2("提示", "请及时与经销商或客服进行续锻炼时间，以免影响正常训练", "我知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                            }
                        }

                    } else {
                        VolleyUtils.dealErrorStatus(getActivity(), remindEntity.getCode(), remindEntity.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 上传裸眼数据
     *
     * @param vision
     */
    private void updateVesion(final VisionDataView.VisionBean vision) {
        String url = HttpsAddress.BASE_ADDRESS + HttpsAddress.UPDATE_VISION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "上传裸眼视力updateVesion（）:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        MyUtils.showToast(getActivity(), "裸眼视力上传成功");
                    } else {
                        String msg = jsonObject.getString("msg");
                        VolleyUtils.dealErrorStatus(getActivity(), code, msg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", SaveUtils.getString(KeyUtils.token_type) + " " + SaveUtils.getString(KeyUtils.access_token));
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("left_vision", vision.getLeft_vision());
                map.put("left_num", vision.getLeft_num());
                map.put("right_vision", vision.getRight_vision());
                map.put("right_num", vision.getRight_num());
                map.put("double_vision", vision.getDouble_vision());
                map.put("double_num", vision.getDouble_num());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}
