package com.medbox.medbox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.medbox.medbox.MainActivity;
import com.medbox.medbox.R;
import com.medbox.medbox.ui.util.ActivityUtils;
import com.medbox.medbox.ui.util.HandlerUtils;

public class LaunchActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        HandlerUtils.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (ActivityUtils.isAlive(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}
