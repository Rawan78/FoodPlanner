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
import com.example.foodplanner.start.StartScreenFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    WebView mealYoutube;


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
                if(StartScreenFragment.isGuest == true)   {
                    Toast.makeText(MealDetailsActivity.this, "Sorry, You Cannot add to plan \n If you want, You must Login First", Toast.LENGTH_SHORT).show();
                }else {
                    showDayChooserDialog(randomMeal);
                }
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
            mealIngredient1.setText(meal.getStrIngredient1() +"     " + meal.getStrMeasure1());
        }

        if(meal.getStrIngredient2() != null) {
            mealIngredient2.setText(meal.getStrIngredient2()  +"     " + meal.getStrMeasure2());
        }

        if(meal.getStrIngredient3() != null) {
            mealIngredient3.setText( meal.getStrIngredient3() +"     " + meal.getStrMeasure3());
        }

        if(meal.getStrIngredient4() != null) {
            mealIngredient4.setText(meal.getStrIngredient4() +"     " + meal.getStrMeasure4());
        }

        if(meal.getStrIngredient5() != null) {
            mealIngredient5.setText(meal.getStrIngredient5()  +"     " + meal.getStrMeasure5());
        }

        if(meal.getStrIngredient6() != null) {
            mealIngredient6.setText(meal.getStrIngredient6()  +"     " + meal.getStrMeasure6());
        }

        if(meal.getStrIngredient7() != null) {
            mealIngredient7.setText(meal.getStrIngredient7() +"     " + meal.getStrMeasure7());
        }

        if(meal.getStrIngredient8() != null) {
            mealIngredient8.setText(meal.getStrIngredient8() +"     " + meal.getStrMeasure8());
        }

        if(meal.getStrIngredient9() != null) {
            mealIngredient9.setText(meal.getStrIngredient9() +"     " + meal.getStrMeasure9());
        }

        if(meal.getStrIngredient10() != null) {
            mealIngredient10.setText( meal.getStrIngredient10() +"     " + meal.getStrMeasure10());
        }

        if(meal.getStrIngredient11() != null) {
            mealIngredient11.setText(meal.getStrIngredient11() +"     " + meal.getStrMeasure11());
        }

        if(meal.getStrIngredient12() != null) {
            mealIngredient12.setText( meal.getStrIngredient12() +"     " + meal.getStrMeasure12());
        }

        if(meal.getStrIngredient13() != null) {
            mealIngredient13.setText( meal.getStrIngredient13() +"     " + meal.getStrMeasure13());
        }

        if(meal.getStrIngredient14() != null) {
            mealIngredient14.setText(meal.getStrIngredient14() +"     " + meal.getStrMeasure14());
        }

        if(meal.getStrIngredient15() != null) {
            mealIngredient15.setText(meal.getStrIngredient15() +"     " + meal.getStrMeasure15());
        }

        if(meal.getStrIngredient16() != null) {
            mealIngredient16.setText( meal.getStrIngredient16() +"     " + meal.getStrMeasure16());
        }

        if(meal.getStrIngredient17() != null) {
            mealIngredient17.setText( meal.getStrIngredient17() +"     " + meal.getStrMeasure17());
        }

        if(meal.getStrIngredient18() != null) {
            mealIngredient18.setText( meal.getStrIngredient18() +"     " + meal.getStrMeasure18());
        }

        if(meal.getStrIngredient19() != null) {
            mealIngredient19.setText( meal.getStrIngredient19() +"     " + meal.getStrMeasure19());
        }

        if(meal.getStrIngredient20() != null) {
            mealIngredient20.setText( meal.getStrIngredient20() +"     " + meal.getStrMeasure20());
        }

        if(meal.getStrYoutube() != null) {
        setMealVideo(meal.getStrYoutube());
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
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding meal to plan", e);
                    });
        }
    }

    public static String convertToEmbeddedUrl(String youtubeUrl) {
        String videoId = extractVideoId(youtubeUrl);
        return "https://www.youtube.com/embed/" + videoId;
    }

    private static String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String regex = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2Fvideos%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.find()) {
            videoId = matcher.group();
        }

        return videoId;
    }
    public void setMealVideo(String url)
    {
        Log.d("TAG", "setMealVideo: width " +mealYoutube.getX());
        String videoUrl = convertToEmbeddedUrl(url);
        String video="<iframe width='400' height=\"200\" src= '"+videoUrl+"' title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        mealYoutube.getSettings().setJavaScriptEnabled(true);
        mealYoutube.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                adjustIframeWidth(view);
            }
        });
        mealYoutube.loadData(video, " text/html", "utf-8");

    }
    private void adjustIframeWidth(WebView webView) {
        webView.evaluateJavascript("javascript:(function() { " +
                "var iframes = document.getElementsByTagName('iframe');" +
                "for (var i = 0; i < iframes.length; i++) {" +
                "    var iframe = iframes[i];" +
                "    iframe.style.width = '100%';" +
                "}})();", null);
    }
}