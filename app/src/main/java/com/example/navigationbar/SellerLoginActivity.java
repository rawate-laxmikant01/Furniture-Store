package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth firebaseAuth;
    boolean isEmailValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        email=findViewById(R.id.email_login_id);
        password=findViewById(R.id.password_login_id);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void logIn_now(View view) {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();


        //  Toast.makeText(this, ""+Rid, Toast.LENGTH_LONG).show();


        if (Email.isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            password.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }
        if (isEmailValid && isPasswordValid) {
            firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SellerLoginActivity.this, MainActivity.class);
                        //   intent.putExtra("reg_log_id", Rid);
                        startActivity(intent);
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellerLoginActivity.this, "Login Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public void registration_Now(View view) {
        startActivity(new Intent(SellerLoginActivity.this,SellerRegistrationActivity.class));
    }
}