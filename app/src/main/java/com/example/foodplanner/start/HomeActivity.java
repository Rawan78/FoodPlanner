package com.example.foodplanner.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodplanner.MealPlan.presenter.*;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.MealPlan.MealPlanFragment;
import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.Meals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Meals.view.AllMealsView;
import com.example.foodplanner.R;
import com.example.foodplanner.Search.SearchFragment;
import com.example.foodplanner.authentication.LoginFragment;
import com.example.foodplanner.db.MealLocalDataSourceImpl;
import com.example.foodplanner.favMeals.presenter.FavouriteMealPresenter;
import com.example.foodplanner.favMeals.presenter.FavouriteMealPresenterImpl;
import com.example.foodplanner.favMeals.view.FavMealsView;
import com.example.foodplanner.favMeals.view.FavouriteMealActivity;
import com.example.foodplanner.model.Area;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealPlanObject;
import com.example.foodplanner.model.MealRepositoryImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , AllMealsView , FavMealsView {
    AllMealsPresenter allMealsPresenter;
    MealPlanPresenter mealPlanPresenter;
    FavouriteMealPresenter favouriteMealPresenter;
    public static boolean isNotUserByEmail = false ;
    public static boolean isNotGoogleAccount = false;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount account;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    MealPlanFragment mealPlanFragment = new MealPlanFragment();
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "HomeActivity";
    String PREFRENCE_FILE = LoginFragment.PREFRENCE_FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Presenter
        allMealsPresenter = new AllMealsPresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                MealLocalDataSourceImpl.getInstance(this)));

        favouriteMealPresenter = new FavouriteMealPresenterImpl(this,
                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
                        MealLocalDataSourceImpl.getInstance(this)));

//        mealPlanPresenter = new MealPlanPresenterImpl(this,
//                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance() ,
//                        MealLocalDataSourceImpl.getInstance(this)));

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        account = GoogleSignIn.getLastSignedInAccount(this);


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeItem);

        //Logout and move to mainActivity
        navigationView = findViewById(R.id.my_navigation_view);
        //firebaseAuth = FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(HomeActivity.this, "Clicked To Logout", Toast.LENGTH_SHORT).show();

                    signOut();

                    deleteFavMealsFromRoom();

                    deleteMealsPlanFromRoom();

                    FirebaseAuth.getInstance().signOut();
                    sharedPreferences.edit().clear().apply();
                    Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.putExtra("navigate_to_login_fragment", true);
                    startActivity(intent);
                    finish();
                    return true;
                }else if(item.getItemId() == R.id.nav_favourite){
                    Toast.makeText(HomeActivity.this, "Clicked To Favourite", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, FavouriteMealActivity.class);
                    startActivity(intent);
                    //finish();
                }
                return false;
            }
        });
        sharedPreferences = getSharedPreferences(PREFRENCE_FILE, MODE_PRIVATE);

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            showFavMealsFromFirebase(user);
            showMealsPlanFromFirebase(user);
            // If user is logged in, set the user's name in the navigation drawer header
            String userEmail = user.getEmail();
            if (userEmail != null) {
                navigationView.getMenu().findItem(R.id.nav_account).setTitle(userEmail);

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
                Log.e(TAG, "User email is null");
            }
        } else {
            // Handle case where user is not signed in
            Log.e(TAG, "User is not signed in");
            isNotUserByEmail = true;
        }
        if(account != null){
            String personNameOfGoogle = account.getDisplayName();
            String personEmailOfGoogle = account.getEmail();
            if (personEmailOfGoogle != null) {
                navigationView.getMenu().findItem(R.id.nav_account).setTitle(personEmailOfGoogle);

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            } else {
                Log.e(TAG, "Google account email is null");
            }
        }else {
            isNotGoogleAccount = true;
        }

        if(user==null && account==null){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, searchFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.homeItem) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.mealPlan) {
           // if (isNotUserByEmail && isNotGoogleAccount) { // Check if the user is not a guest

                // Show a toast message indicating that guests cannot access th
               // Toast.makeText(this, "You cannot access Meal Plan \n If You want to access it , Please Login", Toast.LENGTH_SHORT).show();
               // return false;

           // } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, mealPlanFragment)
                        .commit();
                return true;
           // }
        }
        return true;
    }

    //Sign out From Google Account
    public void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    private void showFavMealsFromFirebase(FirebaseUser user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = currentUser;
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("favorites");
            favoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Meal meal = dataSnapshot.getValue(Meal.class);
                        allMealsPresenter.addToFav(meal);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private void deleteFavMealsFromRoom() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("favorites");
            favoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Meal meal = dataSnapshot.getValue(Meal.class);
                        favouriteMealPresenter.deleteAllMealsFromRoom();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    private void showMealsPlanFromFirebase(FirebaseUser user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = currentUser;
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("plan");
            favoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        MealPlanObject mealPlanObject = dataSnapshot.getValue(MealPlanObject.class);
                        allMealsPresenter.addToPlan(mealPlanObject);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void deleteMealsPlanFromRoom() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("plan");
            favoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        MealPlanObject mealPlanObject = dataSnapshot.getValue(MealPlanObject.class);
                        allMealsPresenter.deleteAllMealPlanFromROOM();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    @Override
    public void showData(List<Meal> meals) {

    }

    @Override
    public void showData() {

    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void removeMeal(Meal meal) {

    }

    @Override
    public void showCategories() {

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

    }
}