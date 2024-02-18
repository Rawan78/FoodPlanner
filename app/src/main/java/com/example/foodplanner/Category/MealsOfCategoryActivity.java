package com.example.foodplanner.Category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.Meals.view.CategoriesAdapter;
import com.example.foodplanner.Meals.view.OnFavouriteClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.Area;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealPlanObject;
import com.example.foodplanner.model.MealRepositoryImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class MealsOfCategoryActivity extends AppCompatActivity implements OnFavouriteClickListener, AllMealsView {
    RecyclerView recyclerViewMealsByCategory;
    LinearLayoutManager linearLayoutManager;
    CategoriesAdapter categoriesAdapter;
    AllMealsPresenter allMealsPresenter;
    String categoryName;
    private static final String TAG = "RetrofitMainActivity";
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_CATEGORY_NAME = "categoryName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_of_category);

        recyclerViewMealsByCategory = findViewById(R.id.recyclerView_mealsByCategory);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMealsByCategory.setLayoutManager(linearLayoutManager);

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            categoryName= extra.getString("categoryName");
        }

        //Adapter
        categoriesAdapter = new CategoriesAdapter(this, new ArrayList<>() , this);
        recyclerViewMealsByCategory.setAdapter(categoriesAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getCategoryMeals(categoryName);
        // Call getMeals with the retrieved categoryName
    }


    @Override
    public void showData(List<Meal> meals) {
        categoriesAdapter.setMeal(meals);
        categoriesAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Downloaded Successfully" + meals.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void addMeal(Meal meal) {
        allMealsPresenter.addToFav(meal);
    }

    @Override
    public void showCategories(List<Category> categories) {

    }

    @Override
    public void showAreas(List<Area> areas) {

    }

    @Override
    public void addMealToPlan(MealPlanObject mealPlanObject) {

    }


    @Override
    public void onFavMealClick(Meal meal) {
        addMeal(meal);
    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }
}