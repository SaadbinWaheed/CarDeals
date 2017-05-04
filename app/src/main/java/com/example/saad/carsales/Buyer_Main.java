package com.example.saad.carsales;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Main extends AppCompatActivity {

    RecyclerView RV;
    ArrayList<String> ads;
    Folder_Adapter adap;
    ArrayList<String> titles,year,carOwner;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);

        Firebase.setAndroidContext(this);

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/");
        titles=new ArrayList<>();
        year=new ArrayList<>();
        carOwner=new ArrayList<>();


        RV = (RecyclerView) findViewById(R.id.RV);
        adap = new Folder_Adapter(this,getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RV.setAdapter(adap);
        RV.setLayoutManager(mLayoutManager);

    }

    public List<Add> getData()
    {
        final List<Add> data= new ArrayList<>();

        ref.child("Adverts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add current= new Add();

                titles.add(dataSnapshot.child("Model").getValue().toString());
                year.add(dataSnapshot.child("Model Year").getValue().toString());
                carOwner.add(dataSnapshot.child("Seller Name").getValue().toString());

                current.setTitle(dataSnapshot.child("Model").getValue().toString());
                current.setYear(dataSnapshot.child("Model Year").getValue().toString());
                current.setCar_owner(dataSnapshot.child("Seller Name").getValue().toString());

                data.add(current);
                adap.notifyDataSetChanged();
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
//        Toast.makeText(this,Integer.toString(titles.size()),Toast.LENGTH_SHORT).show();
//        for (int i=0;i<titles.size();i++)
//        {
//
//
//
//
//
//        }

        return data;
    }
}
