package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;
import com.example.foodplanner.model.*;

import java.util.List;

public interface MealLocalDataSource {
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    LiveData<List<Meal>> getAllStoredMeals();
}
