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

public class FourItemViewAdapter extends RecyclerView.Adapter<FourItemViewAdapter.itemViewHolder> {

    ArrayList<ItemGridViewModel> item;
    Context mcontexr;

    public FourItemViewAdapter(ArrayList<ItemGridViewModel> item, Context mcontexr) {
        this.item = item;
        this.mcontexr = mcontexr;
    }


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_gridview, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        ItemGridViewModel itemmodel = item.get(position);

        holder.itemname.setText(itemmodel.getName());
        holder.price.setText(itemmodel.getPrice());
        holder.mrpprice.setText(itemmodel.getMrpprice());
        holder.discount.setText(itemmodel.getDiscount());

         String img = itemmodel.getItemimg();
//        String iname = itemmodel.getName();
//        String iprice = itemmodel.getPrice();
//        String imrp = itemmodel.getMrpprice();
//        String idiscount = itemmodel.getDiscount();
//        String iid = itemmodel.getId();
//
//        String totalquantity, category, brand, color;
//
//        totalquantity = itemmodel.getTotalquantity();
//        category = itemmodel.getCategory();
//        brand = itemmodel.getBrand();
//        color = itemmodel.getColor();

        holder.itemviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontexr, ProductDetails.class);

                intent.putExtra("img", img);
                intent.putExtra("iname", itemmodel.getName());
                intent.putExtra("iprice", itemmodel.getPrice());
                intent.putExtra("imrp", itemmodel.getMrpprice());
                intent.putExtra("idiscount", itemmodel.getDiscount());
                intent.putExtra("chartproduct", itemmodel.getId());
                intent.putExtra("totalquantity", itemmodel.getTotalquantity());
                intent.putExtra("category", itemmodel.getCategory());
                intent.putExtra("brand", itemmodel.getBrand());
                intent.putExtra("color", itemmodel.getColor());

                mcontexr.startActivity(intent);
            }
        });
        Glide.with(mcontexr)
                .load(img)
                .into(holder.itemimg);

   }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimg;
        TextView itemname, mrpprice, price, discount;
        View itemviewbtn;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemviewbtn = itemView.findViewById(R.id.item_cardview_id);
            itemimg = itemView.findViewById(R.id.img_gridview_id);
            itemname = itemView.findViewById(R.id.tv_name);
            mrpprice = itemView.findViewById(R.id.tv_mrpprice);
            price = itemView.findViewById(R.id.tv_price);
            discount = itemView.findViewById(R.id.tv_discount);

            mrpprice.setPaintFlags(mrpprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
