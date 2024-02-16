package com.example.foodplanner;

import java.util.List;
import com.example.foodplanner.model.*;

public interface FavMealsView {
    public void showData();
    public void showErrMsg(String error);
    public void removeMeal(Meal meal);

    public void showCategories();

}
