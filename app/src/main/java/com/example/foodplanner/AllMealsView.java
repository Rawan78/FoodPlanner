package com.example.foodplanner;

import java.util.List;
import com.example.foodplanner.model.*;

public interface AllMealsView {

    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
    public void addMeal(Meal meal);

    public void showCategories(List<Category> categories);
    public void showAreas(List<Area> areas);

}
