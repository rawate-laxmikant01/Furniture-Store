package com.example.navigationbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderDetails extends AppCompatActivity {

    TextView orderid,ordername,orderprice,ordersubdetail;
    ImageView orderimg,shippingtik,deliverdtik,returntik;

    LinearLayout return_order;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference, ref;
    Button btn_ordercancle,btn_needhelp,btn_return;

    TextView name,adress, number;
    TextView itemprice, item, discount, total, save, delivaryfee;
    int itemtotal;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("userdata");

        itemprice = findViewById(R.id.order_ad_price);
        item = findViewById(R.id.order_ad_item);
        discount = findViewById(R.id.order_ad_discount);
        total = findViewById(R.id.order_ad_total);
        save = findViewById(R.id.order_ad_save);
        delivaryfee = findViewById(R.id.order_ad_delivary);
        name = findViewById(R.id.order_ad_name);
        adress = findViewById(R.id.order_ad_address);
        number = findViewById(R.id.order_ad_number);
        orderid=findViewById(R.id.order_id);
        ordername=findViewById(R.id.order_name_detail);
        orderprice=findViewById(R.id.order_price);
        orderimg=findViewById(R.id.order_img);
        shippingtik=findViewById(R.id.shipping_orderid);
        deliverdtik=findViewById(R.id.delivered_orderid);
        returntik=findViewById(R.id.return_orderid);

        btn_ordercancle=findViewById(R.id.btn_order_cancelled);
        btn_needhelp=findViewById(R.id.btn_needhelp);
        btn_return=findViewById(R.id.btn_order_return);
        return_order=findViewById(R.id.return_view);

       // closeButton = (Button) findViewById(R.id.button);


        String uuid=getIntent().getStringExtra("uuid");
        String date=getIntent().getStringExtra("orderdate");
        String orderid=getIntent().getStringExtra("orderid");
        String status=getIntent().getStringExtra("status");

        setOrderData();
        if(status.equals("Shipped")){
            shippingtik.setBackgroundResource(R.drawable.righttik);
        }
        if(status.equals("Delivered")){
            shippingtik.setBackgroundResource(R.drawable.righttik);
            deliverdtik.setBackgroundResource(R.drawable.righttik);

            btn_return.setVisibility(View.VISIBLE);
            btn_ordercancle.setVisibility(View.INVISIBLE);

        }
        if (status.equals("Cancelled Order")){
            shippingtik.setBackgroundResource(R.drawable.righttik);
            deliverdtik.setBackgroundResource(R.drawable.wrongtik);
        }
        if (status.equals("Return Pending")){
            return_order.setVisibility(View.VISIBLE);
            btn_ordercancle.setVisibility(View.INVISIBLE);
            shippingtik.setBackgroundResource(R.drawable.righttik);
            deliverdtik.setBackgroundResource(R.drawable.righttik);
           // returntik.setBackgroundResource(R.drawable.righttik);
        }

        if(status.equals("Return")){
            return_order.setVisibility(View.VISIBLE);
            shippingtik.setBackgroundResource(R.drawable.righttik);
            deliverdtik.setBackgroundResource(R.drawable.righttik);
            returntik.setBackgroundResource(R.drawable.righttik);
            btn_ordercancle.setVisibility(View.INVISIBLE);
        }

        builder = new AlertDialog.Builder(this);
        btn_ordercancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want to Cancle the Order?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reference.child(auth.getCurrentUser().getUid()).child("order")
                                    .child(date).child(orderid).child("status").setValue("Cancelled Order");
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Order Cancle");
                alert.show();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder.setMessage("Do you want to Return the Order?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reference.child(auth.getCurrentUser().getUid()).child("order")
                                        .child(date).child(orderid).child("status").setValue("Return Pending");
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Return Request");
                alert.show();
            }
        });
    }

    private void setOrderData() {
        name.setText(getIntent().getStringExtra("username"));
        adress.setText(getIntent().getStringExtra("adress"));
        number.setText(getIntent().getStringExtra("number"));
        String price=getIntent().getStringExtra("price");
        itemprice.setText(price);
        String dis=getIntent().getStringExtra("discount");



        orderid.setText(getIntent().getStringExtra("id"));
        ordername.setText(getIntent().getStringExtra("name"));
        orderprice.setText(getIntent().getStringExtra("price"));
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        if(Integer.parseInt(price)<499){
            delivaryfee.setText("50");
            itemtotal=Integer.parseInt(price)+50;
            total.setText(String.valueOf(itemtotal));
            discount.setText(dis);
            save.setText(dis);
        }else {
            delivaryfee.setText("Free");
            itemtotal=Integer.parseInt(price);
            total.setText(String.valueOf(itemtotal));
            discount.setText(String.valueOf(Integer.parseInt(dis)+50));
            save.setText(String.valueOf(Integer.parseInt(dis)+50));
        }
        String img=getIntent().getStringExtra("img");
        Glide.with(this).load(img).into(orderimg);
    }
}