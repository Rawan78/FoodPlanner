package com.example.foodplanner.network;

import android.util.Log;
import android.widget.Toast;
import com.example.foodplanner.*;

import com.example.foodplanner.AreaAdapter;
import com.example.foodplanner.CategoriesAdapter;
import com.example.foodplanner.CategoryAdapter;
import com.example.foodplanner.MealsOfCategoryActivity;
import com.example.foodplanner.model.AreaResponse;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealResponse;
import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource{
    private static final String TAG = "MealRemoteDataSourceImp";
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealRemoteDataSourceImpl client = null;
    private MealService myRandomMealsService;
    private MealService myCategoryMealsService;
    private MealService myCategoryService;
    private MealService myAreaService;
    private MealService myAreaMealsService;

    private MealRemoteDataSourceImpl( ){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();

        //For Random Meals In Home
        myRandomMealsService = retrofit.create(MealService.class);

        //For Meals Of Category in MealsOfCategoryActivity
        myCategoryMealsService = retrofit.create(MealService.class);

        //For Categories in HomeFragment
        myCategoryService = retrofit.create(MealService.class);

        //For Areas in HomeFragment
        myAreaService = retrofit.create(MealService.class);

        //For Meals Of Area In MealsByAreaActivity
        myAreaMealsService = retrofit.create(MealService.class);

    }
    public static MealRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new MealRemoteDataSourceImpl();
        }
        return client;
    }

    @Override
    public void networkMethod(NetworkCallback networkCallback) {
        myRandomMealsService.getRandomMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onRandomSuccessfullResult(response.body().meals);
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response.body());
                }
            }
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallback.onRandomFailure(t.getMessage());
            }
        });
    }


    //For Meals of Category
    // Retrieve category name from intent
    @Override
    public void networkMethodForCategoryMeals(String categoryName,NetworkCallback networkCallback) {
        myCategoryMealsService.getCategoryMeals(categoryName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onRandomSuccessfullResult(response.body().meals);
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response.body());
                    //Toast.makeText(MealsOfCategoryActivity.this, "Downloaded Successfully" + response.body().meals.size(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallback.onRandomFailure(t.getMessage());
                //Toast.makeText(MealsOfCategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void networkMethodForCategories(NetworkCallback networkCallback) {
        myCategoryService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkCallback.onCategorySuccessfullResult(response.body().categories);
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                networkCallback.onRandomFailure(t.getMessage());
            }
        });
    }

    @Override
    public void networkMethodForAreas(NetworkCallback networkCallback) {
        myAreaService.getAreas().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {

                networkCallback.onAreaSuccessfullResult(response.body().meals);
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                networkCallback.onRandomFailure(t.getMessage());
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void networkMethodForMealsByAreas(NetworkCallback networkCallback) {
        String areaName = MealsByAreaActivity.areaName;
        myAreaMealsService.getAreaMeals(areaName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                networkCallback.onRandomSuccessfullResult(response.body().meals);

                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: " + response.body());
                    // Toast.makeText(CategoriesActivity.this, "Downloaded Successfully" + response.body().products.size(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onRandomFailure(t.getMessage());
                Log.i(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(MealsByAreaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
