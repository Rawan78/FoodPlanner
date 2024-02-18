package com.example.foodplanner.start;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.Area.AreaAdapter;
import com.example.foodplanner.Category.CategoryAdapter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.Meals.view.CategoriesAdapter;
import com.example.foodplanner.Meals.view.OnFavouriteClickListener;
import com.example.foodplanner.R;
import com.example.foodplanner.model.*;
import com.example.foodplanner.network.*;
import com.example.foodplanner.db.*;


import com.example.foodplanner.model.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnFavouriteClickListener, AllMealsView {

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
        checkIfMealExistsInFavorites(meal);
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

    @Override
    public void addMealToPlan(MealPlanObject mealPlanObject) {

    }


    private void checkIfMealExistsInFavorites(Meal meal) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("favorites");

            favoritesRef.orderByChild("name").equalTo(meal.getStrMeal()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(getContext(), "Meal already added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        addMealToFirebase(meal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Error checking if meal exists in favorites", databaseError.toException());
                    Toast.makeText(getContext(), "Failed to check if meal exists in favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addMealToFirebase(Meal meal) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String mealId = meal.getIdMeal(); // Assuming getIdMeal() returns the mealId

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("favorites")
                    .child(mealId); // Use mealId as the key

            favoritesRef.setValue(meal)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Meal added to favorites");
                        Toast.makeText(getContext(), "Added To Favourite", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding meal to favorites", e);
                        Toast.makeText(getContext(), "Failed to add to Favourite", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}