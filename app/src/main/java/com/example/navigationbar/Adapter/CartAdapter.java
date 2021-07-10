package com.example.navigationbar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Model.CartModel;
import com.example.navigationbar.MyCartActivity;
import com.example.navigationbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartviewholder> {

    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference reference;
    final ArrayList<CartModel> cartlist;
    final Context cartcontext;


    int tsum = 0;
    int tdis = 0;


    public CartAdapter(ArrayList<CartModel> cartlist, Context cartcontext) {
        this.cartlist = cartlist;
        this.cartcontext = cartcontext;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("userdata").child(Objects.requireNonNull(auth.getCurrentUser()).getUid());

    }


    @NonNull
    @Override
    public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_product, parent, false);
        CartAdapter.cartviewholder viewHolder = new CartAdapter.cartviewholder(view);

        return (viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull cartviewholder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date afterSevenday = calendar.getTime();

       // "EEE, d MMM yyyy HH:mm:ss Z"
      //  Wed, 4 Jul 2001 12:08:56 -0700

        SimpleDateFormat df = new SimpleDateFormat("EEE  MMM d", Locale.US);
        String tomorrowAsString = df.format(afterSevenday);

        holder.date.setText("Delivery by "+tomorrowAsString);
       // Toast.makeText(cartcontext, ""+tomorrowAsString, Toast.LENGTH_SHORT).show();


        int itemprice = Integer.parseInt(cartlist.get(position).getPrice());
        int itemdiscount = Integer.parseInt(cartlist.get(position).getDiscount());
        holder.name.setText(cartlist.get(position).getName());
        holder.price.setText(String.valueOf(itemprice));
        holder.mrp.setText(cartlist.get(position).getMrpprice());
        holder.discount.setText(cartlist.get(position).getDiscount());
        String id = cartlist.get(position).getId();

        String img = cartlist.get(position).getItemimg();
        Glide.with(cartcontext).load(img).into(holder.image);





        int itemquantity = Integer.parseInt(holder.quantity.getText().toString());

//        if (itemquantity == 1) {
            tsum = tsum + itemprice * itemquantity;
            tdis = tdis + itemdiscount * itemquantity;
//        } else {
//            tdis = tdis + itemdiscount * itemquantity;
//            tsum = tsum + itemprice * itemquantity;
//            Intent intent = new Intent(cartcontext, MyCartActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            notifyDataSetChanged();
//            cartcontext.startActivity(intent);
       // }

        String items = String.valueOf(cartlist.size());
        reference.child("Cartvalue").child("items").setValue(items);
        reference.child("Cartvalue").child("Total").setValue(tsum);
        reference.child("Cartvalue").child("discount").setValue(tdis);

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("chart").child(cartlist.get(position).getId()).setValue(null);

                                    cartlist.remove(cartlist.get(position));
                                    Intent intent = new Intent(cartcontext, MyCartActivity.class);
                                    notifyDataSetChanged();
                                    cartcontext.startActivity(intent);
                                  //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    Toast.makeText(cartcontext, "remove", Toast.LENGTH_SHORT).show();
           }
        });

 //        holder.removeItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reference.child("chart").child(cartlist.get(position).getId()).removeValue()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//
//                                    Intent intent = new Intent(cartcontext, MyCartActivity.class);
//                                    cartlist.remove(cartlist.get(position));
//                                    notifyDataSetChanged();
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    Toast.makeText(cartcontext, "remove", Toast.LENGTH_SHORT).show();
//                                    cartcontext.startActivity(intent);
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(cartcontext, "Unable to removed item please try again", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public static class cartviewholder extends RecyclerView.ViewHolder {

        TextView name, price, mrp, discount,date;
        ImageView image;
        Button removeItem;
        EditText quantity;

        public cartviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cart_pname_id);
            price = itemView.findViewById(R.id.cart_price_id);
            mrp = itemView.findViewById(R.id.cart_mrp_id);
            discount = itemView.findViewById(R.id.cart_discout_id);
            image = itemView.findViewById(R.id.cart_product_image_id);
            removeItem = itemView.findViewById(R.id.btn_removeCart_product_id);
            quantity = itemView.findViewById(R.id.default_quantity);
            date=itemView.findViewById(R.id.delivarydateid);
            mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }


    }
}
