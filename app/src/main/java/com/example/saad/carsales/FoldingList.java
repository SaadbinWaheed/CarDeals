package com.example.saad.carsales;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class FoldingList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Folder_Adapter adapter;
    Firebase ref;
    ArrayList<String> titles,year,carOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_list);

        Firebase.setAndroidContext(this);

        titles=new ArrayList<>();
        year=new ArrayList<>();
        carOwner=new ArrayList<>();

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/").child("Adverts");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                titles.add(dataSnapshot.child("Model").getKey());
                year.add(dataSnapshot.child("Model Year").getKey());
                carOwner.add(dataSnapshot.child("Seller Name").getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.folder_recycler_view);
        adapter = new Folder_Adapter(this,getData());

         recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public List<Add> getData()
    {
        List<Add> data= new ArrayList<>();



        for (int i=0;i<titles.size();i++)
        {

            Add current= new Add();

            current.setTitle(titles.get(i));
            current.setYear(year.get(i));
            current.setCar_owner(carOwner.get(i));

            data.add(current);

        }

        return data;
    }

}
