package com.example.saad.carsales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.veer.shapeletter.ShapeLetter;

public class MainActivity extends AppCompatActivity {
    CardView Buy,Sell;
    Button foldingCards_btn;
    public int x;
    String Name;
    ShapeLetter sl;
    TextView name_txt;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        Bundle b= getIntent().getExtras();
        Name=b.getString("Name");
        Toast.makeText(this,Name,Toast.LENGTH_SHORT).show();

        sl=(ShapeLetter) findViewById(R.id.letter);
        name_txt=(TextView) findViewById(R.id.name_txt);
        name_txt.setText(Name);

        sl.setTitleText(Name.substring(0,1).toUpperCase());

        sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                dlgAlert.setTitle("Choose an option");
                dlgAlert.setMessage("");

                dlgAlert.setNegativeButton("Sign Out",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                                dlgAlert.setTitle("Log Out");
                                dlgAlert.setMessage("Are you sure you want to Log out?");

                                dlgAlert.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mAuth.signOut();

                                            }
                                        });
                                dlgAlert.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                dlgAlert.show();
                            }
                        });
                dlgAlert.setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                dlgAlert.show();
            }

        });

        Firebase.setAndroidContext(this);

        Firebase ref= new Firebase("https://car-sales-f4f9c.firebaseio.com/");

//        Button b = (Button) findViewById(R.id.trying);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent n = new Intent(MainActivity.this,FoldingList.class);
//                startActivity(n);
//            }
//        });

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
