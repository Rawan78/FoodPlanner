package com.example.foodplanner.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.foodplanner.start.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodplanner.R;
import com.example.foodplanner.Search.SearchFragment;
import com.example.foodplanner.start.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeGuestActivity extends AppCompatActivity {
    HomeGuestFragment homeGuestFragment = new HomeGuestFragment();
    SearchGuestFragment searchGuestFragment = new SearchGuestFragment();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_guest);

        bottomNavigationView = findViewById(R.id.bottomNavigationViewGuest);
        bottomNavigationView.setSelectedItemId(R.id.homeItem);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragmentGuest, searchGuestFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.homeItem) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragmentGuest, homeGuestFragment)
                    .commit();
            return true;
        }else if(itemId == R.id.mealPlan){
            showAlertDialog();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Sorry, if you want to make plan , you should Login First , Do You Want to Login?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(HomeGuestActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragmentGuest, homeGuestFragment)
                                .commit();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}
