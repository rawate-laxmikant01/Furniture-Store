package com.example.navigationbar;

import android.content.Intent;

import android.icu.text.DateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;
import com.example.navigationbar.Model.AdressModel;
import com.example.navigationbar.Model.CartModel;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OrderPlaced extends AppCompatActivity {

    Button btn_buynew;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        btn_buynew = findViewById(R.id.btn_buy_new);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid());


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
        String time = df.format(new Date());

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    btn_buynew.postDelayed(new Runnable() {
                        public void run() {
                            btn_buynew.setVisibility(View.VISIBLE);
                        }
                    }, 450);


                    reference.child("chart").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            reference.child("order").child(time).setValue(dataSnapshot.getValue());
                            reference.child("order").child(time).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    for (DataSnapshot s : snapshot.getChildren()) {
                                        s.getRef().child("date").setValue(time);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

                    reference.child("chart").setValue(null);
                    reference.child("information").child("uuid").setValue(auth.getCurrentUser().getUid());

                } catch (Exception e) {

                } finally {

                }
            }//;
        };
        splashTread.start();

        btn_buynew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderPlaced.this, MainActivity.class));
                finish();
            }
        });
    }
}