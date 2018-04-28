package com.example.saad.carsales;

/**
 * Created by Musab on 5/3/2017.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Image_slide extends PagerAdapter {
    String imageResources[];
    private Context ctx;
    private LayoutInflater layoutInflater;

    public Image_slide(Context ctx,String[] imageResources) {
        this.ctx=ctx;
        this.imageResources=imageResources;
    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

   // static ImageView imageView;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      //  Toast.makeText(ctx, String.valueOf(imageResources[position]), Toast.LENGTH_SHORT).show();
        layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.activity_image_slide,container,false);
        final ImageView imageView=(ImageView) itemView.findViewById(R.id.img_pager_item);
      //  imageView.setImageResource(imageResources[position]);
        Glide.with(ctx).load(imageResources[position]).into(imageView);

        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return  (view==object);
    }
}
