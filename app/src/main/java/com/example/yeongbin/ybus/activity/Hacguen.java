package com.example.yeongbin.ybus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yeongbin.ybus.R;

public class Hacguen extends AppCompatActivity {
    private WebView mWebView;
    private WebSettings mWebSettings;
    private int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacguen);
        mWebView = (WebView)findViewById(R.id.hacguen);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://its.wonju.go.kr/moMap/mBusStopResult.do?station_id=251036041&route_id=&searchType=N&searchKeyword=36041&serivce_id=&searchType=N");

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
