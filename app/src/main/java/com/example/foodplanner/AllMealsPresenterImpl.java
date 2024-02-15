package com.example.foodplanner;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.network.*;

import com.example.foodplanner.model.*;

import java.util.List;


public class AllMealsPresenterImpl implements AllMealsPresenter, NetworkCallback{
    private AllMealsView _view;
    private MealRepository _repo;

    public AllMealsPresenterImpl(AllMealsView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getMeals() {
        _repo.getAllMeals(this);
    }

    @Override
    public void getCategoryMeals(String categoryName) {
        _repo.getAllCategoryMeals(categoryName, new NetworkCallback() {
            @Override
            public void onRandomSuccessfullResult(List<Meal> meals) {
                _view.showData(meals);
            }

            @Override
            public void onRandomFailure(String error) {
                _view.showErrMsg(error);
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
        });
    }

    @Override
    public void addToFav(Meal meal) {
        _repo.insertMeal(meal);
    }

    @Override
    public void getCategories() {
        _repo.getAllCategories(this);
    }

    @Override
    public void getAreas() {
        _repo.getAllAreas(this);
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
        _view.showCategories(categories);
    }

    @Override
    public void onAreaSuccessfullResult(List<Area> meals) {
        _view.showAreas(meals);
    }

    @Override
    public void onMealsAreaSuccessfullResult(List<Meal> meals) {
        _view.showData(meals);
    }
}
