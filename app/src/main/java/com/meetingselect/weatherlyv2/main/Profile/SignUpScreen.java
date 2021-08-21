package com.meetingselect.weatherlyv2.main.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.meetingselect.weatherlyv2.HelperClasses.ValidateInput;
import com.meetingselect.weatherlyv2.HelperClasses.ValidateInputSignUp;
import com.meetingselect.weatherlyv2.R;


public class SignUpScreen extends Fragment {

    private static final String TAG = "ProfileSignUp";
    private FirebaseAuth mAuth;
    private EditText EmailInput, PasswordInput, RepeatPasswordInput;
    private String mEmail, mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EmailInput = view.findViewById(R.id.profileSS_emailaddress_editext);
        PasswordInput = view.findViewById(R.id.profileSS_password_editext);
        RepeatPasswordInput = view.findViewById(R.id.profileSS_repeatpassword_editext);
        Button SignUpButton = view.findViewById(R.id.profileSS_signup_button);

        mAuth = FirebaseAuth.getInstance();

        ValidateInputSignUp validateInputSignUp = new ValidateInputSignUp(requireActivity(), EmailInput, PasswordInput, RepeatPasswordInput);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signUpNewUser();

            }
        });


        

    }


    public void signUpNewUser() {

        ValidateInputSignUp validateInputSignUp = new ValidateInputSignUp(requireActivity(), EmailInput, PasswordInput, RepeatPasswordInput);

        Boolean emailVerified = validateInputSignUp.validateEmail();
        Boolean passwordVerified = validateInputSignUp.validatePassword();
        Boolean repeatPasswordVerified = validateInputSignUp.repeatPasswordValidation();


        if(emailVerified && passwordVerified && repeatPasswordVerified) {

            mEmail = EmailInput.getText().toString().trim();
            mPassword = PasswordInput.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "onComplete: Create Account Success");
                                Navigation.findNavController(requireView()).navigate(R.id.action_signUpScreen_to_profile);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(TAG, "onComplete: Create Account Failure");
                                Toast.makeText(requireActivity(), "Fatal Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }
}