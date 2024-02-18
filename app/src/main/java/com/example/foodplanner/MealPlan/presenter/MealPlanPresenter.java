package com.example.foodplanner.MealPlan.presenter;
import com.example.foodplanner.model.*;

import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MealPlanPresenter {
    public Flowable<List<MealPlanObject>> getMealsPlan();
    public void removeFromPlan(MealPlanObject mealPlanObject);

    //public void insertToPlan(MealPlanObject mealPlanObject);
    void deleteAllMealsPlanFromRoom();

}
