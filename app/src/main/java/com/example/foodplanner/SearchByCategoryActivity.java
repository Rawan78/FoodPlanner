package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchByCategoryActivity extends AppCompatActivity implements AllMealsView , OnFavouriteClickListener{
    SearchByCategoryAdapter searchByCategoryAdapter;
    EditText editText_SearchByCategory;
    RecyclerView recyclerView_SearchByCategory;
    LinearLayoutManager linearLayoutManager;
    AllMealsPresenter allMealsPresenter;
    List<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_category);

        editText_SearchByCategory = findViewById(R.id.et_search_by_category);
        recyclerView_SearchByCategory = findViewById(R.id.recyclerView_search_by_category);

        searchByCategoryAdapter = new SearchByCategoryAdapter(this, new ArrayList<>(), this);
        recyclerView_SearchByCategory.setAdapter(searchByCategoryAdapter);
       // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_SearchByCategory.setLayoutManager(linearLayoutManager);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getCategories();

        editText_SearchByCategory.addTextChangedListener(new TextWatcher() {
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
                searchByCategoryAdapter.getFilter().filter(s);
            }
        });

    }

    @Override
    public void showData(List<Meal> meals) {

    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void showCategories(List<Category> categories) {
        searchByCategoryAdapter.setList(categories);
        searchByCategoryAdapter.notifyDataSetChanged();
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