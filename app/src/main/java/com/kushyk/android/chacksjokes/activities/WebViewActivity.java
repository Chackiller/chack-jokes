package com.kushyk.android.chacksjokes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kushyk.android.chacksjokes.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.getActivity;
import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.makeFullScreen;

public class WebViewActivity extends AppCompatActivity {
    private CircularProgressBar circularProgressBar;
    private WebView mWebView;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen(getActivity(this));
        setContentView(R.layout.activity_web_view);
        setView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setView() {
        circularProgressBar = findViewById(R.id.circularProgressBar);
        circularProgressBar.setIndeterminateMode(true);
        loadingText = findViewById(R.id.loadingTextView);
        mWebView = findViewById(R.id.webViewActivity);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("https://www.google.com/");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                doProgressAnimation(newProgress);
            }
        });
    }

    private void doProgressAnimation(int newProgress) {
        loadingText.setText(newProgress + "%");
        if (newProgress == 100) {
            circularProgressBar.setIndeterminateMode(false);
            circularProgressBar.setVisibility(View.GONE);
            loadingText.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            repeatAnimation(view);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            repeatAnimation(view);
            return true;
        }
    }

    private void repeatAnimation(WebView view) {
        mWebView.setVisibility(View.GONE);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setIndeterminateMode(true);
        loadingText.setVisibility(View.VISIBLE);
        view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                doProgressAnimation(newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}