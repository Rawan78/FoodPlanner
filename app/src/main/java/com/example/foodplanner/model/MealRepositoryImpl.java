package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.db.*;

import com.example.foodplanner.network.*;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class MealRepositoryImpl implements MealRepository{
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static MealRepositoryImpl repo = null;

    public MealRepositoryImpl(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    public static MealRepositoryImpl getInstance(MealRemoteDataSource mealRemoteDataSource , MealLocalDataSource mealLocalDataSource){
        if(repo == null){
            repo = new MealRepositoryImpl(mealRemoteDataSource , mealLocalDataSource);
        }
        return repo;
    }

    @Override
    public Flowable<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public Completable insertMeal(Meal meal) {
        return mealLocalDataSource.insertMeal(meal);
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        return mealLocalDataSource.deleteMeal(meal);
    }

    @Override
    public Observable<MealResponse> getAllMeals() {
        return mealRemoteDataSource.networkMethod();
    }

    @Override
    public Observable<MealResponse> getAllCategoryMeals(String categoryName) {
       return mealRemoteDataSource.networkMethodForCategoryMeals(categoryName);
    }

    @Override
    public Observable<MealResponse> getAllAreaMeals(String areaName) {
        return mealRemoteDataSource.networkMethodForMealsByAreas(areaName);
    }

    @Override
    public Observable<MealResponse> getMealsByIngredients(String ingredientName) {
        return mealRemoteDataSource.networkMethodForMealsByIngredients(ingredientName);
    }

    @Override
    public Observable<CategoryResponse> getAllCategories( ) {
        return mealRemoteDataSource.networkMethodForCategories();
    }

    @Override
    public Observable<AreaResponse> getAllAreas() {
        return mealRemoteDataSource.networkMethodForAreas();
    }

    @Override
    public Completable deleteAllMeals() {
        return mealLocalDataSource.deleteAllMeals();
    }

    @Override
    public Completable insertMealToPlan(MealPlanObject mealPlanObject) {
        return mealLocalDataSource.insertMealToPlan(mealPlanObject);
    }

    @Override
    public Completable deleteMealFromPlan(MealPlanObject mealPlanObject) {
        return mealLocalDataSource.deleteMealToPlan(mealPlanObject);
    }

    @Override
    public Flowable<List<MealPlanObject>> getStoredMealsPlan() {
        return mealLocalDataSource.getAllStoredMealsFromPlan();
    }

    @Override
    public Completable deleteAllMealsPlan() {
        return mealLocalDataSource.deleteAllMealsPlan();
    }


}
