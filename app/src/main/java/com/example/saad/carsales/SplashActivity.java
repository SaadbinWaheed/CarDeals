package com.example.saad.carsales;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gospelware.liquidbutton.LiquidButton;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView img_logo=(ImageView) findViewById(R.id.imageView);
        LiquidButton liquidButton = (LiquidButton) findViewById(R.id.button);

        final TextView progress_txt= (TextView) findViewById(R.id.progress_txt);
        liquidButton.startPour();
        liquidButton.setAutoPlay(true);

        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

            @Override
            public void onProgressUpdate(float progress)
            {
                progress_txt.setText(String.format("%.2f", progress * 100) + "%");
            }
        });
        //liquidButton.startPour();
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//
//                //Logo Visibility toggle
//                Animation fadeIn = new AlphaAnimation(0, 1);
//                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
//                fadeIn.setDuration(500);
//
//
//                new Handler().postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//
//                    }
//                }, 1000);
//
//            }
//        }, 500);
    }
}
