package com.example.foodplanner;
import com.example.foodplanner.model.*;

public interface AllMealsPresenter {

    public void getMeals();
    public void getCategoryMeals(String categoryName);
    public void addToFav(Meal meal);
    public void getCategories();
    public void getAreas();
}
