package com.example.foodplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsByAreaActivity extends AppCompatActivity implements OnFavouriteClickListener , AllMealsView{
    RecyclerView recyclerViewMealsByArea;
    LinearLayoutManager linearLayoutManager;
    MealsByAreaAdapter mealsByAreaAdapter;
    AllMealsPresenter allMealsPresenter;
    //static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealsByAreaActivity";
    public static String areaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_by_area);

        recyclerViewMealsByArea = findViewById(R.id.recyclerView_mealsByArea);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMealsByArea.setLayoutManager(linearLayoutManager);

        // Retrieve category name from intent extras
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            areaName= extra.getString("areaName");
        }

        //Adapter
        mealsByAreaAdapter = new MealsByAreaAdapter(this, new ArrayList<>() , this);
        recyclerViewMealsByArea.setAdapter(mealsByAreaAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));
        allMealsPresenter.getAreaMeals(areaName);

//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL).build();

//        MealService myService = retrofit.create(MealService.class);
//        myService.getAreaMeals(areaName).enqueue(new Callback<MealResponse>() {
//            @Override
//            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
//                //Adapter
//                mealsByAreaAdapter = new MealsByAreaAdapter(MealsByAreaActivity.this, response.body().meals);
//                recyclerViewMealsByArea.setAdapter(mealsByAreaAdapter);
//                if(response.isSuccessful()){
//                    Log.i(TAG, "onResponse: " + response.body());
//                    // Toast.makeText(CategoriesActivity.this, "Downloaded Successfully" + response.body().products.size(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<MealResponse> call, Throwable t) {
//                Log.i(TAG, "onFailure: " + t.getMessage());
//                Toast.makeText(MealsByAreaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void showData(List<Meal> meals) {
        mealsByAreaAdapter.setMeal(meals);
        mealsByAreaAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Downloaded Successfully" + meals.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occured");
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
}