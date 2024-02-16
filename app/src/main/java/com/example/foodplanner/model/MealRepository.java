package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;
import com.example.foodplanner.network.*;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealRepository {
    Flowable<List<Meal>> getStoredMeals();

    Completable insertMeal(Meal meal);

    Completable deleteMeal(Meal meal);
    public Observable<MealResponse> getAllMeals ();
    public Observable<MealResponse> getAllCategoryMeals (String categoryName);
    public Observable<MealResponse> getAllAreaMeals (String areaName);
    public Observable<MealResponse> getMealsByIngredients (String ingredientName);
    public Observable<CategoryResponse> getAllCategories ();
    public Observable<AreaResponse> getAllAreas ();

//    Completable insertAllMealsFromFirebase(List<Meal> meals);
//    Completable deleteAllMeals();

}
