package com.example.navigationbar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationbar.Model.CatagoriesModel;
import com.example.navigationbar.R;
import com.example.navigationbar.categories.BedActivity;

import java.util.ArrayList;

public class CatagoriesAdapter extends RecyclerView.Adapter<CatagoriesAdapter.myviewHolder> {

    ArrayList<CatagoriesModel> list;
    Context mcontext;

    public CatagoriesAdapter(ArrayList<CatagoriesModel> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_catagories_view,parent,false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());

        String cimg=list.get(position).getImg();

        Glide.with(mcontext).load(cimg).into(holder.img);

        holder.catergoryview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mcontext.startActivity(new Intent(mcontext, BedFragment.class));
                Toast.makeText(mcontext, "bed", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mcontext, BedActivity.class);
                intent.putExtra("category",list.get(position).getName());

                mcontext.startActivity(intent);
//
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        View catergoryview;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageview_id);
            name=itemView.findViewById(R.id.catagoreis_name);
            catergoryview=itemView.findViewById(R.id.categoryView);

        }
    }
}
