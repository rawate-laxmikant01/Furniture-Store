package com.example.navigationbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetails extends AppCompatActivity {

    private TextView Pname, Psubdetail, Pprice, Pmrp, Pdiscount, Pdetail, Pquantity,hurry,left;
    private Button btn_addtocart,btn_buy;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("userdata");
        hurry=findViewById(R.id.Hurry);
        left=findViewById(R.id.Left);
        ImageView detailimg = findViewById(R.id.productdetail_img_id);
        Pname = findViewById(R.id.productdetail_name_id);
        Psubdetail = findViewById(R.id.productdetail_subdetail_id);
        Pprice = findViewById(R.id.productdetail_price);
        Pmrp = findViewById(R.id.productdetail_mrpprice);
        Pdiscount = findViewById(R.id.productdetail_discount);
        Pdetail = findViewById(R.id.productdetail_description_id);
        btn_addtocart = findViewById(R.id.btn_addtocart_id);
        Pquantity = findViewById(R.id.productdetail_quantity_id);
        btn_buy=findViewById(R.id.btn_buy_id);


        String totalquantity, category, brand, color;


        String img = getIntent().getStringExtra("img");
        Glide.with(this).load(img).into(detailimg);

        String iname = getIntent().getStringExtra("iname");
        String iprice = getIntent().getStringExtra("iprice");
        String imrp = getIntent().getStringExtra("imrp");
        String idiscount = getIntent().getStringExtra("idiscount");
      //  String Pid = getIntent().getStringExtra("chartproduct");
        totalquantity = getIntent().getStringExtra("totalquantity");
//        id=getIntent().getStringExtra("id");
        // category=getIntent().getStringExtra("category");
        brand = getIntent().getStringExtra("brand");
        color = getIntent().getStringExtra("color");

        Pquantity.setText(totalquantity);
        if (Integer.parseInt(totalquantity) < 10) {
            hurry.setVisibility(View.VISIBLE);
            Pquantity.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);
        }

        Psubdetail.setText(color);
        Pdetail.setText(brand);
        Pname.setText(iname);
        Pprice.setText(iprice);
        Pmrp.setText(imrp);
        Pdiscount.setText(idiscount);
        // Pchartid=getIntent().getStringExtra("chartproduct");

        String Pid=reference.push().getKey();

        Pmrp.setPaintFlags(Pmrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        String  date="date";


        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartModel cartModel = new CartModel(iname, img, iprice, imrp, idiscount, Pid, totalquantity,date);
                reference.child(auth.getCurrentUser().getUid()).child("chart").child(Pid).setValue(cartModel);
                Toast.makeText(ProductDetails.this, "Product added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartModel cartModel = new CartModel(iname, img, iprice, imrp, idiscount, Pid, totalquantity,date);
                reference.child(auth.getCurrentUser().getUid()).child("chart").child(Pid).setValue(cartModel);
                Toast.makeText(ProductDetails.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ProductDetails.this,MyCartActivity.class);
                startActivity(intent);
            }
        });


    }
}