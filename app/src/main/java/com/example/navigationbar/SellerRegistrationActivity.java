package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationbar.Model.RegistrationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerRegistrationActivity extends AppCompatActivity {

    TextView login;
    EditText reg_name, reg_number, reg_email, reg_password;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Button btn_registerNow;
    FirebaseAuth firebaseAuth;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    String rId;

    // FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        login = findViewById(R.id.register_login);
        reg_name = findViewById(R.id.register_name);
        reg_number = findViewById(R.id.register_Number);
        reg_email = findViewById(R.id.register_email);
        reg_password = findViewById(R.id.register_password);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admin");
        btn_registerNow = findViewById(R.id.btn_sendotp);
        firebaseAuth = FirebaseAuth.getInstance();


        btn_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rName = reg_name.getText().toString().trim();
                String rNumber = reg_number.getText().toString().trim();
                String rEmail = reg_email.getText().toString().trim();
                String rPassword = reg_password.getText().toString().trim();
                rId = databaseReference.push().getKey();//no need


                // String rId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                //    String rId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                RegistrationModel m = new RegistrationModel(rName, rNumber, rEmail, rPassword,rId);

                if (reg_name.getText().toString().isEmpty()) {
                    reg_name.setError(getResources().getString(R.string.name_error));
                    isNameValid = false;
                } else {
                    isNameValid = true;
                }

                if (reg_number.getText().toString().isEmpty()) {
                    reg_number.setError(getResources().getString(R.string.phone_error));
                    isPhoneValid = false;
                } else {
                    isPhoneValid = true;
                }
                if (rEmail.isEmpty()) {
                    reg_email.setError(getResources().getString(R.string.email_error));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(reg_email.getText().toString()).matches()) {
                    reg_email.setError(getResources().getString(R.string.error_invalid_email));
                    isEmailValid = false;
                } else {
                    isEmailValid = true;
                }

                if (reg_password.getText().toString().isEmpty()) {
                    reg_password.setError(getResources().getString(R.string.password_error));
                    isPasswordValid = false;
                } else if (reg_password.getText().length() < 6) {
                    reg_password.setError(getResources().getString(R.string.error_invalid_password));
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }
                if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {


                    databaseReference.child(rId).setValue(m);
                    firebaseAuth.createUserWithEmailAndPassword(rEmail, rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SellerRegistrationActivity.this, "Registered successfully..", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SellerRegistrationActivity.this,SellerLoginActivity.class);
                                //  intent.putExtra("reg_id",rId);
                                startActivity(intent);

                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SellerRegistrationActivity.this, "Registered failed..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

        });
    }

    public void register_login(View view) {
        startActivity(new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class));
    }
}


