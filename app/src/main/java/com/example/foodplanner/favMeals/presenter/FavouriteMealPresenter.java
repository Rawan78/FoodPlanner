package com.example.foodplanner.favMeals.presenter;

import com.example.foodplanner.model.*;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavouriteMealPresenter {
    public Flowable<List<Meal>> getMeals();
    public void removeFromFav(Meal meal);
}
