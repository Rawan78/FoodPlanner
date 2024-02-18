package com.example.foodplanner.guest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.Area.AreaAdapter;
import com.example.foodplanner.Category.CategoryAdapter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.Meals.view.CategoriesAdapter;
import com.example.foodplanner.R;

public class HomeGuestFragment extends Fragment {
    RecyclerView recyclerViewArea;
    AreaAdapter areaAdapter;
    RecyclerView recyclerViewRanomMeals;
    LinearLayoutManager linearLayoutManager;
    CategoriesAdapter categoriesAdapter;
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerViewCategory;
    AllMealsPresenter allMealsPresenter;

    public HomeGuestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home_guest, container, false);



        return view;
    }
}