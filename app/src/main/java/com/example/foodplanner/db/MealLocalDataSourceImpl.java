package com.example.foodplanner.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private Context context;
    private MealDAO mealsDAO;
    private LiveData<List<Meal>> storedMeals;
    private static MealLocalDataSourceImpl repo = null;

    public MealLocalDataSourceImpl(Context context) {
        this.context = context;
        MealDatabase mealDatabase = MealDatabase.getInstance(context.getApplicationContext());
        mealsDAO = mealDatabase.getMealDAO();
        storedMeals = mealsDAO.getAllMeals();
    }
    public static synchronized MealLocalDataSourceImpl getInstance(Context context){
        if (repo == null){
            repo = new MealLocalDataSourceImpl(context);
        }
        return repo;
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getAllStoredMeals() {
        return storedMeals;
    }
}
