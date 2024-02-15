package com.example.foodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {
    EditText et_email ;
    EditText et_Password;
    EditText et_ConfirmPassword;
    Button btnSignUp;
    FirebaseAuth mAuth;
    ProgressBar progBar;
    ImageView btnSignUpGoogle;
    private static final String TAG = "SignUpFragment";
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Navigate to Home
//            requireActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment, new HomeFragment())
//                    .addToBackStack(null)
//                    .commit();
        }
    }
    public SignUpFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mAuth = FirebaseAuth.getInstance();

        et_email = view.findViewById(R.id.email_in_sign_et);
        et_Password = view.findViewById(R.id.password_in_sign_et);
        et_ConfirmPassword = view.findViewById(R.id.confirm_Password_in_sign_et);
        btnSignUp = view.findViewById(R.id.signUp_btn);
        progBar = view.findViewById(R.id.progressBar);

        btnSignUpGoogle = view.findViewById(R.id.signUpGoogle_btn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progBar.setVisibility(View.VISIBLE);
                String email ;
                String password ;
                String confirmPassword;

                email = et_email.getText().toString();
                password = et_Password.getText().toString();
                confirmPassword = et_ConfirmPassword.getText().toString();


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
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(getContext(), "Confirm your Password", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (!email.endsWith("@gmail.com")) {
                    // Email does not end with "@gmail.com"
                    et_email.setError("Email must end with @gmail.com");
                    et_email.requestFocus();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (password.length() < 7) {
                    // Password length less than 7 characters
                    et_Password.setError("Password must be at least 7 characters");
                    et_Password.requestFocus();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    // Password and confirm password do not match
                    et_ConfirmPassword.setError("Passwords do not match");
                    et_ConfirmPassword.requestFocus();
                    progBar.setVisibility(View.INVISIBLE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progBar.setVisibility(View.INVISIBLE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(getContext(), "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    requireActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.nav_host_fragment, new LoginFragment())
                                            .addToBackStack(null)
                                            .commit();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    progBar.setVisibility(View.INVISIBLE);
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}