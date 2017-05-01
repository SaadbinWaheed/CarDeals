package com.example.saad.carsales;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView img_logo=(ImageView) findViewById(R.id.imageView);
        final TextView title=(TextView) findViewById(R.id.textView);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                //Logo Visibility toggle
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(500);

                img_logo.setAnimation(fadeIn);
                title.setAnimation(fadeIn);

                img_logo.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }
                }, 1000);

            }
        }, 500);
    }
}
