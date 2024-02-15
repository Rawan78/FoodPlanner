package com.example.foodplanner;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.*;
import com.example.foodplanner.network.*;

import java.util.List;


public class FavouriteMealPresenterImpl implements FavouriteMealPresenter, NetworkCallback{
    private FavMealsView _view;
    private MealRepository _repo;

    public FavouriteMealPresenterImpl(FavMealsView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getMeals() {
        _repo.getStoredMeals();
    }

    @Override
    public void removeFromFav(Meal meal) {
        _repo.deleteMeal(meal);
    }

    @Override
    public void onRandomSuccessfullResult(List<Meal> meals) {
        _view.showData(meals);
    }

    @Override
    public void onRandomFailure(String errMsg) {
        _view.showErrMsg(errMsg);
    }

    @Override
    public void onCategorySuccessfullResult(List<Category> categories) {

    }

    @Override
    public void onAreaSuccessfullResult(List<Area> meals) {

    }

    @Override
    public void onMealsAreaSuccessfullResult(List<Meal> meals) {
        _view.showData(meals);
    }
}
