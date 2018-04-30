package com.example.saad.carsales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.saad.carsales.Adapters.Ads_Approve;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AD_details extends AppCompatActivity {
    private Button b_getlocation;
    private TrackGPS gps = null;
    double longitude;
    double latitude;
    int img_nmbr;
    ImageView img1, img2, img3;
    TextView vid;
    TextView Model;
    Button done;
    ListView models;
    ArrayAdapter MODELS;
    String[] CAR_MODELS;
    EditText modelYear, RegCity, Mileage,Engine_Capacity,Price, color, S_Name, S_Contact, S_Address;
    String key,Name,contactInfo,car_model;
    Firebase ref;
    FirebaseStorage storage;
    StorageReference videoRef;
    String VideoDownloadLink;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 2;
    private boolean images_picked = false;
    ProgressDialog dia ;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        storage = FirebaseStorage.getInstance();
        videoRef = storage.getReferenceFromUrl("gs://car-sales-f4f9c.appspot.com/");

        Bundle b= getIntent().getExtras();
        Name=b.getString("Name");
        contactInfo=b.getString("Contact Info");

        dia = new ProgressDialog(AD_details.this);

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/");
        key=ref.child("Adverts").push().getKey();



        modelYear = (EditText) findViewById(R.id.model_year);
        RegCity = (EditText) findViewById(R.id.Reg_city);
        Mileage = (EditText) findViewById(R.id.mileage);
        color = (EditText) findViewById(R.id.body_color);
        Price = (EditText) findViewById(R.id.Price);
        Engine_Capacity = (EditText) findViewById(R.id.Eng_cap);
        S_Name = (EditText) findViewById(R.id.name);
        S_Address = (EditText) findViewById(R.id.seller_address);
        S_Contact = (EditText) findViewById(R.id.contact);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        vid =  findViewById(R.id.vid);

        Model = (TextView) findViewById(R.id.car_model);
        done = (Button) findViewById(R.id.details_done);
        b_getlocation = (Button) findViewById(R.id.location);

        S_Contact.setText(contactInfo);
        S_Name.setText(Name);

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

        vid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);

                return false;
            }
        });

        Model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(AD_details.this);
                View dialog_layout = inflater.inflate(R.layout.list, null);
                AlertDialog.Builder db = new AlertDialog.Builder(AD_details.this);

                models = dialog_layout.findViewById(R.id.list);
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
                if(images_picked)
                    uploadVideo(video);
            }
        });

        b_getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gps==null)
                    gps = new TrackGPS(AD_details.this);
                gps.getLocation();

                if(gps.canGetLocation()){

                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();
                    if(longitude==0.0 && latitude==0.0)
                        Toast.makeText(AD_details.this, "Please try again", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AD_details.this, "Location added!", Toast.LENGTH_SHORT).show();
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
        //gps.stopUsingGPS();
    }

    void Image_Pick_Intent(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    Uri video;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        if (requestCode == 0) {
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
                        images_picked = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedVideoUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedVideoUri.getPath()+".mp4";

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedVideoUri);

                //Toast.makeText(this,filemanagerstring,Toast.LENGTH_LONG).show();
                vid.setText(filemanagerstring);

                if (selectedVideoUri != null) {

                   ImageView Thumb = findViewById(R.id.thumb);
                    Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(filemanagerstring, MediaStore.Video.Thumbnails.MICRO_KIND);

                    Thumb.setImageBitmap(bitmap2);

                    String filename = filemanagerstring.substring(filemanagerstring.lastIndexOf("/")+1);
                    // Create a storage reference from our app

                 //   Toast.makeText(AD_details.this, filename, Toast.LENGTH_SHORT).show();
                    videoRef = videoRef.child("Adverts/"+key+"/video.mp4");

                    video = selectedVideoUri;
                    //uploadVideo(selectedImageUri);

//                    Intent intent = new Intent(AD_details.this,
//                            VideoplayAvtivity.class);
//                    intent.putExtra("path", selectedImagePath);
//                    startActivity(intent);
                }
            }
        }


    }

    private void uploadFile(Bitmap bitmap, final String im) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://car-sales-f4f9c.appspot.com");
        StorageReference mountainImagesRef = storageRef.child("Adverts/"+key+"/"+ im + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
             //   Toast.makeText(AD_details.this, downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                if (im=="2") {
                    Toast.makeText(AD_details.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AD_details.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("Name", Name);
                    b.putString("Contact Info", contactInfo);
                    intent.putExtras(b);
                    dia.hide();
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void UploadData(){
        String model=Model.getText().toString();
        String year=modelYear.getText().toString();
        String regCity=RegCity.getText().toString();
        String mileage=Mileage.getText().toString();
        String eng_cap = Engine_Capacity.getText().toString();
        String bodyColor=color.getText().toString();
        String price=Price.getText().toString();
        String name=S_Name.getText().toString();
        String contact=S_Contact.getText().toString();
        String address=S_Address.getText().toString();

        if(model.equals("") || year.equals("") || regCity.equals("") || eng_cap.equals("") || bodyColor.equals("") || price.equals("") || name.equals("") || contact.equals("") || address.equals("") ) {
            Toast.makeText(AD_details.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else {
            dia = new ProgressDialog(AD_details.this);
            dia.setMessage("Uploading Images");
            dia.show();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("Model", model);
            hashMap.put("Model Year", year);
            hashMap.put("Registration City", regCity);
            hashMap.put("Mileage", mileage);
            hashMap.put("Engine Capacity", eng_cap);
            hashMap.put("Body Color", bodyColor);
            hashMap.put("Price", price);
            hashMap.put("Name", name);
            hashMap.put("Contact", contact);
            hashMap.put("Address", address);
            hashMap.put("Long", String.valueOf(longitude));
            hashMap.put("Lat", String.valueOf(latitude));
            hashMap.put("Pic1", key + "/1.jpg");
            hashMap.put("Pic2", key + "/2.jpg");
            hashMap.put("Pic3", key + "/3.jpg");
            hashMap.put("Video", VideoDownloadLink);

            uploadFile(((BitmapDrawable) img1.getDrawable()).getBitmap(), "1");
            uploadFile(((BitmapDrawable) img2.getDrawable()).getBitmap(), "2");
            uploadFile(((BitmapDrawable) img3.getDrawable()).getBitmap(), "3");

            ref.child("Adverts").child(key).setValue(hashMap);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void uploadVideo(Uri videoUri) {
        if(videoUri != null){
            UploadTask uploadTask = videoRef.putFile(videoUri);
            VideoDownloadLink = String.valueOf(videoRef.getDownloadUrl());
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                        Toast.makeText(AD_details.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                    UploadData();
                }

            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                   // Toast.makeText(AD_details.this,"Uploading Video", Toast.LENGTH_SHORT).show();
                    dia.setMessage("Uploading Video!!");
                    dia.show();
                }
            });
        }else {
            Toast.makeText(AD_details.this, "Nothing to upload", Toast.LENGTH_SHORT).show();
        }

    }

}
