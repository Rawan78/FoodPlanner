package com.example.foodplanner.network;

import com.example.foodplanner.model.AreaResponse;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php?s")
    Call<MealResponse> getRandomMeals();

    @GET("filter.php?c=Seafood")
    Call<MealResponse> getCategoryMeals(@Query("c") String categoryName);

    @GET("filter.php?a=Canadian")
    Call<MealResponse> getAreaMeals(@Query("a") String areaName);

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreas();

    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);

    @GET("search.php")
    Call<MealResponse> searchMealByName(@Query("s") String mealName);
}
