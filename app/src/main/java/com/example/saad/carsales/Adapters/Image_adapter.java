package com.example.saad.carsales.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saad.carsales.R;

import java.util.ArrayList;

public class Image_adapter extends RecyclerView.Adapter<Image_adapter.MyViewHolder>{
        ArrayList<Integer> img;
        Context context;
public Image_adapter (Context context, ArrayList<Integer>image){
        this.context = context;
        this.img = image;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_adapter,parent,false);

        MyViewHolder VH = new MyViewHolder(view);
        return VH;
        }

@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.photos.setImageResource(img.get(position));
        }


@Override
public int getItemCount() {
        return img.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView photos;

    public MyViewHolder(View view) {
        super(view);
        photos =  (ImageView) view.findViewById(R.id.car_images);

    }
}
}
