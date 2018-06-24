package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yxys365.smartglasses.R;

public class CustomScanActivity extends CheckPermissionsActivity implements DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView dbv_custom;

    private CaptureManager captureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        setPermission();
        setTitle("扫描二维码");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas(savedInstanceState);
    }

    private void initDatas(Bundle savedInstanceState) {

        dbv_custom.setTorchListener(this);
        //重要代码，初始化捕获
        captureManager = new CaptureManager(this,dbv_custom);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    private void initViews() {
        dbv_custom=findViewById(R.id.dbv_custom);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CustomScanActivity.class);
        context.startActivity(intent);
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return dbv_custom.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
