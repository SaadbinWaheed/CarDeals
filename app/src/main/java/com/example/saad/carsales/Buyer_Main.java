package com.example.saad.carsales;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Main extends AppCompatActivity {

    RecyclerView RV;
    Folder_Adapter adap;
    ArrayList<String> titles,year,carOwner;
    Firebase ref;
    String[] CAR_MODELS;
    ArrayAdapter MODELS;
    ListView models;
    String selection;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);

        Firebase.setAndroidContext(this);

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/");
        titles=new ArrayList<String>();
        year=new ArrayList<String>();
        carOwner=new ArrayList<String>();

        CAR_MODELS = new String[]{"Honda Civic", "Honda City", "Hilux", "Corolla", "Mehran", "Audi", "Alto", "Cultus", "Mercedes", "BMW", "Vitz", "Bolan", "Cuore", "Kyber",
                "Land Cruiser", "Range Rover", "Lamborghini", "Ferrari", "Prius", "Prado", "Hummer"};
        MODELS = new ArrayAdapter<String>(this, R.layout.text_view, CAR_MODELS);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_backward);

        RV = (RecyclerView) findViewById(R.id.RV);
        adap = new Folder_Adapter(this,getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RV.setAdapter(adap);
        RV.setLayoutManager(mLayoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(Buyer_Main.this, "Cars", Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(Buyer_Main.this);
                View dialog_layout = inflater.inflate(R.layout.list, null);
                AlertDialog.Builder db = new AlertDialog.Builder(Buyer_Main.this);

                models = (ListView) dialog_layout.findViewById(R.id.list);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                models.setAdapter(MODELS);

                models.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(Buyer_Main.this, CAR_MODELS[i], Toast.LENGTH_SHORT).show();
                        selection = CAR_MODELS[i];
                        ad.dismiss();
                        Filter_Cars(selection);
                    }
                });
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(Buyer_Main.this, "Years", Toast.LENGTH_SHORT).show();

                Filter_Year("5");
                /*LayoutInflater inflater = LayoutInflater.from(Buyer_Main.this);
                View dialog_layout = inflater.inflate(R.layout.list, null);
                AlertDialog.Builder db = new AlertDialog.Builder(Buyer_Main.this);

                models = (ListView) dialog_layout.findViewById(R.id.list);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                models.setAdapter(MODELS);

                models.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(Buyer_Main.this, CAR_MODELS[i], Toast.LENGTH_SHORT).show();
                        selection = CAR_MODELS[i];
                        ad.dismiss();
                        Filter_Cars(selection);
                    }
                });*/
            }
        });
    }
    List<Add> data;
    public List<Add> getData()
    {
         data= new ArrayList<>();

        ref.child("Adverts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add current= new Add();

                titles.add(dataSnapshot.child("Model").getValue().toString());
                year.add(dataSnapshot.child("Model Year").getValue().toString());
                carOwner.add(dataSnapshot.child("Name").getValue().toString());

                current.setTitle(dataSnapshot.child("Model").getValue().toString());
                current.setYear(dataSnapshot.child("Model Year").getValue().toString());
                current.setCar_owner(dataSnapshot.child("Name").getValue().toString());
                current.setAdd_id(dataSnapshot.getKey().toString());

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

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            //Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            RV.setClickable(false);
            isFabOpen = true;
            //Log.d("Raj","open");

        }
    }

    public void Filter_Cars(String value){
        data= new ArrayList<>();

        for (int i = 0; i<titles.size(); i++){
            if (titles.get(i).equals(value)){
                /*temp_title.add(titles.get(i));
                temp_year.add(year.get(i));
                temp_owner.add(carOwner.get(i));*/

                Add current= new Add();
                current.setTitle(titles.get(i));
                current.setYear(year.get(i));
                current.setCar_owner(carOwner.get(i));
                data.add(current);
            }
            adap = new Folder_Adapter(this,data);
            RV.setAdapter(adap);
        }
    }
    public void Filter_Year(String value) {
        data = new ArrayList<>();
        Toast.makeText(this, "Default: 5", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < year.size(); i++) {
            if (year.get(i).equals(value)) {
                Add current = new Add();
                current.setTitle(titles.get(i));
                current.setYear(year.get(i));
                current.setCar_owner(carOwner.get(i));
                data.add(current);
            }
            adap = new Folder_Adapter(this, data);
            RV.setAdapter(adap);
        }
    }
}
