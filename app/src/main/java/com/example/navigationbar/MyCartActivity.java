package com.example.navigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.navigationbar.Adapter.CartAdapter;
import com.example.navigationbar.Adapter.ItemGridViewAdapter;
import com.example.navigationbar.Model.CartModel;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    private Button btn_shopNow;
    private FirebaseAuth auth;

    private RecyclerView recyclerView;
   // private CartAdapter cartAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ArrayList<CartModel> cartlist;

    private View empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        btn_shopNow = findViewById(R.id.shopnow_id);
        empty=findViewById(R.id.empty_cart_id);
        recyclerView = findViewById(R.id.cart_recyclerview_id);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid()).child("chart");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartlist = new ArrayList<>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel m = dataSnapshot.getValue(CartModel.class);

                    cartlist.add(m);
                }
                CartAdapter cartAdapter=new CartAdapter(cartlist,MyCartActivity.this);

                if (cartAdapter.getItemCount() != 0) {
                    recyclerView.setAdapter(cartAdapter);
                }else {

                    empty.setVisibility(View.VISIBLE);
                    Toast.makeText(MyCartActivity.this, "Empty", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyCartActivity.this, "unable to load cart"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });


        btn_shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCartActivity.this, MainActivity.class));
            }
        });





    }



}