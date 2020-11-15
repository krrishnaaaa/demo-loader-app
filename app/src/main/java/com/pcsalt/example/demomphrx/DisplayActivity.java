package com.pcsalt.example.demomphrx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pcsalt.example.demomphrx.db.DataSource;
import com.pcsalt.example.demomphrx.model.WebUrl;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "DisplayActivity";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // getting toolbar view from XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setting the toolbar as ActionBar
        setSupportActionBar(toolbar);

        // getting the TextView inside the Toolbar
        TextView textViewTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        // setting Details as the title
        textViewTitle.setText(R.string.title_details);

        // creating DataSource instance to perform database operations
        DataSource dataSource = new DataSource(DisplayActivity.this);
        // opening database connection
        dataSource.open();
        // getting WebUrl information from the database
        List<WebUrl> webUrlList = dataSource.getWebUrl(getIntent().getStringExtra("selectedUrl"));
        if (webUrlList == null || webUrlList.isEmpty()) {
            Toast.makeText(this, "No url found", Toast.LENGTH_SHORT).show();
            return;
        }
        displayUrlChooser(webUrlList);
        dataSource.close();


        webView = (WebView) findViewById(R.id.web_view);

        // changing WebSettings for the webView to enable the change of height of WebView as per
        // the content length and force the URLs to open in the WebView instead of the browser
        // applications installed in the android device
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
                // this javaScript method is defined below to change the height of the WebView
                // after the content is loaded
                view.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
            }

            /**
             * This method will be called after page is finished loading
             *
             * @param height
             */
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

    }

    private void displayUrlChooser(List<WebUrl> webUrlList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[webUrlList.size()];
        int i = 0;
        for (WebUrl url : webUrlList) {
            items[i++] = url.getWebUrl();
        }
        builder.setItems(items, (dialogInterface, which) -> {
            TextView row1 = (TextView) findViewById(R.id.text_view_row1);
            TextView row2 = (TextView) findViewById(R.id.text_view_row2);
            WebUrl webUrl = webUrlList.get(which);
            row1.setText(webUrl.getDescription());
            row2.setText(webUrl.getDescription());
            webView.loadUrl(webUrl.getWebUrl());
            dialogInterface.dismiss();
        });
        builder.create().show();
    }
}
