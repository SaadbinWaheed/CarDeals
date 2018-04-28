package com.example.saad.carsales;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    TextView carname,location,regyear,color,mileage,contact,price;
    String strCarName,strYear,strContact,strRegYear,strColor,strMileage,strRegCity,strPrice,vid;

    ViewPager viewPager;
    Image_slide customSwipe;

    public int dotsCount=5;
    public ImageView[] dots;
    public LinearLayout pager_indicator;
    private int [] imageResources ={
            R.drawable.icon,
            R.drawable.icon3,
            R.drawable.icon,
            R.drawable.civic,
            R.drawable.transmission};

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



        passed_add_id=getIntent().getExtras().getString("Add ID");
        Key = getIntent().getStringExtra("Key");
        imgArr[0] = Key+"/1.jpg";
        imgArr[1] = Key+"/2.jpg";
        imgArr[2] = Key+"/3.jpg";

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
                price.setText(strPrice);
                regyear.setText(strYear);
                contact.setText(strContact);

                VideoView video=findViewById(R.id.video);
                video.setVideoURI(Uri.parse(vid));
                video.start();
                downImages(imgArr,0);
                downImages(imgArr,1);
                downImages(imgArr,2);

                //String strCarName,strYear,strAdd,strRegYear,strColor,strMileage;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        viewPager = findViewById(R.id.viewPager);

    }

    private void downloadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("<your_bucket>");
        StorageReference  islandRef = storageRef.child("file.txt");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "file_name");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"imageName.txt");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Log.e("firebase ",";local tem file created  created " +localFile.toString());
                Toast.makeText(Car_details.this, "Video Downloaded", Toast.LENGTH_LONG).show();
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               // Log.e("firebase ",";local tem file not created  created " +exception.toString());
                Toast.makeText(Car_details.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downImages(String[] path, final int pos){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(path[pos]);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imgArr[pos] = uri.toString();
                Toast.makeText(Car_details.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
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
