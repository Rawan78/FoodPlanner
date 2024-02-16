package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.Area;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealRepositoryImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchMealsByIngredientActivity extends AppCompatActivity implements AllMealsView , OnFavouriteClickListener{
    SearchMealsByIngredientAdapter searchMealsByIngredientAdapter;
    EditText editText_SearchByIngredient;
    RecyclerView recyclerView_SearchByIngredient;
    LinearLayoutManager linearLayoutManager;
    AllMealsPresenter allMealsPresenter;
    List<Meal> meals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meals_by_ingredient);

        editText_SearchByIngredient = findViewById(R.id.et_search_by_ingredient);
        recyclerView_SearchByIngredient = findViewById(R.id.recyclerView_search_by_ingredient);

        String ingredientName = editText_SearchByIngredient.getText().toString();

        searchMealsByIngredientAdapter = new SearchMealsByIngredientAdapter(this, new ArrayList<>(), this);
        recyclerView_SearchByIngredient.setAdapter(searchMealsByIngredientAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_SearchByIngredient.setLayoutManager(linearLayoutManager);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getMealsByIngredients(ingredientName);

        editText_SearchByIngredient.addTextChangedListener(new TextWatcher() {
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
                searchMealsByIngredientAdapter.getFilter().filter(s);
            }
        });

    }

    @Override
    public void showData(List<Meal> meals) {
        if (searchMealsByIngredientAdapter != null) {
            searchMealsByIngredientAdapter.setList(meals);
            searchMealsByIngredientAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void showCategories(List<Category> categories) {

    }

    @Override
    public void showAreas(List<Area> areas) {

    }

    @Override
    public void onFavMealClick(Meal meal) {

    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }
}