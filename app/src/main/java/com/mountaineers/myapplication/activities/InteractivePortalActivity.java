package com.mountaineers.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.utilities.AndroidBridge;

public class InteractivePortalActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT = "extra_subject";
    public static final String EXTRA_SAMPLE = "extra_sample";
    public static final String EXTRA_MODE = "extra_mode"; // "portal" or "practice"

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_portal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide(); // Use the modern in-app responsive header
        }

        webView = findViewById(R.id.portalWebView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        WebView.setWebContentsDebuggingEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                String title = "Exit Examination";
                if (message != null && !message.toLowerCase().contains("exit") && !message.toLowerCase().contains("progress")) {
                    title = "Notice";
                }
                new AlertDialog.Builder(InteractivePortalActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Exit", (dialog, which) -> result.confirm())
                        .setNegativeButton("Cancel", (dialog, which) -> result.cancel())
                        .setOnCancelListener(dialog -> result.cancel())
                        .setCancelable(true)
                        .show();
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(InteractivePortalActivity.this)
                        .setTitle("Exam Portal")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm())
                        .setOnCancelListener(dialog -> result.confirm())
                        .setCancelable(true)
                        .show();
                return true;
            }
        });
        webView.addJavascriptInterface(new AndroidBridge(this), "AndroidBridge");

        loadFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        loadFromIntent(intent);
    }

    private void loadFromIntent(Intent intent) {
        if (webView == null || intent == null) return;

        String subject = intent.getStringExtra(EXTRA_SUBJECT);
        String sample = intent.getStringExtra(EXTRA_SAMPLE);
        String mode = intent.getStringExtra(EXTRA_MODE);

        StringBuilder url = new StringBuilder("file:///android_asset/portal/index.html");
        if (subject != null && !subject.isEmpty() && sample != null && !sample.isEmpty()) {
            url.append("?subject=").append(subject)
               .append("&sample=").append(sample);
            if (mode != null && !mode.isEmpty()) {
                url.append("&mode=").append(mode);
            }
        }

        webView.loadUrl(url.toString());
    }

    @Override
    protected void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            webView.evaluateJavascript("handleBack();", null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("AndroidBridge");
            if (webView.getParent() instanceof ViewGroup) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
