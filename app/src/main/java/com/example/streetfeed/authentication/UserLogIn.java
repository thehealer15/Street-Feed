package com.example.streetfeed.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.streetfeed.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class UserLogIn extends AppCompatActivity {

    EditText mobile;
    CountryCodePicker cpp;
    Button getOTP;
    FirebaseAuth mAuth;
    Boolean temp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);
        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);

        mobile = findViewById(R.id.mobile_no);
        cpp = findViewById(R.id.cpp);
        getOTP = findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_no=mobile.getText().toString();
                mobile_no=(cpp.getSelectedCountryCodeWithPlus()+mobile_no).trim();
                if(mobile_no.length() !=13)
                {
                    Toast.makeText(UserLogIn.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                }
                else{
                Intent otpIntent = new Intent(UserLogIn.this, ManageOTP.class);
                otpIntent.putExtra("mobile",mobile_no);
                startActivity(otpIntent);}
            }

        });
    }
}
