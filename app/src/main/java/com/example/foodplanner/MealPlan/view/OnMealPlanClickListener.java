package com.example.foodplanner.MealPlan.view;
import com.example.foodplanner.model.*;

import com.example.foodplanner.model.Meal;

public interface OnMealPlanClickListener {
    void onMealPlanClick(MealPlanObject mealPlanObject);
    void onClickMealForDetails(MealPlanObject mealPlanObject);
}
