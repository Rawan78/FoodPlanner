package com.example.foodplanner.MealPlan.view;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Meals.view.MealDetailsActivity;
import com.example.foodplanner.model.*;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class DayMealPlanAdapter extends RecyclerView.Adapter<DayMealPlanAdapter.ViewHolder>{
    private static final String TAG = "DayMealPlanAdapter";
    Context context;
    List<MealPlanObject> mealPlanObjects;
    private OnMealPlanClickListener mealPlanClickListener;
    private List<MealPlanObject> saturdayMeals;
    private List<MealPlanObject> sundayMeals;
    private List<MealPlanObject> tuesdayMeals;
    private List<MealPlanObject> wednesdayMeals;
    private List<MealPlanObject> mondayMeals;
    private List<MealPlanObject> thursdayMeals;
    private List<MealPlanObject> fridayMeals;

    public DayMealPlanAdapter(Context context, List<MealPlanObject> mealPlanObjects, OnMealPlanClickListener mealPlanClickListener) {
        this.context = context;
        this.mealPlanObjects = mealPlanObjects;
        this.mealPlanClickListener = mealPlanClickListener;
        mealPlanObjects = new ArrayList<MealPlanObject>();

        saturdayMeals = new ArrayList<>();
        sundayMeals = new ArrayList<>();
        tuesdayMeals = new ArrayList<>();
        wednesdayMeals = new ArrayList<>();
        mondayMeals = new ArrayList<>();
        thursdayMeals = new ArrayList<>();
        fridayMeals = new ArrayList<>();
    }

    @NonNull
    @Override
    public DayMealPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_meal, parent, false);
        DayMealPlanAdapter.ViewHolder viewHolder = new DayMealPlanAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DayMealPlanAdapter.ViewHolder holder, int position) {
        MealPlanObject currentMeal = mealPlanObjects.get(position);
        holder.mealName.setText(currentMeal.getStrMeal());
        Glide.with(context).load(currentMeal.getStrMealThumb()).into(holder.mealPhoto);

        holder.cardMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mealPlanClickListener != null) {
                    mealPlanClickListener.onMealPlanClick(currentMeal);
                }
            }
        });
        holder.mealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealPlanClickListener.onMealPlanClick(currentMeal);
                Toast.makeText(context, "Meal deleted From Plan", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void setMealsPlan(List<MealPlanObject> mealPlanObjects) {
        this.mealPlanObjects = mealPlanObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mealPlanObjects.size();

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
