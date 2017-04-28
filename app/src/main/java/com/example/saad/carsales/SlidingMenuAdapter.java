package com.example.saad.carsales;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fahaid on 4/28/2017.
 */

public class SlidingMenuAdapter extends BaseAdapter {
    private Context context;
    private List<SlideMenu> listitem;

    public SlidingMenuAdapter(Context context, List<SlideMenu> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.items_sliding_menu,null);
        ImageView im = (ImageView) v.findViewById(R.id.imageView);
        TextView txt = (TextView) v.findViewById(R.id.text);

        SlideMenu item = listitem.get(position);
        im.setImageResource(item.getImgid());
        txt.setText(item.getTitle());
        return v;
    }
}