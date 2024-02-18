package com.example.foodplanner.Meals.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.MealPlan.view.*;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.model.*;
import com.example.foodplanner.Meals.presenter.*;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealDetailsActivity extends AppCompatActivity implements OnMealPlanClickListener , AllMealsView{
    AllMealsPresenter allMealsPresenter;
    Button addToPlan_btn;
    private static final String TAG = "MealDetailsActivity";
    ImageView mealImage;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    TextView mealDrink;
    TextView mealInstructions;
    TextView mealIngredient1;
    TextView mealIngredient2;
    TextView mealIngredient3;
    TextView mealIngredient4;
    TextView mealIngredient5;
    TextView mealIngredient6;
    TextView mealIngredient7;
    TextView mealIngredient8;
    TextView mealIngredient9;
    TextView mealIngredient10;
    TextView mealIngredient11;
    TextView mealIngredient12;
    TextView mealIngredient13;
    TextView mealIngredient14;
    TextView mealIngredient15;
    TextView mealIngredient16;
    TextView mealIngredient17;
    TextView mealIngredient18;
    TextView mealIngredient19;
    TextView mealIngredient20;
    TextView mealYoutube;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));

        addToPlan_btn = findViewById(R.id.addToPlanBtn);

        mealImage = findViewById(R.id.iv_strMealThumb);
        mealName = findViewById(R.id.tv_strMeal);
        mealArea = findViewById(R.id.tv_strArea);
        mealCategory =findViewById(R.id.tv_strCategory);
        mealDrink = findViewById(R.id.tv_strDrinkAlternate);
        mealInstructions = findViewById(R.id.tv_strInstructions);
        mealIngredient1 = findViewById(R.id.tv_strIngredient1);
        mealIngredient2 = findViewById(R.id.tv_strIngredient2);
        mealIngredient3 = findViewById(R.id.tv_strIngredient3);
        mealIngredient4 = findViewById(R.id.tv_strIngredient4);
        mealIngredient5 = findViewById(R.id.tv_strIngredient5);
        mealIngredient6 = findViewById(R.id.tv_strIngredient6);
        mealIngredient7 = findViewById(R.id.tv_strIngredient7);
        mealIngredient8 = findViewById(R.id.tv_strIngredient8);
        mealIngredient9 = findViewById(R.id.tv_strIngredient9);
        mealIngredient10 = findViewById(R.id.tv_strIngredient10);
        mealIngredient11 = findViewById(R.id.tv_strIngredient11);
        mealIngredient12 = findViewById(R.id.tv_strIngredient12);
        mealIngredient13 = findViewById(R.id.tv_strIngredient13);
        mealIngredient14 = findViewById(R.id.tv_strIngredient14);
        mealIngredient15 = findViewById(R.id.tv_strIngredient15);
        mealIngredient16 = findViewById(R.id.tv_strIngredient16);
        mealIngredient17 = findViewById(R.id.tv_strIngredient17);
        mealIngredient18 = findViewById(R.id.tv_strIngredient18);
        mealIngredient19 = findViewById(R.id.tv_strIngredient19);
        mealIngredient20 = findViewById(R.id.tv_strIngredient20);
        mealYoutube = findViewById(R.id.tv_strYoutube);

        Intent intent1 = getIntent();
        Log.i(TAG, "daily object" + intent1.hasExtra("mealDetailsObject"));

        Meal randomMeal = (Meal) intent1.getSerializableExtra("mealDetailsObject");
        Log.i(TAG, "on card" + randomMeal);
        if (randomMeal != null) {
            setMealDetails(randomMeal);
        }
        MealPlanObject mealPlanObject = new MealPlanObject( "sunday" ,randomMeal.getStrMeal(),randomMeal.getStrMealThumb(), randomMeal.getIdMeal());

        Log.d(TAG, "MealPlanObject created: " + mealPlanObject.toString());

        addToPlan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showDayChooserDialog();
                showDayChooserDialog(randomMeal);
            }
        });
    }
    public void setMealDetails(Meal meal) {
        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        mealDrink.setText(meal.getStrDrinkAlternate());

        if(meal.getStrInstructions() != null) {
            mealInstructions.setText("Instructions     : \n" + meal.getStrInstructions());
        }

        if(meal.getStrIngredient1() != null) {
            mealIngredient1.setText("Ingredient1    :" + meal.getStrIngredient1());
        }

        if(meal.getStrIngredient2() != null) {
            mealIngredient2.setText("Ingredient2    :" + meal.getStrIngredient2());
        }

        if(meal.getStrIngredient3() != null) {
            mealIngredient3.setText("Ingredient3    :" + meal.getStrIngredient3());
        }

        if(meal.getStrIngredient4() != null) {
            mealIngredient4.setText("Ingredient4    :" + meal.getStrIngredient4());
        }

        if(meal.getStrIngredient5() != null) {
            mealIngredient5.setText("Ingredient5    :" + meal.getStrIngredient5());
        }

        if(meal.getStrIngredient6() != null) {
            mealIngredient6.setText("Ingredient6    :" + meal.getStrIngredient6());
        }

        if(meal.getStrIngredient7() != null) {
            mealIngredient7.setText("Ingredient7    :" + meal.getStrIngredient7());
        }

        if(meal.getStrIngredient8() != null) {
            mealIngredient8.setText("Ingredient8    :" + meal.getStrIngredient8());
        }

        if(meal.getStrIngredient9() != null) {
            mealIngredient9.setText("Ingredient9    :" + meal.getStrIngredient9());
        }

        if(meal.getStrIngredient10() != null) {
            mealIngredient10.setText("Ingredient10    :" + meal.getStrIngredient10());
        }

        if(meal.getStrIngredient11() != null) {
            mealIngredient11.setText("Ingredient11    :" + meal.getStrIngredient11());
        }

        if(meal.getStrIngredient12() != null) {
            mealIngredient12.setText("Ingredient12    :" + meal.getStrIngredient12());
        }

        if(meal.getStrIngredient13() != null) {
            mealIngredient13.setText("Ingredient13    :" + meal.getStrIngredient13());
        }

        if(meal.getStrIngredient14() != null) {
            mealIngredient14.setText("Ingredient14    :" + meal.getStrIngredient14());
        }

        if(meal.getStrIngredient15() != null) {
            mealIngredient15.setText("Ingredient15    :" + meal.getStrIngredient15());
        }

        if(meal.getStrIngredient16() != null) {
            mealIngredient16.setText("Ingredient16    :" + meal.getStrIngredient16());
        }

        if(meal.getStrIngredient17() != null) {
            mealIngredient17.setText("Ingredient17    :" + meal.getStrIngredient17());
        }

        if(meal.getStrIngredient18() != null) {
            mealIngredient18.setText("Ingredient18    :" + meal.getStrIngredient18());
        }

        if(meal.getStrIngredient19() != null) {
            mealIngredient19.setText("Ingredient19    :" + meal.getStrIngredient19());
        }

        if(meal.getStrIngredient20() != null) {
            mealIngredient20.setText("Ingredient20    :" + meal.getStrIngredient20());
        }

        if(meal.getStrYoutube() != null) {
            mealYoutube.setText("Youtube    \n" + meal.getStrYoutube());
        }
    }

    // Method to show the day chooser dialog
    private void showDayChooserDialog(final Meal meal) {
        final List<String> daysOfWeek = new ArrayList<>(Arrays.asList(
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        ));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Day")
                .setItems(daysOfWeek.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedDay = daysOfWeek.get(which);
                        Toast.makeText(MealDetailsActivity.this, "Selected day: " + selectedDay, Toast.LENGTH_SHORT).show();
                        MealPlanObject mealPlanObject = new MealPlanObject(selectedDay, meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal());
                        onMealPlanClick(mealPlanObject); // Pass the mealPlanObject to the click listener
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onMealPlanClick(MealPlanObject mealPlanObject) {
        addMealToPlan(mealPlanObject);
    }

    @Override
    public void onClickMealForDetails(MealPlanObject mealPlanObject) {
        //No Need For This
    }

    @Override
    public void showData(List<Meal> meals) {

    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void showCategories(List<Category> categories) {

    }

    @Override
    public void showAreas(List<Area> areas) {

    }

    @Override
    public void addMealToPlan(MealPlanObject mealPlanObject) {
        allMealsPresenter.addToPlan(mealPlanObject);
        addMealOfPlanToFirebase(mealPlanObject);
    }
    private void addMealOfPlanToFirebase(MealPlanObject mealPlanObject) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String mealId = mealPlanObject.getIdMeal(); // Assuming getIdMeal() returns the mealId

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("plan")
                    .child(mealId); // Use mealId as the key

            favoritesRef.setValue(mealPlanObject)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Meal added to plan");
                        //Toast.makeText(getContext(), "Added To Plan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding meal to plan", e);
                        //Toast.makeText(getContext(), "Failed to add to Plan", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}