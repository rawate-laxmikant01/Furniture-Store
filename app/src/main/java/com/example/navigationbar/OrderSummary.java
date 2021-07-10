package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbar.Adapter.CartAdapter;
import com.example.navigationbar.Model.AdressModel;
import com.example.navigationbar.Model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderSummary extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference, ref;
    private ArrayList<CartModel> cartlist;

    TextView name, type, adress, number, price;
    TextView itemprice, item, discount, total, save, delivaryfee;

    Button btn_changeadress, btn_continue;

    String Total,ProductId,useraddress, username,num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        itemprice = findViewById(R.id.summ_price);
        item = findViewById(R.id.summ_item);
        discount = findViewById(R.id.summ_discount);
        total = findViewById(R.id.sum_total);
        save = findViewById(R.id.summ_save);
        delivaryfee = findViewById(R.id.summ_delivary);


        name = findViewById(R.id.summary_name);
        type = findViewById(R.id.summary_type);
        adress = findViewById(R.id.sumary_address);
        number = findViewById(R.id.summary_number);
        price = findViewById(R.id.summary_totalprice);

        btn_changeadress = findViewById(R.id.btn_change_adress);
        btn_continue = findViewById(R.id.summary_continue);

        recyclerView = findViewById(R.id.summary_recyclerview_id);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartlist = new ArrayList<>();



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdressModel adressModel = dataSnapshot.child("Address").getValue(AdressModel.class);
                username=adressModel.getFullname();
                name.setText(username);

                type.setText(adressModel.getTypeAd());
                useraddress=adressModel.getHouseno() + " " + adressModel.getRoadname() + " " + adressModel.getCity() + " " + adressModel.getState() + " " + adressModel.getPincode();
                adress.setText(useraddress);
                num=adressModel.getPhoneno();
                number.setText(num);
                setOrderPriceDetail(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderSummary.this, "error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        reference.addValueEventListener(postListener);


        reference.child("chart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel m = dataSnapshot.getValue(CartModel.class);

                    dataSnapshot.getRef().child("adress").setValue(useraddress);

                    dataSnapshot.getRef().child("Names").setValue(username);
                    dataSnapshot.getRef().child("number").setValue(num);
                   // dataSnapshot.getRef().child("total").setValue(total);
                    dataSnapshot.getRef().child("uuid").setValue(auth.getCurrentUser().getUid());
                  //  dataSnapshot.getRef().child("discount").setValue(discount);
                  //  dataSnapshot.getRef().child("orderdate").setValue(time);
                    dataSnapshot.getRef().child("status").setValue("Ordered");
                  //  dataSnapshot.getRef().child("orders").setValue(dataSnapshot.getValue());
                    cartlist.add(m);

                }
                CartAdapter cartAdapter = new CartAdapter(cartlist, OrderSummary.this);
                recyclerView.setAdapter(cartAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderSummary.this, "unable to load cart" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummary.this, PaymentActivity.class);
                intent.putExtra("total", Total);
                startActivity(intent);
            }
        });

        btn_changeadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummary.this, AddDeliveryAddress.class);
                startActivity(intent);
            }
        });

    }

    private void setOrderPriceDetail(DataSnapshot dataSnapshot) {
        int delivary;
        String items;
        long itprice, itemdis, totalsave, t;

        itprice = dataSnapshot.child("Cartvalue").child("Total").getValue(long.class);
        itemdis = dataSnapshot.child("Cartvalue").child("discount").getValue(long.class);
         items = dataSnapshot.child("Cartvalue").child("items").getValue(String.class);

        item.setText(items);
        discount.setText(String.valueOf(itemdis));
        itemprice.setText(String.valueOf(itprice));

        if (itprice > 499) {
            delivary = 0;
            delivaryfee.setText("free");
            totalsave = itemdis + 50;
            save.setText(String.valueOf(totalsave));

        } else {
            delivary = 50;
            delivaryfee.setText("50");
            save.setText(String.valueOf(itemdis));
        }

        t = itprice + delivary;
        Total = String.valueOf(t);
        price.setText(Total);
        total.setText(Total);


    }
}