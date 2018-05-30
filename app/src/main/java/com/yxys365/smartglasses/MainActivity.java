package com.yxys365.smartglasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yxys365.smartglasses.utils.Codeutil;
import com.yxys365.smartglasses.utils.SecurityUtil;

public class MainActivity extends AppCompatActivity {

    private Button operate;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        operate=findViewById(R.id.operate);
        operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                byte [] bytes= Codeutil.hexStringToByte("5a5a00ee001314151617202122232425262727");
//                byte [] aaa= SecurityUtil.encryptJNI(bytes);
                Log.e(TAG, "------点击了按钮");

                String str = "5a5a00ee001314151617202122232425262727";
                str = str.substring(4, str.length() - 2);
                String str1 = str.substring(0, str.length() / 2);
                String str2 = str.substring(str.length() / 2, str.length());
                Log.e(TAG, "---str:" + str);
                Log.e(TAG, "---str1:" + str1);
                Log.e(TAG, "---str2:" + str2);

                String s = "5A5A" + Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str1))) +
                        Codeutil.bytesToHexString(SecurityUtil.encryptJNI(Codeutil.hexStringToByte(str2)));
                s = s + getNum(s);

                Log.e(TAG, "------s:" + s);
            }
        });
    }

    /**
     * 获取和校验
     *
     * @return
     */
    public String getNum(String s) {
        byte num = 0;
        byte[] sum = Codeutil.hexStringToByte(s);
        for (int i = 0; i < sum.length; i++) {
            num += sum[i];
        }
        Log.e(TAG, "num-------" + Integer.toHexString(num));
        return Integer.toHexString(num);
    }
}
