package com.example.foodplanner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchByCategoryAdapter extends RecyclerView.Adapter<SearchByCategoryAdapter.ViewHolder>{
    private static final String TAG = "SearchByCategoryAdapter";
    Context context;
    List<Category> categories;
    AllMealsView listener ;
    List<Category> filteredMeals;
    List<Category> categoryList;
    OnFavouriteClickListener onFavMealClick;

    public SearchByCategoryAdapter(Context context, List<Category> categories , AllMealsView listener) {
        this.context = context;
        this.categories = categories;
        this.filteredMeals = new ArrayList<>(categories);
        this.listener = listener;
        categoryList=new ArrayList<>();
    }
    public void setList(List<Category> updateCategories){
        this.categories=updateCategories;
        categoryList.addAll(updateCategories);
    }
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(categoryList); // Add all meals if the search query is empty
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Category category : categoryList) {
                    if (category.getStrCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(category); // Add meal to filtered list if its name contains the search query
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categories.clear(); // Clear current list of meals
            categories.addAll((List<Category>) results.values); // Add filtered list to current list
            notifyDataSetChanged(); // Notify adapter of data change
        }
    };
    @NonNull
    @Override
    public SearchByCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category, parent, false);
        SearchByCategoryAdapter.ViewHolder viewHolder = new SearchByCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByCategoryAdapter.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        Category current = categories.get(position);
        holder.categoryName.setText(current.getStrCategory());

        Glide.with(context).load(categories.get(position).getStrCategoryThumb()).into(holder.categoryPhoto);

        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the category clicked
                // Create an Intent to start the activity to show meals in this category
                Intent intent = new Intent(context, MealsOfCategoryActivity.class);
                intent.putExtra("categoryName", current.getStrCategory());
                intent.putExtra("categoryId", current.getIdCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return categories.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryPhoto;
        TextView categoryName;
        public CardView cardCategory;
        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            categoryPhoto = v.findViewById(R.id.iv_categoryPhoto);
            categoryName = v.findViewById(R.id.tv_categoryName);
            cardCategory = v.findViewById(R.id.card_category);
        }
    }
}
