package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);

                } catch (Exception e) {

                } finally {

//                    startActivity(new Intent(Splash.this, Login.class));
//                    finish();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {

                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);

                    } else {
                        startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));

                    }
                    finish();


                }
            }//;
        };
        splashTread.start();
    }
}