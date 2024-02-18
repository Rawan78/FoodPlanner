package com.example.foodplanner.Search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Area.MealsByAreaActivity;
import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.Meals.view.OnFavouriteClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Area;

import java.util.ArrayList;
import java.util.List;

public class SearchByAreaAdapter extends RecyclerView.Adapter<SearchByAreaAdapter.ViewHolder>{
    private static final String TAG = "SearchByAreaAdapter";
    Context context;
    List<Area> meals;
    AllMealsView listener ;
    List<Area> filteredAreas;
    List<Area> areaList;
    OnFavouriteClickListener onFavMealClick;

    public SearchByAreaAdapter(Context context, List<Area> meals, AllMealsView listener) {
        this.context = context;
        this.meals = meals;
        this.filteredAreas = new ArrayList<>(meals);
        this.listener = listener;
        areaList=new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchByAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_area, parent, false);
        SearchByAreaAdapter.ViewHolder viewHolder = new SearchByAreaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByAreaAdapter.ViewHolder holder, int position) {
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
    }
    public void setList(List<Area> updateAreas){
        this.meals=updateAreas;
        areaList.addAll(updateAreas);
    }
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Area> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(areaList); // Add all meals if the search query is empty
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Area area : areaList) {
                    if (area.getStrArea().toLowerCase().contains(filterPattern)) {
                        filteredList.add(area); // Add meal to filtered list if its name contains the search query
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            meals.clear(); // Clear current list of meals
            meals.addAll((List<Area>) results.values); // Add filtered list to current list
            notifyDataSetChanged(); // Notify adapter of data change
        }
    };
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
