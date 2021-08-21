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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.meetingselect.weatherlyv2.R;

public class Profile extends Fragment {

    private static final String TAG = "ProfileFragment";
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button LoginButton = view.findViewById(R.id.profile_loginin_button);
        Button SignUpButton = view.findViewById(R.id.profile_signup_button);
        TextView LoginStatus = view.findViewById(R.id.profile_loginstatus_string);
        TextView EmailAddressString = view.findViewById(R.id.profile_emailaddressstring_string);
        TextView OrString = view.findViewById(R.id.profile_orstring_string);
        TextView EmailAddress = view.findViewById(R.id.profile_actualemailaddress_string);
        TextView LogOut = view.findViewById(R.id.profile_logout_string);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            Log.d(TAG, "onViewCreated: " + currentUser);
            LoginStatus.setText("You are Logged In, Welcome!");
            LoginButton.setVisibility(View.GONE);
            SignUpButton.setVisibility(View.GONE);
            OrString.setVisibility(View.GONE);
            EmailAddress.setVisibility(View.VISIBLE);
            EmailAddress.setText((currentUser.getEmail()));
            EmailAddressString.setVisibility(View.VISIBLE);
            LogOut.setVisibility(View.VISIBLE);

        } else {
            LoginStatus.setText("You are not Logged In");
            LoginButton.setVisibility(View.VISIBLE);
            SignUpButton.setVisibility(View.VISIBLE);
            OrString.setVisibility(View.VISIBLE);
            EmailAddress.setVisibility(View.GONE);
            EmailAddressString.setVisibility(View.GONE);
            LogOut.setVisibility(View.GONE);

        }

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(requireView()).navigate(R.id.action_profile_self);
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_profile_to_loginScreen);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_profile_to_signUpScreen);

            }
        });
    }

}