package com.example.foodplanner;

import com.example.foodplanner.model.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.db.*;
import com.example.foodplanner.network.*;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealActivity extends AppCompatActivity implements OnFavouriteClickListener, FavMealsView{
    RecyclerView recyclerViewForFavouriteMeals;
    LinearLayoutManager linearLayoutManager;
    FavouriteMealsAdapter favouriteMealsAdapter;
    MealLocalDataSourceImpl mealLocalDataSource;
    FavouriteMealPresenter favouriteMealPresenter;
    private static final String TAG = "FavouriteMealActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_meal);

        recyclerViewForFavouriteMeals = findViewById(R.id.recylerViewFavourite);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewForFavouriteMeals.setLayoutManager(linearLayoutManager);
        favouriteMealsAdapter = new FavouriteMealsAdapter(this, new ArrayList<Meal>(), this);


        mealLocalDataSource = MealLocalDataSourceImpl.getInstance(this);

        favouriteMealPresenter = new FavouriteMealPresenterImpl(this,
                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                        MealLocalDataSourceImpl.getInstance(this)));

        recyclerViewForFavouriteMeals.setAdapter(favouriteMealsAdapter);

        favouriteMealPresenter.getMeals();

        mealLocalDataSource.getAllStoredMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favouriteMealsAdapter.setMeal(meals);
            }
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        favouriteMealsAdapter.setMeal(meals);
        favouriteMealsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void removeMeal(Meal meal) {
        favouriteMealPresenter.removeFromFav(meal);
    }

    @Override
    public void showCategories(List<Category> categories) {

    }

    @Override
    public void onFavMealClick(Meal meal) {
        removeMeal(meal);
        Toast.makeText(this, "Removed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }
}