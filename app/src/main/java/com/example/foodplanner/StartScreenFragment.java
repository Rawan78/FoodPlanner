package com.example.foodplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StartScreenFragment extends Fragment {
    //NavController navController;
    Button btnSignUp;
    TextView btnLogin;

    public StartScreenFragment() {
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
        View view = inflater.inflate(R.layout.fragment_start_screen, container, false);
        btnSignUp = view.findViewById(R.id.signUpButton); // Update ID to signUpButton
        btnLogin = view.findViewById(R.id.logInButton);
//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_startScreenFragment_to_signUpFragment);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_startScreenFragment_to_loginFragment);
                        requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}