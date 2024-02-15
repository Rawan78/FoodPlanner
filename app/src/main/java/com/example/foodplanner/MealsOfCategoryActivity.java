package com.example.foodplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.Area;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealRepositoryImpl;
import com.example.foodplanner.model.MealResponse;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealService;

import java.util.ArrayList;
import java.util.List;

import com.example.foodplanner.network.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsOfCategoryActivity extends AppCompatActivity implements OnFavouriteClickListener , AllMealsView , NetworkCallback{
    RecyclerView recyclerViewMealsByCategory;
    LinearLayoutManager linearLayoutManager;
    CategoriesAdapter categoriesAdapter;
    AllMealsPresenter allMealsPresenter;
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

        String categoryName = getIntent().getStringExtra("categoryName");

        //Adapter
        categoriesAdapter = new CategoriesAdapter(this, new ArrayList<>() , this);
        recyclerViewMealsByCategory.setAdapter(categoriesAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getMeals();
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
    public void onFavMealClick(Meal meal) {
        addMeal(meal);
    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }

    @Override
    public void onRandomSuccessfullResult(List<Meal> meals) {

    }

    @Override
    public void onRandomFailure(String errMsg) {

    }

    @Override
    public void onCategorySuccessfullResult(List<Category> categories) {

    }

    @Override
    public void onAreaSuccessfullResult(List<Area> meals) {

    }

    @Override
    public void onMealsAreaSuccessfullResult(List<Meal> meals) {

    }
//    public static String getCategoryName() {
//        return categoryName;
//    }
}