package com.pcsalt.example.demomphrx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.pcsalt.example.demomphrx.db.DataSource;
import com.pcsalt.example.demomphrx.model.WebUrl;

public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textViewTitle = (TextView) toolbar.findViewById(R.id.text_view_title);
        textViewTitle.setText(R.string.app_name);

        DataSource dataSource = new DataSource(DisplayActivity.this);
        dataSource.open();
        WebUrl webUrl = dataSource.getWebUrl(getIntent().getStringExtra("selectedUrl"));
        Log.d(TAG, "webUrl: " + webUrl.getWebUrl());
        Log.d(TAG, "description: " + webUrl.getDescription());
        dataSource.close();
    }
}
