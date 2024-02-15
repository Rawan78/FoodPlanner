package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;
import com.example.foodplanner.network.*;

import java.util.List;

public interface MealRepository {
    LiveData<List<Meal>> getStoredMeals();

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);
    void getAllMeals (NetworkCallback networkCallback);
    void getAllCategoryMeals (String categoryName,NetworkCallback networkCallback);
    void getAllCategories (NetworkCallback networkCallback);
    void getAllAreas (NetworkCallback networkCallback);

}
