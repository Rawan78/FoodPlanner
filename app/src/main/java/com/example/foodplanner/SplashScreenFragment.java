package com.example.foodplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class SplashScreenFragment extends Fragment {
    private static final int SPLASH_TIME_OUT = 7000;

    public SplashScreenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        // Handler to delay navigation to the main content fragment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Replace the splash fragment with your main content fragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, new StartScreenFragment())
                        .commit();
            }
        }, SPLASH_TIME_OUT);

        return view;
    }
}