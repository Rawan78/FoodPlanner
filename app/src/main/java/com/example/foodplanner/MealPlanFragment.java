package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MealPlanFragment extends Fragment {
    private static final String TAG = "MealPlanFragment";
    private RecyclerView recyclerViewMealPlan;
    private MealPlanAdapter mealPlanAdapter;
    private List<String> daysList;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewMealPlan = view.findViewById(R.id.recyclerViewWeekDays);
        daysList = new ArrayList<>();
        // Populate the list with days (e.g., Monday, Tuesday, etc.)
        daysList.add("Monday");
        daysList.add("Tuesday");
        daysList.add("Wednesday");
        daysList.add("Thursday");
        daysList.add("Friday");
        daysList.add("Saturday");
        daysList.add("Sunday");

        mealPlanAdapter = new MealPlanAdapter(daysList,requireContext());
        recyclerViewMealPlan.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewMealPlan.setAdapter(mealPlanAdapter);
    }

}