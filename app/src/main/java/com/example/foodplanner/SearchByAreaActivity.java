package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.*;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchByAreaActivity extends AppCompatActivity implements AllMealsView , OnFavouriteClickListener{
    private static final String TAG = "SearchByAreaActivity";
    SearchByAreaAdapter searchByAreaAdapter;
    EditText editText_SearchByArea;
    RecyclerView recyclerView_SearchByArea;
    LinearLayoutManager linearLayoutManager;
    AllMealsPresenter allMealsPresenter;
    List<Area> areas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_area);

        editText_SearchByArea = findViewById(R.id.et_search_by_area);
        recyclerView_SearchByArea = findViewById(R.id.recyclerView_search_by_area);

        searchByAreaAdapter = new SearchByAreaAdapter(this, new ArrayList<>(), this);
        recyclerView_SearchByArea.setAdapter(searchByAreaAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_SearchByArea.setLayoutManager(linearLayoutManager);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getAreas();

        editText_SearchByArea.addTextChangedListener(new TextWatcher() {
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
                searchByAreaAdapter.getFilter().filter(s);
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

    }

    @Override
    public void showAreas(List<Area> areas) {
        searchByAreaAdapter.setList(areas);
        searchByAreaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFavMealClick(Meal meal) {

    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }
}