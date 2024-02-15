package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.db.*;

import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.network.*;

import java.util.List;

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
    public LiveData<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public void insertMeal(Meal meal) {
        mealLocalDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealLocalDataSource.deleteMeal(meal);
    }

    @Override
    public void getAllMeals(NetworkCallback networkCallback) {
        mealRemoteDataSource.networkMethod(networkCallback);
    }

    @Override
    public void getAllCategoryMeals(String categoryName, NetworkCallback networkCallback) {
        mealRemoteDataSource.networkMethodForCategories(networkCallback);
    }

    @Override
    public void getAllCategories(NetworkCallback networkCallback) {
        mealRemoteDataSource.networkMethodForCategories(networkCallback);
    }

    @Override
    public void getAllAreas(NetworkCallback networkCallback) {
        mealRemoteDataSource.networkMethodForAreas(networkCallback);
    }
}
