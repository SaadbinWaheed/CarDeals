package com.example.saad.carsales;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.dx.dxloadingbutton.lib.LoadingButton;
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

        Button b = (Button) findViewById(R.id.trying);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this,FoldingList.class);
                startActivity(n);
            }
        });

        final LoadingButton buy_lb = (LoadingButton)findViewById(R.id.buy_load);
        buy_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buy_lb.startLoading(); //start loading

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        buy_lb.loadingSuccessful();

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                                Intent mainIntent = new Intent(MainActivity.this,Buyer_Main.class);
                                startActivity(mainIntent);
                                buy_lb.reset();
                            }
                        }, 1000);


                    }
                }, 1000);
            }
        });

        final LoadingButton sell_lb = (LoadingButton)findViewById(R.id.sell_load);
        sell_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sell_lb.startLoading(); //start loading

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        sell_lb.loadingSuccessful();

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                                Intent mainIntent = new Intent(MainActivity.this,AD_details.class);
                                startActivity(mainIntent);
                                sell_lb.reset();
                            }
                        }, 1000);


                    }
                }, 1000);
            }
        });



        Buy = (CardView) findViewById(R.id.card_buy);
        Sell = (CardView) findViewById(R.id.car_sell);

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
