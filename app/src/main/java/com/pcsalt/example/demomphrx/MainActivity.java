package com.pcsalt.example.demomphrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pcsalt.example.demomphrx.model.WebUrl;
import com.pcsalt.example.demomphrx.net.NetworkClient;
import com.pcsalt.example.demomphrx.net.NetworkRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgUrls;
    private static final long UPDATE_AFTER = 60 * 60 * 1000;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgUrls = (RadioGroup) findViewById(R.id.rg_urls);
    }

    public void loadUrl(View view) {
        int checkedBtnId = rgUrls.getCheckedRadioButtonId();
        switch (checkedBtnId) {
            case R.id.rb_url1:
                getFromUrl1();
                break;
            case R.id.rb_url2:
                break;
        }
    }

    private void getFromUrl1() {
        NetworkRepo networkRepo = NetworkClient.getClient().create(NetworkRepo.class);

        Call<List<WebUrl>> call = networkRepo.getFromUrl1();
        call.enqueue(new Callback<List<WebUrl>>() {
            @Override
            public void onResponse(Call<List<WebUrl>> call, Response<List<WebUrl>> response) {
                Log.d(TAG, "onResponse: " + response.body().get(0).getWebUrl());
                Log.d(TAG, "onResponse: " + response.body().get(0).getDescription());
            }

            @Override
            public void onFailure(Call<List<WebUrl>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
