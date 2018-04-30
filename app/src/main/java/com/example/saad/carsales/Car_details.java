package com.example.saad.carsales;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Car_details extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ArrayList<Integer> image=new ArrayList<Integer>();
    TextView carname,location,regyear,color,mileage,contact,price,video_down;
    String strCarName,strYear,strContact,strColor,strMileage,strRegCity,strPrice,vid;

    ViewPager viewPager;
    Image_slide customSwipe;

    public int dotsCount=5;
    public ImageView[] dots;
    public LinearLayout pager_indicator;

    Firebase ref;
    String passed_add_id,Key;
    String[] imgArr = new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        Firebase.setAndroidContext(this);

        carname=(TextView) findViewById(R.id.car_name);
        color=(TextView) findViewById(R.id.exterior_colorshow);
        location=(TextView) findViewById(R.id.location);
        mileage=(TextView) findViewById(R.id.mileage_s);
        regyear=(TextView) findViewById(R.id.reg_year_s);
        price=(TextView) findViewById(R.id.car_price);
        contact=(TextView) findViewById(R.id.txt_Contact);
        video_down = findViewById(R.id.down_vid);
        viewPager = findViewById(R.id.viewPager);

        passed_add_id=getIntent().getExtras().getString("Add ID");
        Key = getIntent().getStringExtra("Key");
        imgArr[0] = "Adverts/"+Key+"/1.jpg";
        imgArr[1] = "Adverts/"+Key+"/2.jpg";
        imgArr[2] = "Adverts/"+Key+"/3.jpg";

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/");
        ref=ref.child("Adverts").child(Key);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                strColor=dataSnapshot.child("Body Color").getValue().toString();
                strMileage=dataSnapshot.child("Mileage").getValue().toString();
                strYear=dataSnapshot.child("Model Year").getValue().toString();
                strCarName=dataSnapshot.child("Model").getValue().toString();
                strRegCity=dataSnapshot.child("Registration City").getValue().toString();
                strPrice=dataSnapshot.child("Price").getValue().toString();
                strContact=dataSnapshot.child("Contact").getValue().toString();
                vid = dataSnapshot.child("Video").getValue().toString();

                carname.setText(strCarName);
                color.setText(strColor);
                location.setText(strRegCity);
                mileage.setText(strMileage);
                price.setText("PKR "+strPrice);
                regyear.setText(strYear);
                contact.setText(strContact);

                downImages(imgArr,0);
                downImages(imgArr,1);
                downImages(imgArr,2);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        video_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    downloadFile();
                } catch (IOException e) {
                    Toast.makeText(Car_details.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    ProgressDialog dia;

    private void downloadFile() throws IOException {
        dia = new ProgressDialog(Car_details.this);
        dia.setMessage("Downloading Video!!");
        dia.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://car-sales-f4f9c.appspot.com");
        StorageReference  islandRef = storageRef.child("Adverts/"+Key+"/video.mp4");

        //Toast.makeText(this, islandRef.getPath(), Toast.LENGTH_SHORT).show();
        File rootPath = new File(Environment.getExternalStorageDirectory(), Key);
        //Toast.makeText(this, rootPath.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

       // final File localFile = new File(rootPath,"video.mp4");

        final File localFile = File.createTempFile(Key+"Vid", "mp4");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Log.e("firebase ",";local tem file created  created " +localFile.toString());
                Toast.makeText(Car_details.this, localFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                dia.dismiss();
                Video_View(localFile.getAbsolutePath());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               // Log.e("firebase ",";local tem file not created  created " +exception.toString());
                dia.dismiss();
                Toast.makeText(Car_details.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Video_View(String path){
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.vid_view, null);
        final AlertDialog dia = new AlertDialog.Builder(this).create();
        dia.setView(deleteDialogView);
        final VideoView myVideoView = dia.findViewById(R.id.video_view);
        myVideoView.setVideoPath(path);
        myVideoView.setMediaController(new MediaController(this));
        myVideoView.requestFocus();
        myVideoView.start();

        dia.show();
    }

    public void downImages(String[] path, final int pos){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(path[pos]);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imgArr[pos] = uri.toString();
                if (pos == 2){
                    customSwipe = new Image_slide(Car_details.this,imgArr);
                    viewPager.setAdapter(customSwipe);
                    setUiPageViewController();
                }
            }
        });
    }

    public void setUiPageViewController() {

        dotsCount = customSwipe.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

//            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }
}
