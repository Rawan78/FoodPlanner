package com.example.foodplanner.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.foodplanner.model.MealPlanObject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealPlanDAO {
    @Query("SELECT * FROM mealsPlan_table")
    Flowable<List<MealPlanObject>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealPlanObject mealPlanObject);

    @Delete
    Completable deleteMeal(MealPlanObject mealPlanObject);

    @Query("DELETE FROM mealsPlan_table")
    Completable deleteAllMealsFromROOM();
}
