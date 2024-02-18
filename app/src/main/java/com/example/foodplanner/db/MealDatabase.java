package com.example.foodplanner.db;

import android.content.Context;

import com.example.foodplanner.model.*;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class , MealPlanObject.class} , version = 2)
public abstract class MealDatabase extends RoomDatabase {
    private static MealDatabase instance = null;
    public abstract MealDAO getMealDAO();
    public abstract MealPlanDAO getMealPlanDAO();
    public static synchronized MealDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext() , MealDatabase.class , "mealsdb").build();
        }

        return instance;
    }
}
