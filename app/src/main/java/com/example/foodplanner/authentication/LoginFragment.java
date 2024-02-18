package com.example.foodplanner.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodplanner.start.HomeActivity;
import com.example.foodplanner.Meals.presenter.AllMealsPresenter;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    EditText et_email ;
    EditText et_Password;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressBar progBar;
    CheckBox cb_rememberMe;
    SharedPreferences preferences;
    AllMealsPresenter allMealsPresenter;
    public static final String PREFRENCE_FILE = "PREFRENCE_FILE";
    private static final String TAG = "LoginFragment";

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//           //Navigate to Home
//            Intent intent = new Intent(getActivity(), HomeActivity.class);
//            startActivity(intent);
//        }
        preferences = getActivity().getSharedPreferences(PREFRENCE_FILE, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // If user is already logged in, navigate to HomeActivity

            //String userId = mAuth.getCurrentUser().getUid();
            //allMealsPresenter.getFavouriteMealsFromFirebase(userId, new ArrayList<>());


            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        et_email = view.findViewById(R.id.email_et);
        et_Password = view.findViewById(R.id.password_et);
        btnLogin = view.findViewById(R.id.loginBtn);
        progBar = view.findViewById(R.id.progressBarInLogin);
        cb_rememberMe = view.findViewById(R.id.rememberMeCheckBox);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progBar.setVisibility(View.VISIBLE);
                String email ;
                String password ;

                email = et_email.getText().toString();
                password = et_Password.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    //Toast.makeText(getContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                                    if (cb_rememberMe.isChecked()) {
                                        saveLoginStatus(true);
                                    }

                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    progBar.setVisibility(View.INVISIBLE);
//                                    requireActivity().getSupportFragmentManager().beginTransaction()
//                                            .replace(R.id.nav_host_fragment, new HomeFragment())
//                                            .addToBackStack(null)
//                                            .commit();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    progBar.setVisibility(View.INVISIBLE);
                                    //if (task.getException().getMessage().contains("no user record")) {
                                        // User not found, set error message to the email EditText
                                        et_email.setError("Invalid email or password");
                                        et_email.requestFocus();
                                    //}
                                }
                            }
                        });
            }
        });

        return view;
    }
    private void saveLoginStatus(boolean isLoggedIn) {
        preferences = getActivity().getSharedPreferences(PREFRENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}