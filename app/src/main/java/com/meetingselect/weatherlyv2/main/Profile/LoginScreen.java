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
import com.meetingselect.weatherlyv2.R;


public class LoginScreen extends Fragment {

    private static final String TAG = "loginScreen";
    ValidateInput validateInput;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText EmailInput = view.findViewById(R.id.profileLS_emailaddress_editext);
        EditText PasswordInput = view.findViewById(R.id.profileLS_password_editext);
        Button LoginButton = view.findViewById(R.id.profileLS_login_button);

//        validateInput = new ValidateInput(requireActivity(), EmailInput, PasswordInput);

        mAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();

            }
        });
    }



    public void signInUser() {
        EditText EmailInput = requireView().findViewById(R.id.profileLS_emailaddress_editext);
        EditText PasswordInput = requireView().findViewById(R.id.profileLS_password_editext);
        validateInput = new ValidateInput(requireActivity(), EmailInput, PasswordInput);

        Boolean emailVerified = validateInput.validateEmail();
        Boolean passwordVerified = validateInput.validatePassword();


        String email = EmailInput.getText().toString().trim();
        String password = PasswordInput.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        if(emailVerified && passwordVerified) {
            Log.d(TAG, "signInUser: " +  email + password);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Navigation.findNavController(requireView()).navigate(R.id.action_loginScreen_to_profile);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(requireActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}