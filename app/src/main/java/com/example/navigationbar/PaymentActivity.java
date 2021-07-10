package com.example.navigationbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PaymentActivity extends AppCompatActivity {


    CheckBox cod,phonepay,card;
    String payment;

    Button btn_confirm;
    ConstraintLayout paymentview;

    String Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cod=findViewById(R.id.cod);
        phonepay=findViewById(R.id.phonepay);
        card=findViewById(R.id.card);
        btn_confirm=findViewById(R.id.btn_confirm_order);
        paymentview=findViewById(R.id.payment_view);

        Total=getIntent().getStringExtra("total");
        btn_confirm.setText("pay "+Total);

        cod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                  phonepay.setChecked(false);
                  card.setChecked(false);
                  payment="cash on delivary";
                }
                else {
                    payment="p";
                }

            }
        });
        phonepay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cod.setChecked(false);
                    card.setChecked(false);
                    payment="phonepay/googlepay";
                }
                else {
                    payment="p";
                }

            }
        });
        card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    phonepay.setChecked(false);
                    cod.setChecked(false);
                    payment="card";
                }
                else {
                    payment="p";
                }

            }
        });



            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(cod.isChecked()||phonepay.isChecked()||card.isChecked()){

                    Intent intent=new Intent(PaymentActivity.this,OrderPlaced.class);
                    startActivity(intent);
                    finish();

                    }
                    else {
                        Toast.makeText(PaymentActivity.this, "Please select any mode of payment", Toast.LENGTH_SHORT).show();
                    }


                }
            });




    }
}