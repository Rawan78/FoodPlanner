package com.example.foodplanner.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private Context context;
    private MealDAO mealsDAO;
    private Flowable<List<Meal>> storedMeals;
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
    public Completable insertMeal(Meal meal) {
        return mealsDAO.insertMeal(meal);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mealsDAO.insertMeal(meal);
//            }
//        }).start();
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        return mealsDAO.deleteMeal(meal);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mealsDAO.deleteMeal(meal);
//            }
//        }).start();
    }

    @Override
    public Flowable<List<Meal>> getAllStoredMeals() {
        return mealsDAO.getAllMeals();
    }

//    @Override
//    public Completable deleteAllMeals() {
//        return mealsDAO.deleteAllMealsFromROOM();
//    }
//
//    @Override
//    public Completable insertAllMeals(List<Meal> meals) {
//        return mealsDAO.insertMealsFromFirebase(meals);
//    }
}
