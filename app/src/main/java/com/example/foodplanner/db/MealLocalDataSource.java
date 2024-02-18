package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;
import com.example.foodplanner.model.*;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealLocalDataSource {
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Flowable<List<Meal>> getAllStoredMeals();
    Completable deleteAllMeals();

    //Plan
    Completable insertMealToPlan(MealPlanObject mealPlanObject);
    Completable deleteMealToPlan(MealPlanObject mealPlanObject);
    Flowable<List<MealPlanObject>> getAllStoredMealsFromPlan();

}
