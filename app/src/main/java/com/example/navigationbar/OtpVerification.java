package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationbar.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OtpVerification extends AppCompatActivity {

    EditText text1,text2,text3,text4,text5,text6;
    Button verifyotp;
    String backendotp;

    FirebaseAuth auth;
    DatabaseReference userrefrance;
    FirebaseDatabase userdatabase;
    String userId,number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        userdatabase=FirebaseDatabase.getInstance();
        userrefrance=userdatabase.getReference("userdata");
        auth=FirebaseAuth.getInstance();


        number=getIntent().getStringExtra("Mobileno");

        UserModel userModel=new UserModel(number,userId);

        text1=findViewById(R.id.otpnum1);
        text2=findViewById(R.id.otpnum2);
        text3=findViewById(R.id.otpnum3);
        text4=findViewById(R.id.otpnum4);
        text5=findViewById(R.id.otpnum5);
        text6=findViewById(R.id.otpnum6);

    //    EditText[] otp={text1,text2,text3,text4,text5,text6};

        text1.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (text1.getText().length() == 1)
                    text2.requestFocus();
                return false;
            }
        });
        text2.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (text2.getText().length() == 1)
                    text3.requestFocus();
                return false;
            }
        });
        text3.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (text3.getText().length() == 1)
                    text4.requestFocus();
                return false;
            }
        });
        text4.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (text4.getText().length() == 1)
                    text5.requestFocus();
                return false;
            }
        });
        text5.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (text5.getText().length() == 1)
                    text6.requestFocus();
                return false;
            }
        });

        verifyotp=findViewById(R.id.btn_verifyotp);
        ProgressBar progressBar=findViewById(R.id.verify_btn_progressbar);

        TextView mobileno=findViewById(R.id.otp_verification_mobile);

        mobileno.setText(String.format("+91%s", getIntent().getStringExtra("Mobileno")));

        backendotp=getIntent().getStringExtra("backendotp");

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!text1.getText().toString().trim().isEmpty() ){

                    String EnteredOtp=text1.getText().toString()+text2.getText().toString()
                                        +text3.getText().toString()+text4.getText().toString()
                                        +text5.getText().toString()+text6.getText().toString();

                    if (backendotp!=null){

                        progressBar.setVisibility(View.VISIBLE);
                        verifyotp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                backendotp,EnteredOtp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
//                                            Intent intent=new Intent(OtpVerification.this,MainActivity.class);
//                                            startActivity(intent);

                                            userrefrance.child(auth.getCurrentUser().getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Intent intent=new Intent(OtpVerification.this,MainActivity.class);
                                                 //       intent.putExtra("userid",userId);
                                                         startActivity(intent);

                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(OtpVerification.this, "Unable to create account try again", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(OtpVerification.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else {
                        Toast.makeText(OtpVerification.this, "Pleace cheak internet Conncetion", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(OtpVerification.this, "Please Enter 6 digit otp number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}