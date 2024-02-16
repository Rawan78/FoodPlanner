package com.example.foodplanner;

import com.example.foodplanner.model.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.db.*;
import com.example.foodplanner.network.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouriteMealActivity extends AppCompatActivity implements OnFavouriteClickListener, FavMealsView{
    RecyclerView recyclerViewForFavouriteMeals;
    LinearLayoutManager linearLayoutManager;
    FavouriteMealsAdapter favouriteMealsAdapter;
    MealLocalDataSourceImpl mealLocalDataSource;
    FavouriteMealPresenter favouriteMealPresenter;
    private static final String TAG = "FavouriteMealActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_meal);

        recyclerViewForFavouriteMeals = findViewById(R.id.recylerViewFavourite);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewForFavouriteMeals.setLayoutManager(linearLayoutManager);
        favouriteMealsAdapter = new FavouriteMealsAdapter(this, new ArrayList<Meal>(), this);


        mealLocalDataSource = MealLocalDataSourceImpl.getInstance(this);

        favouriteMealPresenter = new FavouriteMealPresenterImpl(this,
                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                        MealLocalDataSourceImpl.getInstance(this)));

        recyclerViewForFavouriteMeals.setAdapter(favouriteMealsAdapter);
        showData();
    }

    @Override
    public void showData() {
        Flowable<List<Meal>> myFavProducts = favouriteMealPresenter.getMeals();
        myFavProducts.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    favouriteMealsAdapter.setMeal(meals);
                });
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void removeMeal(Meal meal) {
        favouriteMealPresenter.removeFromFav(meal);
        removeMealFromFirebase(meal);
    }

    @Override
    public void showCategories() {

    }

    @Override
    public void onFavMealClick(Meal meal) {
        removeMeal(meal);
        Toast.makeText(this, "Removed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMealForDetails(Meal meal) {

    }

    public void removeMealFromFirebase(Meal meal){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            FirebaseDatabase.getInstance().getReference("users")
                    .child(userId)
                    .child("favorites")
                    .child(meal.getIdMeal())
                    .removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Meal removed from Firebase successfully");
                            } else {
                                Log.e(TAG, "Failed to remove meal from Firebase: " + task.getException().getMessage());
                            }
                        }
                    });
        }
    }
}