package com.yxys365.smartglasses.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yxys365.smartglasses.R;
import com.yxys365.smartglasses.utils.MyUtils;


/**
 * -
 * Created by Administrator on 2016-07-21.
 */
public class Web_Activity extends BaseActivity {
    private static final String TAG = "Web_Activity";
    private static final String URL = "URL";

    WebView mWebView;
    String loadurl;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, Web_Activity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ToolBarStyle(0);
        setBack(true);
        setTitle("加载中");
        loadurl = getIntent().getStringExtra(URL);

        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.webview);
        initWebView();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {

                MyUtils.Loge(TAG, "onLoadResource url=" + url);

                super.onLoadResource(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {

                 MyUtils.Loge(TAG, "intercept url=" + url);
                if (url.indexOf("ios:##") >= 0) {
                    return true;
                }

                if (url.indexOf("task/taskCallback") == -1) {
                    webview.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                 MyUtils.Loge(TAG, "onPageStarted url=" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                 MyUtils.Loge(TAG, "onPageFinished url=" + url);
                if (loadurl.equals(url)) {
                    mWebView.clearHistory();
                }
                // addImageClickListner();

//                Log.e(TAG, "onPageFinished WebView title=" + title);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                MyUtils.showToast(Web_Activity.this,"加载错误!");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

                setTitle(title);

                super.onReceivedTitle(view, title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                 MyUtils.Loge(TAG, url);
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }
        });
        mWebView.loadUrl(loadurl);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {

        // mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 设置
        // 缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = this.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        // String cacheDirPath =
        // getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
         MyUtils.Loge(TAG, "cacheDirPath=" + cacheDirPath);
        // 设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        //可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //支持缩放
        mWebView.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAllowFileAccess(false); // 允许访问文件
        mWebView.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮
        mWebView.getSettings().setSupportZoom(true); // 支持缩放
        //    添加js对象(必要)
        mWebView.addJavascriptInterface(new JsOperation(this), "taskWeb");

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }


    class JsOperation {

        Activity activity;

        public JsOperation(Activity activity) {
            this.activity = activity;
        }

    }
}
