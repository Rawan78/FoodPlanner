package com.example.foodplanner.MealPlan.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodplanner.R;
import com.example.foodplanner.model.MealPlanObject;

public class MealPlanActivity extends AppCompatActivity implements MealPlanView , OnMealPlanClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);


    }

    @Override
    public void showData() {

    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void removeMeal(MealPlanObject mealPlanObject) {

    }


    @Override
    public void onMealPlanClick(MealPlanObject mealPlanObject) {

    }

    @Override
    public void onClickMealForDetails(MealPlanObject mealPlanObject) {

    }
}