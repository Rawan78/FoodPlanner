package com.example.foodplanner;

import android.content.Context;
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
import com.example.foodplanner.model.Meal;

import java.util.List;

public class MealsByAreaAdapter extends RecyclerView.Adapter<MealsByAreaAdapter.ViewHolder>{
    private static final String TAG = "MealsByAreaAdapter";
    Context context;
    List<Meal> meals;
    OnFavouriteClickListener listener ;

    public MealsByAreaAdapter(Context context, List<Meal> meals, OnFavouriteClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }
    public void setMeal(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealsByAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_meal, parent, false);
        MealsByAreaAdapter.ViewHolder viewHolder = new MealsByAreaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsByAreaAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Meal current = meals.get(position);
        holder.mealName.setText(current.getStrMeal());

        Glide.with(context).load(meals.get(position).getStrMealThumb()).into(holder.mealPhoto);
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

            addToFavIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle between two icons
                   // if (addToFavIcon.getTag() != null && addToFavIcon.getTag().equals("favorite")) {
                        addToFavIcon.setImageResource(R.drawable.redhearticon);
                        addToFavIcon.setTag("not_favorite");
                        // Handle removing from favorites here
                    //} else {
                    //    addToFavIcon.setImageResource(R.drawable.addtofavicon);
                    //    addToFavIcon.setTag("favorite");
                        // Handle adding to favorites here
                    //}
                }
            });
        }
    }
}
