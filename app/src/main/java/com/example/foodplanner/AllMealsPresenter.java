package com.example.foodplanner;
import com.example.foodplanner.model.*;

import java.util.List;

public interface AllMealsPresenter {

    public void getMeals();
    public void getCategoryMeals(String categoryName);
    public void getAreaMeals(String areaName);
    public void getMealsByIngredients(String ingredientName);
    public void addToFav(Meal meal);
    public void getCategories();
    public void getAreas();

//    public void insertAllMealsFromFirebase(String userId);
//    public void deleteAllMealsFromROOM();

}
