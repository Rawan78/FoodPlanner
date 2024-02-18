package com.example.foodplanner.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealPlanObject;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private Context context;
    private MealDAO mealsDAO;
    private Flowable<List<Meal>> storedMeals;
    private static MealLocalDataSourceImpl repo = null;

    MealPlanDAO mealPlanDAO;
    private Flowable<List<MealPlanObject>> storedMealsPlan;

    public MealLocalDataSourceImpl(Context context) {
        this.context = context;
        MealDatabase mealDatabase = MealDatabase.getInstance(context.getApplicationContext());
        mealsDAO = mealDatabase.getMealDAO();
        storedMeals = mealsDAO.getAllMeals();

        //For MealPlan DAO
        mealPlanDAO = mealDatabase.getMealPlanDAO();
        storedMealsPlan = mealPlanDAO.getAllMeals();

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
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        return mealsDAO.deleteMeal(meal);

    }

    @Override
    public Flowable<List<Meal>> getAllStoredMeals() {
        return mealsDAO.getAllMeals();
    }

    @Override
    public Completable deleteAllMeals() {
        return mealsDAO.deleteAllMealsFromROOM();
    }

    @Override
    public Completable insertMealToPlan(MealPlanObject mealPlanObject) {
        return mealPlanDAO.insertMeal(mealPlanObject );
    }

    @Override
    public Completable deleteMealToPlan(MealPlanObject mealPlanObject) {
        return mealPlanDAO.deleteMeal(mealPlanObject);
    }

    @Override
    public Flowable<List<MealPlanObject>> getAllStoredMealsFromPlan() {
        return mealPlanDAO.getAllMeals();
    }

    @Override
    public Completable deleteAllMealsPlan() {
        return mealPlanDAO.deleteAllMealsFromROOM();
    }

}
