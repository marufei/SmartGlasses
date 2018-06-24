package com.yxys365.smartglasses.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.adapter.LvAdapter;
import com.yxys365.smartglasses.utils.Codeutil;
import com.yxys365.smartglasses.utils.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BLEActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private String s;
    private TextView tv_data;
    private Button btn_search;
    private ListView list_show;
    private String TAG = "BLEActivity";

    private UUID[] serviceUuids;
    private String names;
    private String mac;
    private boolean isAutoConnect;
    /**
     * 搜索到的蓝牙设备
     */
    private List<BleDevice> list = new ArrayList<>();

    private UUID uuid_service;
    private UUID uuid_chara;
    private LvAdapter adapter;
    private BluetoothGatt gattBLE;
    /**
     * 发送UUID
     */
    private UUID sendChara;
    /**
     * 接收UUID
     */
    private UUID notifyChara;
    /**
     * 搜索到的蓝牙Mac地址
     */
    private List<String> listMac=new ArrayList<>();
    private Button btn_success;
    private Button btn_dj;
    private Button btn_add;
    private Button btn_reduce;
    private Button btn_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        setPermission();
        initViews();
        initData();
        initListenner();

    }

    private void initListenner() {
        adapter.setBleListenner(new LvAdapter.BleListenner() {
            @Override
            public void connect(BleDevice bleDevice) {
                if (!BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().cancelScan();
                    connectBLE(bleDevice);
                }
            }

            @Override
            public void disconnet(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                }
//                list.clear();

                adapter.notifyDataSetChanged();
            }

            @Override
            public void oprate(BleDevice bleDevice) {
                if (gattBLE != null) {
                    List<BluetoothGattService> serviceList = gattBLE.getServices();
                    for (BluetoothGattService service : serviceList) {
                        uuid_service = service.getUuid();

                        Log.e(TAG, "uuid_service:" + uuid_service);
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (BluetoothGattCharacteristic characteristic : characteristicList) {
                            uuid_chara = characteristic.getUuid();
                            Log.e(TAG, "uuid_chara:" + uuid_chara);
                            if(uuid_chara.toString().equals("6e400002-b5a3-f393-e0a9-e50e24dcca9e")){
                                sendChara=uuid_chara;
                            }

//                            if(uuid_chara.toString().equals("6e400003-b5a3-f393-e0a9-e50e24dcca9e")){
//                                notifyChara=uuid_chara;
//                            }

                        }

                    }
                    if (uuid_service != null && sendChara != null) {
//                        nitifyData(bleDevice,uuid_service,uuid_chara);
                        writeData(bleDevice, uuid_service, sendChara);
                        return;
                    }
                }
            }
        });
    }

    private void initViews() {
        tv_data = findViewById(R.id.tv_data);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        list_show = findViewById(R.id.list_show);

        adapter = new LvAdapter(BLEActivity.this, list);
        list_show.setAdapter(adapter);

        btn_success=findViewById(R.id.btn_success);
        btn_success.setOnClickListener(this);

        btn_dj=findViewById(R.id.btn_dj);
        btn_dj.setOnClickListener(this);

        btn_add=findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

        btn_reduce=findViewById(R.id.btn_reduce);
        btn_reduce.setOnClickListener(this);

        btn_end=findViewById(R.id.btn_end);
        btn_end.setOnClickListener(this);

    }

    private void initData() {
        s = getIntent().getStringExtra("bleData");
        if (!TextUtils.isEmpty(s)) {
            tv_data.setText(s);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                //判断是否支持ble
                if (BleManager.getInstance().isSupportBle()) {
                    //判断是否打开了蓝牙
                    if (BleManager.getInstance().isBlueEnable()) {
//                        List<BleDevice> list = BleManager.getInstance().getAllConnectedDevice();
//                        Log.e(TAG, "搜到的蓝牙个数：" + list.size());

                        if (checkGPSIsOpen()) {
                            setScanRule();
                            startScan();
                        }

                    } else {
                        //1.通过蓝牙适配器直接打开蓝牙
//                        BleManager.getInstance().enableBluetooth();

                        //2.通过startActivityForResult引导界面引导用户打开蓝牙
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, 0x01);
                    }
                } else {
                    Log.e(TAG, "当前设备不支持BLE");
                }
                break;
            case R.id.btn_success:
                jiamiData("0066确认指令","5a5a006600131415161720212223242526279f");
                break;
            case R.id.btn_dj:
                jiamiData("启动电机","5a5a00e00213141516170100222324252627db");
                break;

            case R.id.btn_add:
                jiamiData("电机加速","5a5a00e200131415161720212223242526271b");
                 break;
            case R.id.btn_reduce:
                jiamiData("电机减速","5a5a00e300131415161720212223242526271c");
                break;
            case R.id.btn_end:
                jiamiData("停止电机","5a5a00e100131415161720212223242526271a");
                break;
        }
    }

    /**
     * APP通过notify接收蓝牙数据
     */
    public void nitifyData(BleDevice bleDevice, UUID uuid_service, UUID uuid_chara) {

        BleManager.getInstance().notify(
                bleDevice,
                uuid_service.toString(),
                uuid_chara.toString(),
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        Log.e(TAG, "---onNotifySuccess");
                        Toast.makeText(BLEActivity.this, "接收数据成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        // 打开通知操作失败
                        Log.e(TAG, "---onNotifyFailure:" + exception.getDescription());
                        Toast.makeText(BLEActivity.this, "接收数据失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        Log.e(TAG, "---onCharacteristicChanged");
                        if (data != null) {
                            Log.e(TAG,"接收的数据："+ Codeutil.bytesToHexString(data));

                            //TODO 解密接收到的数据
                            String ss=Codeutil.bytesToHexString(data);
                            ss = ss.substring(4, ss.length() - 2);
                            String str11 = ss.substring(0, ss.length() / 2);
                            String str22 = ss.substring(ss.length() / 2, ss.length());
                            String notify_data = Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str11))) +
                                    Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str22)));
                            Log.e(TAG, "----解密后的数据notify_data:" + notify_data);

                            Toast.makeText(BLEActivity.this, "接收到解密后的数据notify_data：" + notify_data, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(BLEActivity.this, "接收数据为空值", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    /**
     * 判断GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 配置扫描规则
     */
    private void setScanRule() {

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
                .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    /**
     * 扫描蓝牙设备
     */
    private void startScan() {
        list.clear();
        listMac.clear();
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.e(TAG, "success:" + success);
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
                Log.e(TAG, "执行onLeScan:" + bleDevice.getMac());
//                if (list.size() > 0) {
////                    for (int i = 0; i < list.size(); i++) {
////
////                        if (!bleDevice.getMac().equals(list.get(i).getMac())) {
////                            Log.e(TAG,"i"+i+"-----bleDevice.getmac():"+bleDevice.getMac()+"--list.get(i).getMac():"+list.get(i).getMac());
////                            list.add(bleDevice);
////                        }
////                    }
//                    if(!listMac.contains(bleDevice.getMac())){
//                        Log.e(TAG,"-----bleDevice.getmac():"+bleDevice.getMac()+"--listMac.size()::"+listMac.size());
//
//                        list.add(bleDevice);
//                    }
//
//                } else {
//                    list.add(bleDevice);
//                    listMac.add(bleDevice.getMac());
//                }

                if(listMac.size()>0) {
                    for (int i = 0; i < listMac.size(); i++) {
                        if (listMac.contains(bleDevice.getMac())) {
                            Log.e(TAG, "listMac.size():" + listMac.size());
                        } else {
                            listMac.add(bleDevice.getMac());
                            Log.e(TAG, "----listMac.size():" + listMac.size());
                            list.add(bleDevice);
                        }
                    }
                }else {
                    listMac.add(bleDevice.getMac());
                    list.add(bleDevice);
                }

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.e(TAG, "bleDevice:" + bleDevice.getMac());
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {

                Log.e(TAG, "最终搜索到的蓝牙设备:" + list.size());
                if (adapter == null) {
                    adapter = new LvAdapter(BLEActivity.this, list);
                    list_show.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    /**
     * 连接蓝牙设备
     *
     * @param bleDevice
     */
    public void connectBLE(BleDevice bleDevice) {
        if (!BleManager.getInstance().isConnected(bleDevice)) {
            BleManager.getInstance().cancelScan();
            BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    Log.e(TAG, "onStartConnect");
                }

                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {
                    Log.e(TAG, "onConnectFail");
                    Toast.makeText(BLEActivity.this,"连接蓝牙失败",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    Log.e(TAG, "onConnectSuccess--mac地址：" + bleDevice.getMac());
                    gattBLE = gatt;

                    List<BluetoothGattService> serviceList = gatt.getServices();
                    for (BluetoothGattService service : serviceList) {
                        uuid_service = service.getUuid();

                        Log.e(TAG, "uuid_service:" + uuid_service);
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (BluetoothGattCharacteristic characteristic : characteristicList) {
                            uuid_chara = characteristic.getUuid();
                            if(uuid_chara.toString().equals("6e400003-b5a3-f393-e0a9-e50e24dcca9e")){
                                notifyChara=uuid_chara;
                            }
                            Log.e(TAG, "notifyChara:" + notifyChara);
                        }

                    }
//                    if(uuid_service!=null&&uuid_chara!=null){
////                        nitifyData(bleDevice,uuid_service,uuid_chara);
//                        writeData(bleDevice,uuid_service,uuid_chara);
//                        return;
//                    }
                    if(uuid_service!=null&&notifyChara!=null) {
                        nitifyData(bleDevice, uuid_service, notifyChara);
                        adapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    Log.e(TAG, "onDisConnected");

                }
            });
        }
    }

    /**
     * APP通过write传送数据给蓝牙
     */
    public void writeData(BleDevice bleDevice, UUID uuid_service, UUID uuid_chara) {
        if (!TextUtils.isEmpty(s)) {
            BleManager.getInstance().write(
                    bleDevice,
                    uuid_service.toString(),
                    uuid_chara.toString(),
                    Codeutil.hexStringToByte(s),
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            // 发送数据到设备成功（分包发送的情况下，可以通过方法中返回的参数可以查看发送进度）
                            Log.e(TAG, "onWriteSuccess-------------");
                            Toast.makeText(BLEActivity.this, "发送数据成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            // 发送数据到设备失败
                            Log.e(TAG, "onWriteFailure-------------");
                            Toast.makeText(BLEActivity.this, "发送数据失败：" + exception.getDescription(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Log.e(TAG, "s是空值");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x01:
                Log.e(TAG, "蓝牙已经打开------");
                if (checkGPSIsOpen()) {
                    setScanRule();
                    startScan();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 加密数据
     * @param str
     */
    public void jiamiData(String type,String str){
        //TODO 设置测试的加密数据
        str = str.substring(4, str.length() - 2);
        String str1 = str.substring(0, str.length() / 2);
        String str2 = str.substring(str.length() / 2, str.length());

        s = "5A5A" + Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str1))) +
                Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str2)));
        s = s + Codeutil.getNum(s);
        Log.e(TAG, type+"----加密后的数据-s:" + s);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }
}
