package com.yxys365.smartglasses.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.views.DialogBottomView;
import com.yxys365.smartglasses.views.DialogmidView;

/**
 * TODO 服务信息
 */
public class ServiceInfoActivity extends BaseActivity implements View.OnClickListener{

    private Button user_info_commit;
    private ImageView service_zxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setTitle("服务信息");
        setBack(true);
        ToolBarStyle(1);
        initViews();
        initDatas();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ServiceInfoActivity.class);
        context.startActivity(intent);
    }

    private void initDatas() {

    }

    private void initViews() {
        user_info_commit=findViewById(R.id.user_info_commit);
        user_info_commit.setOnClickListener(this);

        service_zxing=findViewById(R.id.service_zxing);
        service_zxing.setOnClickListener(this);
    }

    /**
     *
     * @param layoutId
     */
    public void myDialog(int layoutId) {
        final DialogmidView dialogBottomView = new DialogmidView(ServiceInfoActivity.this, layoutId);
        Button dialog_btn_login = dialogBottomView.findViewById(R.id.dialog_btn_login);
        dialog_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottomView.dismiss();
                MainActivity.start(ServiceInfoActivity.this);
            }
        });
        dialogBottomView.showDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_info_commit:
                myDialog(R.layout.dialog_register);
                break;
            case R.id.service_zxing:
                customScan();
                break;
        }
    }

    // 你也可以使用简单的扫描功能，但是一般扫描的样式和行为都是可以自定义的，这里就写关于自定义的代码了
// 你可以把这个方法作为一个点击事件
    public void customScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
// 通过 onActivityResult的方法获取 扫描回来的 值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
