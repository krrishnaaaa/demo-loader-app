package com.pcsalt.example.demomphrx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pcsalt.example.demomphrx.db.DataSource;
import com.pcsalt.example.demomphrx.model.WebUrl;

public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        DataSource dataSource = new DataSource(DisplayActivity.this);
        dataSource.open();
        WebUrl webUrl = dataSource.getWebUrl(getIntent().getStringExtra("selectedUrl"));
        Log.d(TAG, "webUrl: " + webUrl.getWebUrl());
        Log.d(TAG, "description: " + webUrl.getDescription());
        dataSource.close();
    }
}
