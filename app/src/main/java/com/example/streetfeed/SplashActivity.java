package com.example.streetfeed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread thread= new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(new Intent(SplashActivity.this, Customer_bottom_nav.class));
                    finish();
                }
            }
        };thread.start();
    }
}