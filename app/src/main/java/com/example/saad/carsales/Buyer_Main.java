package com.example.saad.carsales;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dx.dxloadingbutton.lib.LoadingButton;

import java.util.ArrayList;
import java.util.List;

public class Buyer_Main extends AppCompatActivity {
    private List<SlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    RecyclerView RV;
    ArrayList<String> ads;
    Buyer_Main_Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);
        RV = (RecyclerView) findViewById(R.id.RV);


        ads = new ArrayList<String>();
        for (int i = 0; i<=15;i++){
            ads.add("Ad # "+String.valueOf(i+1));
        }

        adap = new Buyer_Main_Adapter(Buyer_Main.this,ads);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        RV.setLayoutManager(mLayoutManager);
        RV.setItemAnimator(new DefaultItemAnimator());
        RV.setAdapter(adap);

        Slider();
    }

    public void Slider() {
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //Add item for sliding list
        listSliding.add(new SlideMenu(R.drawable.mileage, "Option 1"));
        listSliding.add(new SlideMenu(R.drawable.locks, "Option 2"));
        listSliding.add(new SlideMenu(R.drawable.ac, "Option 3"));

        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set title
        // setTitle(listSliding.get(0).getTitle());
        //item selected
        listViewSliding.setItemChecked(0, true);
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Display fragment 1 when start
        //  replaceFragment(0);
        //Hanlde on item click

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                //   setTitle("Home");
                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace fragment
                //     replaceFragment(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
                AlertDialog.Builder builder = new AlertDialog.Builder(Buyer_Main.this);
                /*if (position == 1) {
                    Intent registerIntent = new Intent(AdminActivity.this, ProjectsTabs.class);
                    registerIntent.addFlags(registerIntent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    AdminActivity.this.startActivity(registerIntent);

                }
                if (position == 2) {
                    Intent registerIntent = new Intent(AdminActivity.this, Clients.class);
                    registerIntent.addFlags(registerIntent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    AdminActivity.this.startActivity(registerIntent);

                }
                if (position == 3) {
                    Intent registerIntent = new Intent(AdminActivity.this, Staff.class);
                    registerIntent.addFlags(registerIntent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    AdminActivity.this.startActivity(registerIntent);

                }
                if (position == 4) {
                    Intent registerIntent = new Intent(AdminActivity.this, Interns.class);
                    registerIntent.addFlags(registerIntent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    AdminActivity.this.startActivity(registerIntent);

                }
                if (position == 6) {

                    builder.setMessage("Are you Sure to Sign Out?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent registerIntent = new Intent(AdminActivity.this, Login.class);
                                    registerIntent.addFlags(registerIntent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    AdminActivity.this.startActivity(registerIntent);
                                    Toast.makeText(AdminActivity.this, "Successful Sign Out", Toast.LENGTH_SHORT).show();

                                }

                            })
                            .create()
                            .show();

                }*/

            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

}
