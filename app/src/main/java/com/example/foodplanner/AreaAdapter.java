package com.example.foodplanner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.*;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>{
    private static final String TAG = "AreaAdapter";
    Context context;
    List<Area> meals;

    public AreaAdapter(Context context, List<Area> meals) {
        this.context = context;
        this.meals = meals;
    }
    public void setArea(List<Area> areas) {
        this.meals = areas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_area, parent, false);
        AreaAdapter.ViewHolder viewHolder = new AreaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Area current = meals.get(position);
        holder.areaName.setText(current.getStrArea());
        holder.cardArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category clicked
                // Create an Intent to start the activity to show meals in this category
                Intent intent = new Intent(context, MealsByAreaActivity.class);
                intent.putExtra("areaName", current.getStrArea());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return meals.size();
        //return 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView areaName;
        public CardView cardArea;
        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            areaName = v.findViewById(R.id.tv_AreaName);
            cardArea = v.findViewById(R.id.card_Area);
        }
    }
}
