package com.example.saad.carsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    CardView Buy,Sell;
    Button foldingCards_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        Firebase ref= new Firebase("https://car-sales-f4f9c.firebaseio.com/");


        Buy = (CardView) findViewById(R.id.card_buy);
        Sell = (CardView) findViewById(R.id.car_sell);
        foldingCards_btn= (Button) findViewById(R.id.foldingCards_btn);

        foldingCards_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,FoldingList.class);
                startActivity(i);
            }
        });

        Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Sign_Signup.class);
                startActivity(i);
            }
        });

        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Buyer_Main.class);
                startActivity(i);
            }
        });
    }
}
