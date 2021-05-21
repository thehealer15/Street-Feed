package com.example.streetfeed.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.streetfeed.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SampleDashboard extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_dashboard);
        ///mAuth=FirebaseAuth.getInstance();
    }
    /*protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser==null)
        {
            startActivity(new Intent(SampleDashboard.this,UserLogIn.class));
            finish();
        }
    }*/
}