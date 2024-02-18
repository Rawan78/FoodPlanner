package com.example.foodplanner.Meals.presenter;
import android.util.Log;

import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.model.Meal;

import com.example.foodplanner.model.*;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.example.foodplanner.MealPlan.view.*;


public class AllMealsPresenterImpl implements AllMealsPresenter {
    private AllMealsView _view;
    private MealRepository _repo;
    private MealPlanView mealPlanView;
    private static final String TAG = "AllMealsPresenterImpl";

    public AllMealsPresenterImpl(AllMealsView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    public AllMealsPresenterImpl(MealPlanView mealPlanView , MealRepository _repo) {
        this.mealPlanView = mealPlanView;
        this._repo = _repo;
    }

    @Override
    public void getMeals() {
        _repo.getAllMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull MealResponse mealResponse) {
                        _view.showData(mealResponse.meals);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCategoryMeals(String categoryName) {
        _repo.getAllCategoryMeals(categoryName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealResponse mealResponse) {
                        _view.showData(mealResponse.meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getAreaMeals(String areaName) {
        _repo.getAllAreaMeals(areaName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealResponse mealResponse) {
                        _view.showData(mealResponse.meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMealsByIngredients(String ingredientName) {
        _repo.getMealsByIngredients(ingredientName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealResponse mealResponse) {
                        _view.showData(mealResponse.meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addToFav(Meal meal) {
        _repo.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.i(TAG, "addToFav: Successfully Insert");
                },error->{
                    Log.i(TAG, "addToFav: Failed To Insert");
                });
    }

    @Override
    public void getCategories() {
        _repo.getAllCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryResponse categoryResponse) {
                        _view.showCategories(categoryResponse.categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getAreas() {
        _repo.getAllAreas()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AreaResponse areaResponse) {
                        _view.showAreas(areaResponse.meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.showErrMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void deleteAllMealsFromROOM() {

    }

    @Override
    public void addToPlan(MealPlanObject mealPlanObject) {
        //mealPlanObject.setDay(dayName);
        _repo.insertMealToPlan(mealPlanObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> {
                    Log.i(TAG, "addToPlan: Successfully Insert");
                }, error ->{
                    Log.i(TAG, "addToPlan: Failed To Insert");
                });

//        _repo.insertMeal(meal)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(()->{
//                    Log.i(TAG, "addToFav: Successfully Insert");
//                },error->{
//                    Log.i(TAG, "addToFav: Failed To Insert");
//                });
    }
}
