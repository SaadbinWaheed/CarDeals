package com.example.saad.carsales;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Buyer_Main extends AppCompatActivity {
    RecyclerView RV;
    ArrayList<String> ads;
    Buyer_Main_Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);
        RV = (RecyclerView) findViewById(R.id.RV);

        ads = new ArrayList<String>();
        for (int i = 0; i<=15;i++){
            ads.add("Ad # "+String.valueOf(i+1));
        }

        adap = new Buyer_Main_Adapter(Buyer_Main.this,ads);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        RV.setLayoutManager(mLayoutManager);
        RV.setItemAnimator(new DefaultItemAnimator());
        RV.setAdapter(adap);

    }
}
