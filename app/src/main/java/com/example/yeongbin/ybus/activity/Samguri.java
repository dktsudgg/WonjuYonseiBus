package com.example.yeongbin.ybus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yeongbin.ybus.classes.BusTimeChecker;
import com.example.yeongbin.ybus.classes.BusTimeInfo;
import com.example.yeongbin.ybus.R;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Samguri extends AppCompatActivity {
    private WebView mWebView;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samguri);
        mWebView = (WebView)findViewById(R.id.samguri);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://its.wonju.go.kr/moMap/mBusStopResult.do?station_id=251036045&route_id=&searchType=N&searchKeyword=36045&serivce_id=&searchType=N");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
