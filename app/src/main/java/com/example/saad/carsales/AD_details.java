package com.example.saad.carsales;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class AD_details extends AppCompatActivity {
    private Button b_getlocation;
    private TrackGPS gps;
    double longitude;
    double latitude;
    int img_nmbr;
    String car_model;
    ImageView img1, img2, img3;
    TextView Model;
    Button done;
    ListView models;
    ArrayAdapter MODELS;
    String[] CAR_MODELS;
    EditText modelYear, RegCity, Mileage,Price, color, S_Name, S_Contact, S_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        modelYear = (EditText) findViewById(R.id.model_year);
        RegCity = (EditText) findViewById(R.id.Reg_city);
        Mileage = (EditText) findViewById(R.id.mileage);
        color = (EditText) findViewById(R.id.body_color);
        Price = (EditText) findViewById(R.id.Price);
        S_Name = (EditText) findViewById(R.id.name);
        S_Address = (EditText) findViewById(R.id.seller_address);
        S_Contact = (EditText) findViewById(R.id.contact);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        Model = (TextView) findViewById(R.id.car_model);
        done = (Button) findViewById(R.id.details_done);

        CAR_MODELS = new String[]{"Honda Civic", "Honda City", "Hilux", "Corolla", "Mehran", "Audi", "Alto", "Cultus", "Mercedes", "BMW", "Vitz", "Bolan", "Cuore", "Kyber",
                "Land Cruiser", "Range Rover", "Lamborghini", "Ferrari", "Prius", "Prado", "Hummer"};


        MODELS = new ArrayAdapter<String>(this, R.layout.text_view, CAR_MODELS);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_nmbr = 1;
                Image_Pick_Intent();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_nmbr = 2;
                Image_Pick_Intent();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_nmbr = 3;
                Image_Pick_Intent();
            }
        });

        Model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(AD_details.this);
                View dialog_layout = inflater.inflate(R.layout.list, null);
                AlertDialog.Builder db = new AlertDialog.Builder(AD_details.this);

                models = (ListView) dialog_layout.findViewById(R.id.list);
                db.setView(dialog_layout);
                final AlertDialog ad = db.show();
                models.setAdapter(MODELS);
                models.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        car_model = CAR_MODELS[i];
                        Model.setText(car_model);
                        ad.dismiss();
                    }
                });


            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passdetails = new Intent(AD_details.this, Car_details.class);
                String address = S_Address.getText().toString();
                String mileage = Mileage.getText().toString();
                String contact = S_Contact.getText().toString();
                String year = modelYear.getText().toString();
                String regcity = RegCity.getText().toString();
                String clr = color.getText().toString();
                String prce = Price.getText().toString();
                SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("Model", car_model);
                edit.putString("Address", address);
                edit.putString("Color", clr);
                edit.putString("Mileage", mileage);
                edit.putString("Contact", contact);
                edit.putString("Registration_Year", year);
                edit.putString("Registration_City", regcity);
                edit.putString("Price", prce);
                edit.commit();


            }
        });
        b_getlocation = (Button) findViewById(R.id.location);

        b_getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new TrackGPS(AD_details.this);


                if(gps.canGetLocation()){


                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();

                    Toast.makeText(AD_details.this,"Longitude:"+String.valueOf(longitude)+"\nLatitude:"+String.valueOf(latitude),Toast.LENGTH_SHORT).show();
                }
                else
                {

                    gps.showSettingsAlert();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        gps.stopUsingGPS();
    }


    void Image_Pick_Intent(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    if(img_nmbr == 1){
                        img1.setImageBitmap(bitmap);
                    }else if (img_nmbr == 2){
                        img2.setImageBitmap(bitmap);
                    }else if (img_nmbr == 3){
                        img3.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}
