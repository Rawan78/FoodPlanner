package com.example.foodplanner.Meals.view;

import com.example.foodplanner.start.*;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{

    private static final String TAG = "CategoriesAdapter";
    Context context;
    List<Meal> meals;
    OnFavouriteClickListener listener ;
     boolean isAGuest=false;

    public CategoriesAdapter(Context context, List<Meal> meals, OnFavouriteClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
        meals = new ArrayList<Meal>();
    }

        public void setMeal(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
        }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_meal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Meal current = meals.get(position);
        holder.mealName.setText(current.getStrMeal());

        Glide.with(context).load(meals.get(position).getStrMealThumb()).into(holder.mealPhoto);
        holder.addToFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle between two icons
                if(StartScreenFragment.isGuest == true){
                    Toast.makeText(context, "Sorry, You Cannot add to favourite \n If you want, You must Login First", Toast.LENGTH_SHORT).show();
                }else {
                    listener.onFavMealClick(current);
                    holder.addToFavIcon.setImageResource(R.drawable.redhearticon);
                    holder.addToFavIcon.setTag("not_favorite");
                    Toast.makeText(context, "Added To Favourite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.cardMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealDetailsActivity.class);
                intent.putExtra("mealDetailsObject", current);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return meals.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealPhoto;
        TextView mealName;
        ImageView addToFavIcon;
        public CardView cardMeal;
        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            mealPhoto = v.findViewById(R.id.iv_mealPhoto);
            mealName = v.findViewById(R.id.tv_mealName);
            cardMeal = v.findViewById(R.id.card_meal);
            addToFavIcon = v.findViewById(R.id.iv_addToFavourite);

        }
    }
}
