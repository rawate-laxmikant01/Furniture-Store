package com.example.navigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Adapter.CartAdapter;
import com.example.navigationbar.Model.CartModel;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity {

    private ImageView detailimg;
    private TextView Pname,Psubdetail,Pprice,Pmrp,Pdiscount,Pdetail;
    private Button btn_addtocart;

    String Pchartid;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("userdata");


//-------------------------------------------------
        detailimg=findViewById(R.id.productdetail_img_id);
        Pname=findViewById(R.id.productdetail_name_id);
        Psubdetail=findViewById(R.id.productdetail_subdetail_id);
        Pprice=findViewById(R.id.productdetail_price);
        Pmrp=findViewById(R.id.productdetail_mrpprice);
        Pdiscount=findViewById(R.id.productdetail_discount);
        Pdetail=findViewById(R.id.productdetail_description_id);
        btn_addtocart=findViewById(R.id.btn_addtocart_id);



        String img=getIntent().getStringExtra("img");
        Glide.with(this).load(img).into(detailimg);

        String iname= getIntent().getStringExtra("iname");
        String iprice=getIntent().getStringExtra("iprice");
        String imrp=getIntent().getStringExtra("imrp");
        String idiscount=getIntent().getStringExtra("idiscount");
        String Pid=getIntent().getStringExtra("chartproduct");

        Pname.setText(iname);
        Pprice.setText(iprice);
        Pmrp.setText(imrp);
        Pdiscount.setText(idiscount);
       // Pchartid=getIntent().getStringExtra("chartproduct");

        Pmrp.setPaintFlags(Pmrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetails.this, "Product added to cart", Toast.LENGTH_SHORT).show();

                //CartModel cartModel=new CartModel(Pchartid);
                String name,itemimg,price,mrpprice,discount,id;
                ItemGridViewModel itemGridViewModel=new ItemGridViewModel(iname,img,iprice,imrp,idiscount,Pid,null,null,null,null);

                reference.child(auth.getCurrentUser().getUid()).child("chart").child(Pid).setValue(itemGridViewModel);
                //we want user id

            }
        });



    }
}