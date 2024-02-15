package com.example.foodplanner;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.model.*;
import com.example.foodplanner.network.*;
import com.example.foodplanner.db.*;


import com.example.foodplanner.model.AreaResponse;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealResponse;
import com.example.foodplanner.network.MealService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements OnFavouriteClickListener , AllMealsView{

    RecyclerView recyclerViewArea;
    AreaAdapter areaAdapter;
    RecyclerView recyclerViewRanomMeals;
    LinearLayoutManager linearLayoutManager;
    CategoriesAdapter categoriesAdapter;
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerViewCategory;
    AllMealsPresenter allMealsPresenter;
    private static final String TAG = "RetrofitMainActivity";
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //RecyclerView and Retrofit For Random Meals
        recyclerViewRanomMeals = view.findViewById(R.id.recyclerView_random);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewRanomMeals.setLayoutManager(linearLayoutManager);

        //Adapter
        categoriesAdapter = new CategoriesAdapter(getContext() , new ArrayList<>() ,this);
        recyclerViewRanomMeals.setAdapter(categoriesAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(getActivity())));
        allMealsPresenter.getMeals();


        //RecyclerView and Retrofit For Categories in the Home Fragment
        recyclerViewCategory = view.findViewById(R.id.recyclerView_category);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        //Adapter
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        recyclerViewCategory.setAdapter(categoryAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(getActivity())));
        allMealsPresenter.getCategories();


        //RecyclerView and Retrofit For Area
        recyclerViewArea = view.findViewById(R.id.recyclerView_Area);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewArea.setLayoutManager(linearLayoutManager);

        //Adapter
        areaAdapter = new AreaAdapter(getContext(), new ArrayList<>());
        recyclerViewArea.setAdapter(areaAdapter);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(getActivity())));
        allMealsPresenter.getAreas();

        return view;

    }

    @Override
    public void onFavMealClick(Meal meal) {
        addMeal(meal);
    }

    @Override
    public void onClickMealForDetails(Meal meal) {
       // click to the meal
    }

    @Override
    public void showData(List<Meal> meals) {
        categoriesAdapter.setMeal(meals);
        categoriesAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "Downloaded Successfully" + meals.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        categoryAdapter.setCategory(categories);
        categoryAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "Categories Downloaded Successfully" + categories.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAreas(List<Area> areas) {
        areaAdapter.setArea(areas);
        areaAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "Categories Downloaded Successfully" + areas.size(), Toast.LENGTH_SHORT).show();
    }
}