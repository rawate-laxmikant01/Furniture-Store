package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyCartActivity extends AppCompatActivity {

    private Button btn_shopNow,btn_placeorder;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference,ref;
    private RecyclerView recyclerView;
    private ArrayList<CartModel> cartlist;
    ProgressBar cartprogess;

    TextView TotalPrice,num_items,totalitemdiscount,itemprice,save,delivaryfee,total;
    private View empty,pricedetail,pricetotal;

    int delivary;
    long itprice,itemdis,totalsave,t;
    String items;

    Boolean Adress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        cartprogess=findViewById(R.id.cartprogress);
        num_items=findViewById(R.id.items);
        totalitemdiscount=findViewById(R.id.itemdiscount);
        itemprice=findViewById(R.id.itemprices);
        save=findViewById(R.id.save);
        delivaryfee=findViewById(R.id.delivarycharge);
        total=findViewById(R.id.grandtotal);


        btn_placeorder=findViewById(R.id.placeorder);
        TotalPrice = findViewById(R.id.totalprice);
        btn_shopNow = findViewById(R.id.shopnow_id);
        empty = findViewById(R.id.empty_cart_id);
        pricedetail=findViewById(R.id.pricedetail);
        pricetotal=findViewById(R.id.pricetotal);
        recyclerView = findViewById(R.id.cart_recyclerview_id);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartlist = new ArrayList<>();

        empty.setVisibility(View.INVISIBLE);
        pricedetail.setVisibility(View.INVISIBLE);
        pricetotal.setVisibility(View.INVISIBLE);

        reference.child("chart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel m = dataSnapshot.getValue(CartModel.class);
                    cartlist.add(m);

                }

                CartAdapter cartAdapter = new CartAdapter(cartlist, MyCartActivity.this);
                if (cartAdapter.getItemCount() != 0) {
                    cartprogess.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    pricedetail.setVisibility(View.VISIBLE);
                    pricetotal.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(cartAdapter);

                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            itprice = dataSnapshot.child("Cartvalue").child("Total").getValue(long.class);
                            items=dataSnapshot.child("Cartvalue").child("items").getValue(String.class);
                            itemdis=dataSnapshot.child("Cartvalue").child("discount").getValue(long.class);

                            num_items.setText(items);
                            totalitemdiscount.setText(String.valueOf(itemdis));
                            itemprice.setText(String.valueOf(itprice));

                            if(itprice>499){
                                delivary=0;
                                delivaryfee.setText("free");
                                totalsave=itemdis+50;
                                save.setText(String.valueOf(totalsave));

                            }
                            else {
                                delivary=50;
                                delivaryfee.setText("50");
                                save.setText(String.valueOf(itemdis));
                            }


                            t=itprice+delivary;
                            TotalPrice.setText(String.valueOf(t));
                            total.setText(String.valueOf(t));



                            if (dataSnapshot.child("Address").exists()){
//                    Toast.makeText(MyCartActivity.this, "Adres exists", Toast.LENGTH_SHORT).show();
                                Adress=true;
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MyCartActivity.this, "cart error "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    reference.addValueEventListener(postListener);

                } else {
                    cartprogess.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                      pricedetail.setVisibility(View.INVISIBLE);
                      pricetotal.setVisibility(View.INVISIBLE);

                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyCartActivity.this, "unable to load cart" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




      //  ref = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid()).child("Total");
     //   ref = firebaseDatabase.getReference("userdata").child(auth.getCurrentUser().getUid());



        btn_shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCartActivity.this, MainActivity.class));
            }
        });


        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Adress){
                    Intent i=new Intent(MyCartActivity.this,OrderSummary.class);
//                    i.putExtra("itprice",itprice);
//                    i.putExtra("itemdis",itemdis);
//                    i.putExtra("items",items);

                    startActivity(i);
                }else {
                    Intent intent=new Intent(MyCartActivity.this,AddDeliveryAddress.class);
                    startActivity(intent);
                }
            }
        });
    }
}