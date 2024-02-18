package com.example.foodplanner.Meals.view;

import java.util.List;
import com.example.foodplanner.model.*;

public interface AllMealsView {

    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    public void addMeal(Meal meal);

    public void showCategories(List<Category> categories);
    public void showAreas(List<Area> areas);

    public void addMealToPlan(MealPlanObject mealPlanObject);


//    void showFavoriteMealsFromFirebase(List<Meal> meals);
//    void showInsertionSuccess();
//    void showInsertionError(String error);

}
