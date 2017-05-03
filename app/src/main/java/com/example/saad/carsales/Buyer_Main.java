package com.example.saad.carsales;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Main extends AppCompatActivity {

    RecyclerView RV;
    ArrayList<String> ads;
    Folder_Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);
        RV = (RecyclerView) findViewById(R.id.RV);



        adap = new Folder_Adapter(this,getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RV.setAdapter(adap);
        RV.setLayoutManager(mLayoutManager);

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
}
