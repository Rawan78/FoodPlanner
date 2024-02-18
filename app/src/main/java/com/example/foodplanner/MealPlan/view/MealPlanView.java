package com.example.foodplanner.MealPlan.view;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.*;

public interface MealPlanView {
    public void showData();
    public void showErrMsg(String error);
    public void removeMeal(MealPlanObject mealPlanObject);
}
