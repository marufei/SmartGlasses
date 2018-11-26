package com.yxys365.smartglasses.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.utils.Codeutil;
import com.yxys365.smartglasses.utils.MyUtils;
import com.yxys365.smartglasses.utils.SecurityUtil;


public class TestBleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private String TAG="TestBleActivity";
    private String sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ble);

        //初始化蓝牙
        BleManager.getInstance().init(getApplication());
        //是否显示框架内部日志，重连次数和重连时间间隔，以及操作超时时间。
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);
        initviews();
    }

    private void initviews() {
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:


                break;
            case R.id.btn2:
                //TODO 设置测试的加密数据
                String str = "5a5a00ee001314151617202122232425262727";//00FF067071727374E3E3E3E3E3E37575
                str = str.substring(4, str.length() - 2);
                String str1 = str.substring(0, str.length() / 2);
                String str2 = str.substring(str.length() / 2, str.length());

                 sendData = "5A5A" + Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str1))) +
                        Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str2)));
                sendData = sendData + Codeutil.getNum(sendData);

                 MyUtils.Loge(TAG, "------加密后的数据:" + sendData);
                break;
            case R.id.btn3:
                //TODO 设置测试的解密数据
                String ss = "5a5afc27a3da82fffc140f3fa73d6a11b383c8";
                ss = ss.substring(4, ss.length() - 2);
                String str11 = ss.substring(0, ss.length() / 2);
                String str22 = ss.substring(ss.length() / 2, ss.length());
                String data = Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str11))) +
                        Codeutil.bytesToHexString(SecurityUtil.decrpytJNI(Codeutil.hexStringToByte(str22)));
                 MyUtils.Loge(TAG, "----解密后的数据-s:" + data);
                break;
            case R.id.btn4:
                if(!TextUtils.isEmpty(sendData)) {
                    Intent intent = new Intent(TestBleActivity.this, BLEActivity.class);
                    intent.putExtra("bleData", sendData);
                    startActivity(intent);
                }else {
                    Toast.makeText(TestBleActivity.this,"先加密才能进行下一步",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
