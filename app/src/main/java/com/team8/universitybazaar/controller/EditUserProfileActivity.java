package com.team8.universitybazaar.controller;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityEditUserProfileBinding;
import com.team8.universitybazaar.model.User;

public class EditUserProfileActivity extends AppCompatActivity {

    ActivityEditUserProfileBinding activityEditUserProfileBinding;
    User loggedUser;
    String username;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditUserProfileBinding = ActivityEditUserProfileBinding.inflate(getLayoutInflater());
        View view = activityEditUserProfileBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Edit Your Profile");

        databaseHelper = new DatabaseHelper(this);

        username = getIntent().getStringExtra("loggedUsernameKey");

        loggedUser = databaseHelper.getDetails(username);

        activityEditUserProfileBinding.etUsernameVal.setText(loggedUser.getUserName());
        activityEditUserProfileBinding.etPasswordVal.setText(loggedUser.getPassword());
        activityEditUserProfileBinding.etFNameVal.setText(loggedUser.getFirstName());
        activityEditUserProfileBinding.etLNameVal.setText(loggedUser.getLastName());
        activityEditUserProfileBinding.etPhoneVal.setText(loggedUser.getPhone());
        activityEditUserProfileBinding.etLEmailVal.setText(loggedUser.getEmail());
        activityEditUserProfileBinding.etStreetVal.setText(loggedUser.getStAddress());
        activityEditUserProfileBinding.etCityVal.setText(loggedUser.getCity());
        activityEditUserProfileBinding.etStateVal.setText(loggedUser.getState());
        activityEditUserProfileBinding.etZipVal.setText(loggedUser.getZipCode());

        activityEditUserProfileBinding.btnSaveProfile.setOnClickListener(v -> {

        });

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
