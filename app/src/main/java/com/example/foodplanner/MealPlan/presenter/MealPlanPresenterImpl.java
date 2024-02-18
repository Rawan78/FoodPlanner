package com.example.foodplanner.MealPlan.presenter;

import android.util.Log;

import com.example.foodplanner.model.MealPlanObject;
import com.example.foodplanner.MealPlan.view.*;
import com.example.foodplanner.model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanPresenterImpl implements MealPlanPresenter{
    private static final String TAG = "MealPlanPresenterImpl";
    private MealPlanView _view;
    private MealRepository _repo;

    public MealPlanPresenterImpl(MealPlanView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public Flowable<List<MealPlanObject>> getMealsPlan() {
        return _repo.getStoredMealsPlan();
    }

    @Override
    public void removeFromPlan(MealPlanObject mealPlanObject) {
        _repo.deleteMealFromPlan(mealPlanObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.i(TAG, "addToPlan: Successfully Insert");
                },error->{
                    Log.i(TAG, "addToFav: Failed To Insert");
                });
    }

    @Override
    public void deleteAllMealsPlanFromRoom() {
        _repo.deleteAllMealsPlan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.i(TAG, "addToPlan: Successfully Insert All ");
                },error->{
                    Log.i(TAG, "addToPlan: Failed To Insert");
                });
//        _repo.deleteAllMeals()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(()->{
//                    Log.i(TAG, "addToFav: Successfully Insert All ");
//                },error->{
//                    Log.i(TAG, "addToFav: Failed To Insert");
//                });
    }

//    @Override
//    public void insertToPlan(MealPlanObject mealPlanObject, String dayName) {
//        mealPlanObject.setDay(dayName);
//
//    }
}
