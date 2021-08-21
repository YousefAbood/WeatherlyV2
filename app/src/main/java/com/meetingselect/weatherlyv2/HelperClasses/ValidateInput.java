package com.meetingselect.weatherlyv2.HelperClasses;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {

    private Context mContext;

    private EditText mEmail, mPassword, mRepeatPassword;

    public ValidateInput(Context mContext, EditText mEmail, EditText mPassword) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
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
}
