package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SearchFragment extends Fragment {
    private String[] searchCategories = {"Search By Area", "Search By Category", "Search By Ingredients", "Search By Meal Name"};

    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ChipGroup chipGroup = view.findViewById(R.id.chipGroupOfSearch);

        for (String category : searchCategories) {
            Chip chip = new Chip(getContext());
            chip.setText(category);
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked && category.equals("Search By Meal Name")) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }else if(isChecked && category.equals("Search By Category")){
                    Intent intent = new Intent(getActivity(), SearchByCategoryActivity.class);
                    startActivity(intent);
                }else if(isChecked && category.equals("Search By Area")){
                    Intent intent = new Intent(getActivity(), SearchByAreaActivity.class);
                    startActivity(intent);
                }else if(isChecked && category.equals("Search By Ingredients")){
                    Intent intent = new Intent(getActivity(), SearchMealsByIngredientActivity.class);
                    startActivity(intent);
                }
            });
            chipGroup.addView(chip);
        }

        return view;
    }
}