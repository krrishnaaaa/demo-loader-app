package com.pcsalt.example.demomphrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgUrls;
    private static final long UPDATE_AFTER = 60 * 60 * 1000;

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
                break;
            case R.id.rb_url2:
                break;
        }
    }
}
