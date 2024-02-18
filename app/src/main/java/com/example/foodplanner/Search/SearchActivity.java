package com.example.foodplanner.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.Meals.view.OnFavouriteClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.network.*;
import com.example.foodplanner.model.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.foodplanner.model.Area;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AllMealsView, OnFavouriteClickListener {
    EditText editText_SearchByName;
    RecyclerView recyclerView_SearchByName;
    LinearLayoutManager linearLayoutManager;
    SearchAdapter searchAdapter;
    AllMealsPresenter allMealsPresenter;
    List<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText_SearchByName = findViewById(R.id.et_search_by_name);
        recyclerView_SearchByName = findViewById(R.id.recyclerView_search_by_name);

        searchAdapter = new SearchAdapter(this, new ArrayList<>(), this);
        recyclerView_SearchByName.setAdapter(searchAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_SearchByName.setLayoutManager(linearLayoutManager);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getMeals();

        editText_SearchByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                  //Log.i(TAG, "beforeTextChanged: ");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //   Log.i(TAG, "onTextChanged: ");
            }
            @Override
            public void afterTextChanged(Editable s) {
                //  Log.i(TAG, "afterTextChanged: ");
                searchAdapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void showData(List<Meal> meals) {
        searchAdapter.setList(meals);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void showCategories(List<Category> categories) {
        //No Need For This
    }

    @Override
    public void showAreas(List<Area> areas) {
        //No Need For This
    }

    @Override
    public void addMealToPlan(MealPlanObject mealPlanObject) {

    }

    @Override
    public void onFavMealClick(Meal meal) {

    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }
}