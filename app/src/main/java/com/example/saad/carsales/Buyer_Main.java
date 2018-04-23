package com.example.saad.carsales;

import android.location.Location;
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
import android.widget.Button;
import android.widget.EditText;
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
    ArrayList<String> titles,year,carOwner,price;
    Firebase ref;
    String[] CAR_MODELS;
    ArrayAdapter MODELS;
    ListView models;
    String selection;
    Location myloc;
    List<Add> data,nearest;
    ArrayList<Location> coords;

    private TrackGPS gps = null;
    private Boolean isFabOpen = false;
    private FloatingActionButton main_search,search_model,search_year,search_price;
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

        main_search = findViewById(R.id.fab);
        search_model = findViewById(R.id.fab1);
        search_year = findViewById(R.id.fab2);
        search_price = findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rot_backward);

        RV = findViewById(R.id.RV);
        adap = new Folder_Adapter(this,getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RV.setAdapter(adap);
        RV.setLayoutManager(mLayoutManager);

        main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();

            }
        });

        search_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(Buyer_Main.this, "Car Model", Toast.LENGTH_LONG).show();

                LayoutInflater inflater = LayoutInflater.from(Buyer_Main.this);
                View dialog_layout = inflater.inflate(R.layout.list, null);
                AlertDialog.Builder db = new AlertDialog.Builder(Buyer_Main.this);

                models = dialog_layout.findViewById(R.id.list);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                ad.setTitle("Car Model");
                models.setAdapter(MODELS);

                models.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(Buyer_Main.this, CAR_MODELS[i], Toast.LENGTH_SHORT).show();
                        selection = CAR_MODELS[i];
                        ad.dismiss();
                        Filter_Cars(selection);
                    }
                });
            }
        });
        search_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(Buyer_Main.this, "Manufacture Year", Toast.LENGTH_LONG).show();


                LayoutInflater inflater = LayoutInflater.from(Buyer_Main.this);
                View dialog_layout = inflater.inflate(R.layout.search, null);
                AlertDialog.Builder db = new AlertDialog.Builder(Buyer_Main.this);

                final EditText data = dialog_layout.findViewById(R.id.Search);
                Button go = dialog_layout.findViewById(R.id.go);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                ad.setTitle("Manufacture Year");

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                        if(data.getText().toString().length() > 4 || data.getText().toString().length()<4)
                            Toast.makeText(Buyer_Main.this, "Invalid Date!!!", Toast.LENGTH_SHORT).show();
                        else
                            Filter_Year(data.getText().toString());
                    }
                });
            }
        });
        search_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
                Toast.makeText(Buyer_Main.this, "Price", Toast.LENGTH_LONG).show();

                LayoutInflater inflater = LayoutInflater.from(Buyer_Main.this);
                View dialog_layout = inflater.inflate(R.layout.search, null);
                AlertDialog.Builder db = new AlertDialog.Builder(Buyer_Main.this);

                final EditText data = dialog_layout.findViewById(R.id.Search);
                Button go = dialog_layout.findViewById(R.id.go);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                ad.setTitle("Manufacture Year");

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                        Filter_Price(data.getText().toString());
                    }
                });
            }
        });

        if (gps == null)
            gps = new TrackGPS(Buyer_Main.this);
        gps.getLocation();
       // Toast.makeText(Buyer_Main.this,"Longitude:"+String.valueOf(gps.longitude)+"\nLatitude:"+String.valueOf(gps.latitude),Toast.LENGTH_SHORT).show();
        myloc = new Location("Start");
        myloc.setLongitude(gps.getLongitude());
        myloc.setLatitude(gps.getLatitude());
    }

    public List<Add> getData() {
         data= new ArrayList<>();
         nearest = new ArrayList<>();
         coords = new ArrayList<>();
         price = new ArrayList<>();
        ref.child("Adverts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add current= new Add();
                titles.add(dataSnapshot.child("Model").getValue().toString());
                year.add(dataSnapshot.child("Model Year").getValue().toString());
                carOwner.add(dataSnapshot.child("Name").getValue().toString());
                price.add(dataSnapshot.child("Price").getValue().toString());
                Location temp = new Location("Start");
                temp.setLongitude(Float.valueOf(dataSnapshot.child("Long").getValue().toString()));
                temp.setLatitude(Float.valueOf(dataSnapshot.child("Lat").getValue().toString()));
                coords.add(temp);
                float dist = myloc.distanceTo(temp) / 1000;

                current.setTitle(dataSnapshot.child("Model").getValue().toString());
                current.setYear(dataSnapshot.child("Model Year").getValue().toString());
                current.setCar_owner(dataSnapshot.child("Name").getValue().toString());
                current.setAdd_id(dataSnapshot.getKey().toString());
                current.setLat(Float.valueOf(dataSnapshot.child("Lat").getValue().toString()));
                current.setLong(Float.valueOf(dataSnapshot.child("Long").getValue().toString()));

                data.add(current);
                if (dist <= 10 && dist > 0.5) {
                    nearest.add(current);
                    Toast.makeText(Buyer_Main.this, String.valueOf(dist), Toast.LENGTH_SHORT).show();
                }
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
        return nearest;
    }

    public void animateFAB(){

        if(isFabOpen){

            main_search.startAnimation(rotate_backward);
            search_model.startAnimation(fab_close);
            search_year.startAnimation(fab_close);
            search_price.startAnimation(fab_close);
            search_model.setClickable(false);
            search_year.setClickable(false);
            search_price.setClickable(false);
            isFabOpen = false;


        } else {

            main_search.startAnimation(rotate_forward);
            search_model.startAnimation(fab_open);
            search_year.startAnimation(fab_open);
            search_price.startAnimation(fab_open);
            search_model.setClickable(true);
            search_year.setClickable(true);
            search_price.setClickable(true);
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
       // Toast.makeText(this, "Default: 5", Toast.LENGTH_SHORT).show();
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

    public void Filter_Price(String value) {
        data = new ArrayList<>();
        // Toast.makeText(this, "Default: 5", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < price.size(); i++) {
            if (price.get(i).equals(value)) {
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


