package com.team8.universitybazaar.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityRegisterUserBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.User;

public class RegisterUser extends AppCompatActivity {

    private Validations validations;
    private DatabaseHelper databaseHelper;

    ActivityRegisterUserBinding activityRegisterUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterUserBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        View view = activityRegisterUserBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Register");

        databaseHelper = new DatabaseHelper(this);

        activityRegisterUserBinding.btnRegister.setOnClickListener(v -> {

            final User user = new User();

            user.setUserName(activityRegisterUserBinding.etUsername.getText().toString().trim());
            user.setPassword(activityRegisterUserBinding.etPassword.getText().toString().trim());
            user.setFirstName(activityRegisterUserBinding.etFirstName.getText().toString().trim());
            user.setLastName(activityRegisterUserBinding.etLastName.getText().toString().trim());
            user.setPhone(activityRegisterUserBinding.etPhone.getText().toString().trim());
            user.setEmail(activityRegisterUserBinding.etEmail.getText().toString().trim());
            user.setStAddress(activityRegisterUserBinding.etStrAddress.getText().toString().trim());
            user.setCity(activityRegisterUserBinding.etCity.getText().toString().trim());
            user.setState(activityRegisterUserBinding.etState.getText().toString().trim());
            user.setZipCode(activityRegisterUserBinding.etZipCode.getText().toString().trim());

            databaseHelper.addUser(user);

            Intent i = new Intent(RegisterUser.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
    }

    private boolean isValid() {
        if (validations.isBlank(activityRegisterUserBinding.etUsername)) {
            activityRegisterUserBinding.etUsername.setError("Please enter a username");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etPassword)) {
            activityRegisterUserBinding.etPassword.setError("Enter a password");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etFirstName)) {
            activityRegisterUserBinding.etFirstName.setError("Please enter your first name");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etLastName)) {
            activityRegisterUserBinding.etLastName.setError("Please enter your last name");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etPhone)) {
            activityRegisterUserBinding.etPhone.setError("Please enter a phone number");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etEmail)) {
            activityRegisterUserBinding.etEmail.setError("Please enter an email address");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etStrAddress)) {
            activityRegisterUserBinding.etStrAddress.setError("Please enter a street address");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etCity)) {
            activityRegisterUserBinding.etCity.setError("Please enter your City name");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etState)) {
            activityRegisterUserBinding.etState.setError("Please enter your state name");
            return false;
        } else if (validations.isBlank(activityRegisterUserBinding.etZipCode)) {
            activityRegisterUserBinding.etZipCode.setError("Please enter your zip code");
            return false;
        } else if (!validations.isValidEmail(activityRegisterUserBinding.etEmail)) {
            activityRegisterUserBinding.etEmail.setError("Please enter a valid email address");
            return false;
        } else if (validations.isValidPhone(activityRegisterUserBinding.etPhone)) {
            activityRegisterUserBinding.etPhone.setError("Please enter a valid phone number");
            return false;
        } else if (validations.isValidPincode(activityRegisterUserBinding.etZipCode)) {
            activityRegisterUserBinding.etZipCode.setError("Please enter a valid zip code");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}