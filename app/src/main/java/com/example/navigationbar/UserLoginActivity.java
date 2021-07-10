package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.IMediaControllerCallback;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {

    EditText mobileno;
    Button sendotp;
//    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mobileno = findViewById(R.id.userNumber);
        sendotp = findViewById(R.id.btn_sendotp);
        //  FirebaseAuth auth = FirebaseAuth.getInstance();


        ProgressBar progressBar = findViewById(R.id.send_btn_progressbar);


        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mobileno.getText().toString().trim().isEmpty()) {
                    if (mobileno.getText().toString().trim().length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        sendotp.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobileno.getText().toString().trim()
                                , 60
                                , TimeUnit.SECONDS
                                , UserLoginActivity.this,


                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);

                                        Toast.makeText(UserLoginActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(UserLoginActivity.this, OtpVerification.class);
                                        intent.putExtra("Mobileno", mobileno.getText().toString().trim());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);

                                    }
                                }
                        );

                    } else {
                        Toast.makeText(UserLoginActivity.this, "Please Enter 10 digit number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserLoginActivity.this, "Please Enter Mobile number..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    public void LogInSeller(View view) {
//        startActivity(new Intent(UserLoginActivity.this, SellerLoginActivity.class));
//    }
}