package com.example.saad.carsales;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Fahaid on 3/5/2017.
 */

public class Buyer_Main_Adapter extends RecyclerView.Adapter<Buyer_Main_Adapter.MyViewHolder>{
    ArrayList<String> ads;
    Context context;
    public Buyer_Main_Adapter (Context context, ArrayList<String>ADs){
        this.context = context;
        this.ads = ADs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_buyer_main,parent,false);

        MyViewHolder VH = new MyViewHolder(view);
        return VH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(ads.get(position));
        holder.ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Ad Number "+String.valueOf(position+1), Toast.LENGTH_SHORT).show();
                Intent details=new Intent(context,Car_details.class);
                context.startActivity(details);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CardView ad;
        public MyViewHolder(View view) {
            super(view);

            title =  (TextView) view.findViewById(R.id.ad_name);
            ad = (CardView) view.findViewById(R.id.ad_card);
        }
    }
}