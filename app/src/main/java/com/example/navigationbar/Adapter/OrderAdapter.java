package com.example.navigationbar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Model.CartModel;
import com.example.navigationbar.Model.OrderModel;
import com.example.navigationbar.OrderDetails;
import com.example.navigationbar.R;

import java.util.ArrayList;
import static com.example.navigationbar.R.color.green;
import static com.example.navigationbar.R.color.red;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderviewholder> {

    final ArrayList<OrderModel> orderlist;
    final Context ordercontext;

    public OrderAdapter(ArrayList<OrderModel> orderlist, Context ordercontext) {
        this.orderlist = orderlist;
        this.ordercontext = ordercontext;

    }


    @NonNull
    @Override
    public OrderAdapter.orderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_myorder_view, parent, false);
        return new orderviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.orderviewholder holder, int position) {

        String status = orderlist.get(position).getStatus();
        if(status.equals("Shipped")){
            holder.Delivarystatus.setText(status);
            holder.Delivarystatus.setTextColor(ordercontext.getResources().getColor(green));
        }
        if(status.equals("Ordered")){
            holder.Delivarystatus.setText(status);
            holder.Delivarystatus.setTextColor(ordercontext.getResources().getColor(green));
        }
        if(status.equals("Cancelled Order")){
            holder.Delivarystatus.setText(status);
            holder.Delivarystatus.setTextColor(ordercontext.getResources().getColor(red));
        }
        if(status.equals("Return")){

            holder.Delivarystatus.setText(status);
            holder.Delivarystatus.setTextColor(ordercontext.getResources().getColor(R.color.orange));
        }
        if(status.equals("Return pending")){
            holder.Delivarystatus.setText(status);
            holder.Delivarystatus.setTextColor(ordercontext.getResources().getColor(R.color.yello));
        }
        else {
            holder.Delivarystatus.setText(status);
        }



        String adress=orderlist.get(position).getAdress();
        holder.Adress.setText(adress);
        String number=orderlist.get(position).getNumber();
        holder.usernumber.setText(number);

        String img = orderlist.get(position).getItemimg();
        Glide.with(ordercontext).load(img).into(holder.productimg);

        holder.Name.setText(orderlist.get(position).getName());
        String id = orderlist.get(position).getId();
        String price = orderlist.get(position).getPrice();
        String name = orderlist.get(position).getName();
        holder.username.setText(orderlist.get(position).getNames());

        holder.orderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ordercontext, OrderDetails.class);
                intent.putExtra("id", id);
                intent.putExtra("price", price);
                intent.putExtra("name", name);
                intent.putExtra("img", img);

                intent.putExtra("status",status);
             //   intent.putExtra("subdetail",orderlist.get(position).get)

                intent.putExtra("username",orderlist.get(position).getNames());
                intent.putExtra("adress",adress);
                intent.putExtra("number",number);
                intent.putExtra("price",orderlist.get(position).getPrice());
                intent.putExtra("discount",orderlist.get(position).getDiscount());
                intent.putExtra("uuid",orderlist.get(position).getUuid());
               intent.putExtra("orderdate",orderlist.get(position).getDate());
                intent.putExtra("orderid",orderlist.get(position).getId());
                ordercontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public static class orderviewholder extends RecyclerView.ViewHolder {

        View orderview;
        ImageView productimg;
        TextView Delivarystatus, Name,Adress,username,usernumber;

        public orderviewholder(@NonNull View itemView) {
            super(itemView);

            Delivarystatus = itemView.findViewById(R.id.delivarystatus);
            Name = itemView.findViewById(R.id.ordered_name);
            productimg = itemView.findViewById(R.id.image_order);
            orderview = itemView.findViewById(R.id.myorder_list);
            Adress=itemView.findViewById(R.id.ordered_adress);
            username=itemView.findViewById(R.id.ordered_username);
            usernumber=itemView.findViewById(R.id.ordered_number);

        }


    }
}