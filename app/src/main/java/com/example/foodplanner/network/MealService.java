package com.example.foodplanner.network;

import com.example.foodplanner.model.AreaResponse;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php?s")
    Observable<MealResponse> getRandomMeals();

    @GET("filter.php")
    Observable<MealResponse> getCategoryMeals(@Query("c") String categoryName);

    @GET("filter.php")
    Observable<MealResponse> getAreaMeals(@Query("a") String areaName);

    @GET("categories.php")
    Observable<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Observable<AreaResponse> getAreas();

    @GET("lookup.php")
    Observable<MealResponse> getMealDetails(@Query("i") String mealId);

    @GET("search.php")
    Observable<MealResponse> searchMealByName(@Query("s") String mealName);

    @GET("filter.php")
    Observable<MealResponse> searchMealByIngredient(@Query("i") String mealIngredient);
}
