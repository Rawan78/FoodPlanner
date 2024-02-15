package com.example.foodplanner;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
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
                    finish();
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
        //Log.i(TAG, "onStart: Username "+user.getEmail());
        if (user != null) {
            // If user is logged in, set the user's name in the navigation drawer header
            String userEmail = user.getEmail();
            if (userEmail != null) {
                navigationView.getMenu().findItem(R.id.nav_account).setTitle(userEmail);
            } else {
                // Handle case where user email is null
                Log.e(TAG, "User email is null");
            }
        } else {
            // Handle case where user is not signed in
            Log.e(TAG, "User is not signed in");
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, mealPlanFragment)
                    .commit();
            return true;
        }
        return false;
    }
}