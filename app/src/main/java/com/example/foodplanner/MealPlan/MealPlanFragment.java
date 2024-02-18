package com.example.foodplanner.MealPlan;

import android.os.Bundle;

import com.example.foodplanner.MealPlan.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.MealPlan.MealPlanAdapter;
import com.example.foodplanner.MealPlan.view.MealPlanView;
import com.example.foodplanner.MealPlan.view.OnMealPlanClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.favMeals.presenter.FavouriteMealPresenterImpl;
import com.example.foodplanner.favMeals.view.FavouriteMealsAdapter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealPlanObject;
import com.example.foodplanner.MealPlan.presenter.*;
import com.example.foodplanner.model.MealRepositoryImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanFragment extends Fragment implements MealPlanView, OnMealPlanClickListener {
    private static final String TAG = "MealPlanFragment";
    //private RecyclerView recyclerViewMealPlan;
    //private MealPlanAdapter mealPlanAdapter;
    //private List<String> daysList;

    DayMealPlanAdapter saturdayAdapter;
    DayMealPlanAdapter sundayAdapter;
    DayMealPlanAdapter mondayAdapter;
    DayMealPlanAdapter tuesdayAdapter;
    DayMealPlanAdapter wednesdayAdapter;
    DayMealPlanAdapter thursdayAdapter;
    DayMealPlanAdapter fridayAdapter;
    MealPlanPresenter mealPlanPresenter;
    LinearLayoutManager linearLayoutManager;
    MealLocalDataSourceImpl mealLocalDataSource;
    RecyclerView recyclerViewSaturDay;
    RecyclerView recyclerViewSunDay;
    RecyclerView recyclerViewMonDay;
    RecyclerView recyclerViewTuesDay;
    RecyclerView recyclerViewWednesDay;
    RecyclerView recyclerViewThursDay;
    RecyclerView recyclerViewFriDay;


    public MealPlanFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        recyclerViewSaturDay = view.findViewById(R.id.recyclerView_saturday);
        recyclerViewSunDay = view.findViewById(R.id.recyclerView_sunday);
        recyclerViewMonDay = view.findViewById(R.id.recyclerView_monday);
        recyclerViewTuesDay = view.findViewById(R.id.recyclerView_tuesday);
        recyclerViewWednesDay = view.findViewById(R.id.recyclerView_wednesday);
        recyclerViewThursDay = view.findViewById(R.id.recyclerView_thursday);
        recyclerViewFriDay = view.findViewById(R.id.recyclerView_friday);


        saturdayAdapter = new DayMealPlanAdapter(getContext(),new ArrayList<>() ,this);
        sundayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);
        mondayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);
        tuesdayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);
        wednesdayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);
        thursdayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);
        fridayAdapter = new DayMealPlanAdapter(getContext(), new ArrayList<>(), this);

        //For Saturday
        LinearLayoutManager saturdayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSaturDay.setLayoutManager(saturdayLayoutManager);

        //For Sunday
        LinearLayoutManager sundayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSunDay.setLayoutManager(sundayLayoutManager);

        //For Monday
        LinearLayoutManager mondayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMonDay.setLayoutManager(mondayLayoutManager);

        //For Tuesday
        LinearLayoutManager tuesdayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTuesDay.setLayoutManager(tuesdayLayoutManager);

        //For Wednesday
        LinearLayoutManager wednesdayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWednesDay.setLayoutManager(wednesdayLayoutManager);

        //For Thursday
        LinearLayoutManager thursdayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewThursDay.setLayoutManager(thursdayLayoutManager);

        //For Friday
        LinearLayoutManager fridayLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFriDay.setLayoutManager(fridayLayoutManager);

        recyclerViewSaturDay.setAdapter(saturdayAdapter);

        recyclerViewSunDay.setAdapter(sundayAdapter);

        recyclerViewMonDay.setAdapter(mondayAdapter);

        recyclerViewTuesDay.setAdapter(tuesdayAdapter);

        recyclerViewWednesDay.setAdapter(wednesdayAdapter);

        recyclerViewThursDay.setAdapter(thursdayAdapter);

        recyclerViewFriDay.setAdapter(fridayAdapter);



        mealLocalDataSource = MealLocalDataSourceImpl.getInstance(getContext());

        mealPlanPresenter = new MealPlanPresenterImpl(this,
                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(),
                        MealLocalDataSourceImpl.getInstance(getActivity())));

        showData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showData() {
        Flowable<List<MealPlanObject>> myFavProducts = mealPlanPresenter.getMealsPlan();
        Log.i(TAG, "showData: " + myFavProducts);
        myFavProducts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    saturdayAdapter.setMealsPlan(filterMealsByDay(meals, "saturday"));
                    sundayAdapter.setMealsPlan(filterMealsByDay(meals, "sunday"));
                    mondayAdapter.setMealsPlan(filterMealsByDay(meals, "monday"));
                    tuesdayAdapter.setMealsPlan(filterMealsByDay(meals, "tuesday"));
                    wednesdayAdapter.setMealsPlan(filterMealsByDay(meals, "wednesday"));
                    thursdayAdapter.setMealsPlan(filterMealsByDay(meals, "thursday"));
                    fridayAdapter.setMealsPlan(filterMealsByDay(meals, "friday"));
                });
    }
    private List<MealPlanObject> filterMealsByDay(List<MealPlanObject> meals, String day) {
        List<MealPlanObject> filteredMeals = new ArrayList<>();
        for (MealPlanObject meal : meals) {
            if (meal.getDay().equalsIgnoreCase(day)) {
                filteredMeals.add(meal);
            }
        }
        return filteredMeals;
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void removeMeal(MealPlanObject mealPlanObject) {
        mealPlanPresenter.removeFromPlan(mealPlanObject);
    }

    @Override
    public void onMealPlanClick(MealPlanObject mealPlanObject) {
        removeMeal(mealPlanObject);
    }

    @Override
    public void onClickMealForDetails(MealPlanObject mealPlanObject) {

    }
}