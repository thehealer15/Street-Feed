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
                mobile_no=cpp.getSelectedCountryCodeWithPlus()+mobile_no;
                Intent otpIntent = new Intent(UserLogIn.this, ManageOTP.class);
                otpIntent.putExtra("mobile",mobile_no);
                startActivity(otpIntent);
            }

        });
    }
}
               /* PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(mobile.getText().toString())       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(UserLogIn.this)                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                    @Override//When the Sim is not in your device
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent otpIntent = new Intent(UserLogIn.this , ManageOTP.class);
                                                otpIntent.putExtra("backendOTP" , s);
                                                startActivity(otpIntent);
                                            }
                                        }, 10000);
                                    }

                                    @Override//When sim is in your device
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        signInWithPhoneAuthCredential(phoneAuthCredential);
                                    }

                                    @Override//Failed
                                    public void onVerificationFailed(@NonNull FirebaseException e)
                                    {
                                        Toast.makeText(UserLogIn.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }) .build()  ;       // OnVerificationStateChangedCallbacks
                PhoneAuthProvider.verifyPhoneNumber(options);*/

   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserLogIn.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), SampleDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(UserLogIn.this, "SignIn code error!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }*/
