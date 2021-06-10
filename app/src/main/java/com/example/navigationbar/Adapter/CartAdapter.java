package com.example.navigationbar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
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
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.MyCartActivity;
import com.example.navigationbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartviewholder> {

    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference reference;

    ArrayList<CartModel> cartlist;
    Context cartcontext;

    private  static int count=1;
    String quantityval;

    public CartAdapter(ArrayList<CartModel> cartlist, Context cartcontext) {
        this.cartlist = cartlist;
        this.cartcontext = cartcontext;
    }


    @NonNull
    @Override
    public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_product, parent, false);
        return new cartviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartviewholder holder, int position) {

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("userdata").child(auth.getCurrentUser().getUid()).child("chart");


        holder.name.setText(cartlist.get(position).getName());
        holder.price.setText(cartlist.get(position).getPrice());
        holder.mrp.setText(cartlist.get(position).getMrpprice());
        holder.discount.setText(cartlist.get(position).getDiscount());
        String id=cartlist.get(position).getId();

        String img=cartlist.get(position).getItemimg();

        Glide.with(cartcontext).load(img).into(holder.image);

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(cartcontext, MyCartActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            cartcontext.startActivity(intent);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(cartcontext, "Unable to removed item please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
                    count++;
                    quantityval= Integer.toString(count);
                    holder.quantity.setText(quantityval);
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        count--;
                        quantityval= Integer.toString(count);
                        holder.quantity.setText(quantityval);


                }
            });



    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public static class cartviewholder extends RecyclerView.ViewHolder {

        TextView name, price, mrp, discount;
        ImageView image;
        Button removeItem,add,minus;
        EditText quantity;

        public cartviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cart_pname_id);
            price = itemView.findViewById(R.id.cart_price_id);
            mrp = itemView.findViewById(R.id.cart_mrp_id);
            discount = itemView.findViewById(R.id.cart_discout_id);
            image = itemView.findViewById(R.id.cart_product_image_id);
            removeItem=itemView.findViewById(R.id.btn_removeCart_product_id);
            add=itemView.findViewById(R.id.btn_add);
            minus=itemView.findViewById(R.id.btn_minus);
            quantity=itemView.findViewById(R.id.default_quantity);

            mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
