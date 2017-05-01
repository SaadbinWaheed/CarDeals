package com.example.saad.carsales;

/**
 * Created by Saad on 01/05/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class Folder_Adapter extends RecyclerView.Adapter<Folder_Adapter.MyViewHolder> {


    LayoutInflater inflater;
    private List<Add> Add;
    Context context;
    public Folder_Adapter (Context context , List<Add> Add)
    {

         inflater = LayoutInflater.from(context);
        this.Add=Add;
        this.context=context;

    }
    @Override
    public Folder_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_folding_cards, parent,false);

        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Folder_Adapter.MyViewHolder holder, final int position) {

        Add current = Add.get(position);
        holder.title.setText(current.getTitle());
        holder.car_owner.setText("Owner Name: " + current.getCar_owner());
        holder.year.setText("Model year: " + current.getYear());


        holder.year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.fc.toggle(true);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.fc.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Add.size();
    }

        public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, car_owner;
            FoldingCell fc;
            //        // attach click listener to folding cell
//        fc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fc.toggle(false);
//            }
//        });
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            car_owner = (TextView) view.findViewById(R.id.car_owner);
            year = (TextView) view.findViewById(R.id.year);
            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
        }
    }

}
//    private List<Movie> moviesList;
//

//
//
//    public Folder_Adapter(List<Movie> moviesList) {
//        this.moviesList = moviesList;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_folding_cards, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Movie movie = FoldingList.get(position);
//        holder.title.setText(movie.getTitle());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
//    }
//
//    @Override
//    public int getItemCount() {
//        return moviesList.size();
//    }
//}