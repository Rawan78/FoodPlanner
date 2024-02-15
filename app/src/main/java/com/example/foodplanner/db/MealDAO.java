package com.example.foodplanner.db;

import com.example.foodplanner.model.*;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meals_table")
    LiveData<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);
}
