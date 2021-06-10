package com.example.streetfeed.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.streetfeed.Customer_bottom_nav;
import com.example.streetfeed.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ManageOTP extends AppCompatActivity {

    EditText otp;
    Button verifyOTP;
    String mobile_no;
    FirebaseAuth mAuth;
    String otpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        mobile_no=getIntent().getStringExtra("mobile").toString();
        otp=findViewById(R.id.text_otp);
        verifyOTP=findViewById(R.id.verify_OTP);
        mAuth=FirebaseAuth.getInstance();
        mobile_no=getIntent().getStringExtra("mobile");

        initiateOTP();
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(otp.getText().toString().isEmpty())
                    Toast.makeText(ManageOTP.this, "Empty credentials!Try Again", Toast.LENGTH_SHORT).show();
                else if(otp.getText().toString().length() != 6)
                    Toast.makeText(ManageOTP.this, "OTP verification failed!Try Again", Toast.LENGTH_SHORT).show();
                else if(otpid==null)
                {
                    Toast.makeText(ManageOTP.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            }
        });
    }
    public void initiateOTP(){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(ManageOTP.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override//When the Sim is not in your device
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;

                            }

                            @Override//When sim is in your device
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override//Failed
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(ManageOTP.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) .build()  ;       // OnVerificationStateChangedCallbacks
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManageOTP.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), Customer_bottom_nav.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ManageOTP.this, "SignIn code error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}