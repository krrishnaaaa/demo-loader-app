package com.pcsalt.example.demomphrx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pcsalt.example.demomphrx.db.DataSource;
import com.pcsalt.example.demomphrx.model.WebUrl;
import com.pcsalt.example.demomphrx.net.NetworkClient;
import com.pcsalt.example.demomphrx.net.NetworkRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgUrls;
    private ProgressBar progressBar;
    private LinearLayout controls;
    private static final String TAG = "MainActivity";
    private NetworkRepo networkRepo;
    private DataSource dataSource;

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(MainActivity.this);
        networkRepo = NetworkClient.getClient().create(NetworkRepo.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textViewTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        textViewTitle.setText(R.string.app_name);

        rgUrls = (RadioGroup) findViewById(R.id.rg_urls);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        controls = (LinearLayout) findViewById(R.id.controls);
    }

    public void loadUrl(View view) {
        int checkedBtnId = rgUrls.getCheckedRadioButtonId();
        switch (checkedBtnId) {
            case R.id.rb_url1:
                handleUi(true);
                if (dataSource.exists(getString(R.string.url1))) {
                    openDisplayActivity(getString(R.string.url1));
                } else {
                    getFromUrl1();
                }
                break;
            case R.id.rb_url2:
                handleUi(true);
                if (dataSource.exists(getString(R.string.url2))) {
                    openDisplayActivity(getString(R.string.url2));
                } else {
                    getFromUrl2();
                }
                break;
        }
    }

    private void getFromUrl1() {
        Call<List<WebUrl>> call = networkRepo.getFromUrl1();
        handleResponse(call, getString(R.string.url1));
    }

    private void getFromUrl2() {
        Call<List<WebUrl>> call = networkRepo.getFromUrl2();
        handleResponse(call, getString(R.string.url2));
    }

    private void handleResponse(Call<List<WebUrl>> call, String url) {
        call.enqueue(new Callback<List<WebUrl>>() {
            @Override
            public void onResponse(@NonNull Call<List<WebUrl>> call, @NonNull Response<List<WebUrl>> response) {
                if (response.body() != null) {
                    for (WebUrl webUrl : response.body()) {
                        Log.d(TAG, "onResponse: " + webUrl.getWebUrl());
                        Log.d(TAG, "onResponse: " + webUrl.getDescription());

                        long insertId = dataSource.insertWebUrl(webUrl, url);
                        Log.d(TAG, "insertId: " + insertId);
                    }
                    openDisplayActivity(url);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WebUrl>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDisplayActivity(String selectedWebUrl) {
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        intent.putExtra("selectedUrl", selectedWebUrl);
        startActivity(intent);
        handleUi(false);
    }

    private void handleUi(boolean isLoading) {
        controls.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
}
