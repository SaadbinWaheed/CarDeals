package com.example.saad.carsales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class FoldingList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Folder_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_list);

        recyclerView=(RecyclerView) findViewById(R.id.folder_recycler_view);
        adapter = new Folder_Adapter(this,getData());

         recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


    public static List<Add> getData()
    {
        List<Add> data= new ArrayList<>();

        String[] titles={"Corolla", "Mehran", "Bentley", "Civic"};
        String[] year={"2017", "2011", "2016", "2017"};
        String[] carOwner={"Fahad", "Musab", "Saad", "Usaid"};

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
