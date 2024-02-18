package com.example.foodplanner.favMeals.presenter;

import android.util.Log;

import com.example.foodplanner.favMeals.view.FavMealsView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.*;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteMealPresenterImpl implements FavouriteMealPresenter {
    private FavMealsView _view;
    private MealRepository _repo;
    private static final String TAG = "FavouriteMealPresenterI";

    public FavouriteMealPresenterImpl(FavMealsView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public Flowable<List<Meal>> getMeals() {
        return _repo.getStoredMeals();
    }

    @Override
    public void removeFromFav(Meal meal) {
        _repo.deleteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.i(TAG, "addToFav: Successfully Insert");
                },error->{
                    Log.i(TAG, "addToFav: Failed To Insert");
                });
    }

    @Override
    public void deleteAllMealsFromRoom() {
        _repo.deleteAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.i(TAG, "addToFav: Successfully Insert All ");
                },error->{
                    Log.i(TAG, "addToFav: Failed To Insert");
                });
    }

}
