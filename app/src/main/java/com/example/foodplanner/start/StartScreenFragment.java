package com.example.foodplanner.start;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodplanner.guest.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.LoginFragment;
import com.example.foodplanner.authentication.SignUpFragment;
import com.example.foodplanner.start.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class StartScreenFragment extends Fragment {
    //NavController navController;
    Button btnSignUp;
    TextView btnLogin;
    ImageView googleBtn;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    Button guestBtn;
    public static boolean isGuest = false;

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
        googleBtn = view.findViewById(R.id.signUpGoogle_btn);

        guestBtn = view.findViewById(R.id.guestButton);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

//        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.navigate(R.id.action_startScreenFragment_to_signUpFragment);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
                isGuest = false;
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

                        isGuest = false;
            }
        });
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "You Entered As A Guest ", Toast.LENGTH_SHORT).show();
                isGuest = true;
            }
        });

        return view;
    }
    public void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent , 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToHomeActivity();
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void navigateToHomeActivity(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}