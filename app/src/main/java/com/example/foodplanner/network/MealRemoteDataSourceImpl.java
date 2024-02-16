package com.example.foodplanner.network;

import com.example.foodplanner.model.AreaResponse;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<MealResponse> networkMethod() {
        return myRandomMealsService.getRandomMeals()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MealResponse> networkMethodForCategoryMeals(String categoryName) {
        return myCategoryMealsService.getCategoryMeals(categoryName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CategoryResponse> networkMethodForCategories() {
        return myCategoryService.getCategories()
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Observable<AreaResponse> networkMethodForAreas() {
        return myAreaService.getAreas()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MealResponse> networkMethodForMealsByAreas(String areaName) {
        return myAreaMealsService.getAreaMeals(areaName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MealResponse> networkMethodForMealsByIngredients(String ingredientName) {
        return myAreaMealsService.getAreaMeals(ingredientName)
                .subscribeOn(Schedulers.io());
    }
}
