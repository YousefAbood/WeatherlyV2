package com.meetingselect.weatherlyv2.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInputSignUp {

    private static final String TAG = "ProfileSignUp";


    private Context mContext;

    private EditText mEmail, mPassword, mRepeatPassword;

    String emailInput, passwordInput, repeatPasswordInput;

    public ValidateInputSignUp(Context mContext, EditText mEmail, EditText mPassword, EditText mRepeatPassword) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mRepeatPassword = mRepeatPassword;
    }

    public boolean validateEmail() {

        String emailInput = mEmail.getText().toString().trim();

        if(emailInput.isEmpty()) {
            Toast.makeText(mContext, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(mContext, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword() {

        String passwordInput = mPassword.getText().toString().trim();

        if(passwordInput.isEmpty()) {
            Toast.makeText(mContext, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        } else if(passwordInput.length() < 8) {
            Toast.makeText(mContext, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean repeatPasswordValidation() {

        repeatPasswordInput = mRepeatPassword.getText().toString().trim();
        passwordInput = mPassword.getText().toString().trim();

        if(repeatPasswordInput.isEmpty()) {
            Toast.makeText(mContext, "Fill out all the fields", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "repeatPasswordValidation: " + repeatPasswordInput + mRepeatPassword);
            return false;
        } else if(!repeatPasswordInput.equals(passwordInput)) {
            Log.d(TAG, "repeatPasswordValidation: ");
            Toast.makeText(mContext, "Passwords don't match", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "repeatPasswordValidation: " + repeatPasswordInput + passwordInput);
            return false;
        } else {
            return true;
        }
    }
}
