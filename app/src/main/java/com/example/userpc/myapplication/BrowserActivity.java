package com.example.userpc.myapplication;

import android.content.Intent;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BrowserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_browser);
        getLayoutInflater().inflate(R.layout.activity_browser, frameLayout);

        Intent webViewBrowser = getIntent();
        String data = webViewBrowser.getStringExtra("authenticate");

        WebView browser = (WebView) findViewById(R.id.webView);

        //to enable java script content
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        browser.loadData(data, "text/html", "UTF-8");



    }
}
