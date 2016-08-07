package com.pcsalt.example.demomphrx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcsalt.example.demomphrx.db.DataSource;
import com.pcsalt.example.demomphrx.model.WebUrl;

public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "DisplayActivity";
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textViewTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        textViewTitle.setText(R.string.title_details);

        DataSource dataSource = new DataSource(DisplayActivity.this);
        dataSource.open();
        WebUrl webUrl = dataSource.getWebUrl(getIntent().getStringExtra("selectedUrl"));
        Log.d(TAG, "webUrl: " + webUrl.getWebUrl());
        Log.d(TAG, "description: " + webUrl.getDescription());
        dataSource.close();

        TextView row1 = (TextView) findViewById(R.id.text_view_row1);
        TextView row2 = (TextView) findViewById(R.id.text_view_row2);
        webView = (WebView) findViewById(R.id.web_view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(DisplayActivity.this, "Now What: " + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                webView.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
            }

            @JavascriptInterface
            public void resize(final float height) {
                DisplayActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
                    }
                });
            }
        });

        row1.setText(webUrl.getDescription());
        row2.setText(webUrl.getDescription());
        webView.loadUrl(webUrl.getWebUrl());
    }
}
