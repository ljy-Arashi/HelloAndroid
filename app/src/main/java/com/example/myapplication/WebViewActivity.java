package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView mwebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mwebView=findViewById(R.id.webView);
        //加载本地URL
       // mwebView.loadUrl("file:///android_asset/helloworld.html");
        //加载网络地址
        mwebView.getSettings().setJavaScriptEnabled(true);//设了才能加载
        //mwebView.setWebViewClient(new MyWebViewClient());加了这个会出现ERR_UNKNOWN_URL_SCHEME
        mwebView.setWebChromeClient(new MyWebChromeClient());//用了这个加载页面的时候不会打开外部浏览器
        mwebView.loadUrl("https://m.baidu.com");
    }

    //重写下面这个方法使得按返回键不会直接结束活动，会返回网页上一级页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && mwebView.canGoBack())
        {
            mwebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class  MyWebViewClient extends WebViewClient{
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());//加载页面的时候不会打开外部浏览器
            return true;//处理后别的不需要再处理
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("webview","onPageStarted");
            mwebView.evaluateJavascript("javascript:alert('load start')",null);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("webview","onPageFinished");
            mwebView.loadUrl("javascript:alert('load over')");
        }
    }
    class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //设置网页加载进度
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //获取加载网页的名称
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }
}
