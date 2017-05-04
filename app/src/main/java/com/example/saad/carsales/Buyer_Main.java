package com.example.saad.carsales;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Main extends AppCompatActivity {

    RecyclerView RV;

    Folder_Adapter adap;

    boolean isFabOpen = false;
    FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);
        RV = (RecyclerView) findViewById(R.id.RV);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_backward);


        adap = new Folder_Adapter(this,getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RV.setAdapter(adap);
        RV.setLayoutManager(mLayoutManager);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                //isFabOpen = true;
            }
        });
    }

    public static List<Add> getData()
    {
        List<Add> data= new ArrayList<>();

        String[] titles={ "Audi", "BMW", "Land Cruiser", "Range Rover", "Lamborghini", "Ferrari", "Hummer"};
        String[] year={"2017", "2011", "2016", "2017","2018","2019","2050"};
        String[] carOwner={"Fahad", "Musab", "Saad", "Usaid","Malak","Raja","Notty Boi"};

        for (int i=0;i<titles.length;i++)
        {

            Add current= new Add();

            current.setTitle(titles[i]);
            current.setYear(year[i]);
            current.setCar_owner(carOwner[i]);

            data.add(current);

        }

        return data;
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            //  Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            // Log.d("Raj","open");

        }
    }
}
