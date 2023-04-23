package com.example.cafemanager.ui.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafemanager.R;
import com.example.cafemanager.ui.HomeActivity;

import java.util.ArrayList;

public class Adapterban extends RecyclerView.Adapter<Adapterban.ViewHolder>{
    ArrayList<String> planetList;
    private AdapterCong.Listener listener;

    public Adapterban(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }

    @NonNull
    @Override
    public Adapterban.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull Adapterban.ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.ic_baseline_table_restaurant_24);
        Log.e("ư",position+" test: "+planetList.get(position)+" home"+HomeActivity.sobanquaMonfragment);
       if (HomeActivity.sobanquaMonfragment == position+1){
           holder.text.setTextColor(Color.RED);
       }else {
           holder.text.setTextColor(Color.BLACK);
       }
        holder.text.setText(planetList.get(position));


    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image=  itemView.findViewById(R.id.image_id);
            text=  itemView.findViewById(R.id.text_id);

        }
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }
}
