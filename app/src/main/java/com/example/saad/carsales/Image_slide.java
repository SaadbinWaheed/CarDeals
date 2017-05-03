package com.example.saad.carsales;

/**
 * Created by Musab on 5/3/2017.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Image_slide extends PagerAdapter {
    int imageResources[];
    private Context ctx;
    private LayoutInflater layoutInflater;

    public Image_slide(Context c,int[] imageResources) {
        ctx=c;
        this.imageResources=imageResources;
    }

    @Override
    public int getCount() {

        return imageResources.length;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.activity_image_slide,container,false);
        ImageView imageView=(ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageResource(imageResources[position]);
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
