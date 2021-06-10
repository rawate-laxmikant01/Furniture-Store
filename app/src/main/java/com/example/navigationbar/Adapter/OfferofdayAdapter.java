package com.example.navigationbar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Model.ItemGridViewModel;
import com.example.navigationbar.ProductDetails;
import com.example.navigationbar.R;

import java.util.ArrayList;

public class OfferofdayAdapter  extends RecyclerView.Adapter<OfferofdayAdapter.itemViewHolder> {

    ArrayList<ItemGridViewModel> item;
    Context mcontexr;

    public OfferofdayAdapter(ArrayList<ItemGridViewModel> item, Context mcontexr) {
        this.item = item;
        this.mcontexr = mcontexr;
    }



    @NonNull
    @Override
    public OfferofdayAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_gridview,parent,false);
        return new OfferofdayAdapter.itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferofdayAdapter.itemViewHolder holder, int position) {

        ItemGridViewModel itemm=item.get(position);

        holder.itemname.setText(item.get(position).getName());

        holder.price.setText(item.get(position).getPrice());

        holder.mrpprice.setText(item.get(position).getMrpprice());

        holder.discount.setText(item.get(position).getDiscount());

        String img=itemm.getItemimg();
        String iname=itemm.getName();
        String iprice=itemm.getPrice();
        String imrp=itemm.getMrpprice();
        String idiscount=itemm.getDiscount();
        String iid=itemm.getId();

        holder.itemviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontexr, ProductDetails.class);

                intent.putExtra("img",img);
                intent.putExtra("iname",iname);
                intent.putExtra("iprice",iprice);
                intent.putExtra("imrp",imrp);
                intent.putExtra("idiscount",idiscount);
                intent.putExtra("chartproduct",iid);

                mcontexr.startActivity(intent);
            }
        });



        //   String img=itemmodel.getItemimg();
//
        Glide.with(mcontexr)
                .load(img)
                .into(holder.itemimg);


    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimg;
        TextView itemname,mrpprice,price,discount;
        View itemviewbtn;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemviewbtn=itemView.findViewById(R.id.item_cardview_id);
            itemimg=itemView.findViewById(R.id.img_gridview_id);
            itemname=itemView.findViewById(R.id.tv_name);
            mrpprice=itemView.findViewById(R.id.tv_mrpprice);
            price=itemView.findViewById(R.id.tv_price);
            discount=itemView.findViewById(R.id.tv_discount);

            mrpprice.setPaintFlags(mrpprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



            //   TextView someTextView = (TextView) findViewById(R.id.some_text_view);
//            someTextView.setText(someString);
//            someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
