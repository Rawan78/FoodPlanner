package com.example.foodplanner.network;
import com.example.foodplanner.model.*;

import io.reactivex.rxjava3.core.Observable;

public interface MealRemoteDataSource {
    //For Random Meals In Home Fragment
    Observable<MealResponse> networkMethod( );
    Observable<MealResponse> networkMethodForCategoryMeals(String categoryName);
    Observable<CategoryResponse> networkMethodForCategories();
    Observable<AreaResponse> networkMethodForAreas();
    Observable<MealResponse> networkMethodForMealsByAreas(String areaName);
    Observable<MealResponse> networkMethodForMealsByIngredients(String ingredientName);

}
