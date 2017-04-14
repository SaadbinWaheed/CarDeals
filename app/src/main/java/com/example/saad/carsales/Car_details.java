package com.example.saad.carsales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class Car_details extends AppCompatActivity {
    ArrayList<Integer> image=new ArrayList<Integer>();
TextView carname,year,add,regyear,color,mileage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        image.add(R.drawable.buyer);
        image.add(R.drawable.sell);
        image.add(R.drawable.car);
        image.add(R.drawable.buyer);
        SharedPreferences details = getSharedPreferences("my_prefs", 0);
        String car_name = details.getString("Model","");
        String clr=details.getString("Color","");
        String reg_year = details.getString("Registraion_Year", "");
        String address = details.getString("Address", "");
        String miles = details.getString("Mileage", "");
        Image_adapter car_img=new Image_adapter(Car_details.this,image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView imageList = (RecyclerView) findViewById(R.id.slider);
        imageList.setLayoutManager(layoutManager);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setAdapter(car_img);
        carname=(TextView) findViewById(R.id.car_name);
        color=(TextView) findViewById(R.id.exterior_colorshow);
        add=(TextView) findViewById(R.id.location);
        mileage=(TextView) findViewById(R.id.miles);
        color.setText(clr);
        add.setText(address);
        mileage.setText(miles);
        carname.setText(car_name);
    }
    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected void onStart()
    {
        super.onStart();

    }
}
