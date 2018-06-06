package com.example.userpc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.userpc.myapplication.supportclasses.MyAppWebViewClient;

import com.example.userpc.myapplication.supportclasses.MyAppWebViewClient;

public class BrowserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_browser);
        getLayoutInflater().inflate(R.layout.activity_browser, frameLayout);
        WebView browser = (WebView) findViewById(R.id.webView);

        //to open links and redirects inwebView itself
        browser.setWebViewClient(new MyAppWebViewClient());

        Intent webViewBrowser = getIntent();
        String data = webViewBrowser.getStringExtra("authenticate");

        //to enable java script content
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        //browser.loadData(data, "text/html", "UTF-8");'
        Log.i("MoviesApp","url is -->"+data);
        browser.loadUrl(data);


    }
}
