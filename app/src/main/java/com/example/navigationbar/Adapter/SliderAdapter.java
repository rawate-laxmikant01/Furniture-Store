package com.example.navigationbar.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.navigationbar.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    private int[] images;

    public SliderAdapter(int[] images) {
        this.images = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_sliderimage_layout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class MyViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}

