package com.example.foodplanner.favMeals.view;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodplanner.Meals.view.OnFavouriteClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.ViewHolder>{
    private static final String TAG = "FavouriteMealsAdapter";
    Context context;
    List<Meal> meals;
    private OnFavouriteClickListener favouriteClicklistener;

    public FavouriteMealsAdapter(Context context, List<Meal> _meals, OnFavouriteClickListener favouriteClicklistener) {
        this.context = context;
        this.meals = _meals;
        this.favouriteClicklistener = favouriteClicklistener;
        meals = new ArrayList<Meal>();
    }

    @NonNull
    @Override
    public FavouriteMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_meal, parent, false);
        FavouriteMealsAdapter.ViewHolder viewHolder = new FavouriteMealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealsAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Meal current = meals.get(position);
        holder.mealName.setText(current.getStrMeal());

        Glide.with(context).load(meals.get(position).getStrMealThumb()).into(holder.mealPhoto);
        holder.addToFavIcon.setImageResource(R.drawable.redhearticon);
        holder.addToFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteClicklistener.onFavMealClick(current);

                Toast.makeText(context, "Removed From Favourite", Toast.LENGTH_SHORT).show();
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
    public void setMeal(List<Meal> updateMeals) {
        this.meals = updateMeals;
        notifyDataSetChanged();
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
