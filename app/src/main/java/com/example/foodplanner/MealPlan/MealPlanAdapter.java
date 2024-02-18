package com.example.foodplanner.MealPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder>{
    private List<String> daysList;
    private Context context;

    public MealPlanAdapter(List<String> daysList, Context context) {
        this.daysList = daysList;
        this.context = context;
    }

    @NonNull
    @Override
    public MealPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanAdapter.ViewHolder holder, int position) {
        String day = daysList.get(position);
        holder.textViewBreakfast.setText(day + " - Breakfast");
        holder.textViewLunch.setText(day + " - Lunch");
        holder.textViewDinner.setText(day + " - Dinner");
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBreakfast;
        TextView textViewLunch;
        TextView textViewDinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBreakfast = itemView.findViewById(R.id.textViewBreakfast);
            textViewLunch = itemView.findViewById(R.id.textViewLunch);
            textViewDinner = itemView.findViewById(R.id.textViewDinner);
        }
    }
}
