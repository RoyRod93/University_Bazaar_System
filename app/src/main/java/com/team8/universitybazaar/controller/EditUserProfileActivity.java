package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
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

        activityEditUserProfileBinding.tvUsernameVal.setText(loggedUser.getUserName());
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

            boolean updateUserProfileResult = updateUserProfile();

            if (updateUserProfileResult) {
                Toast.makeText(this, "Profile Saved to Database!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditUserProfileActivity.this, MainActivity.class);
                i.putExtra("userName", loggedUser.getUserName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Toast.makeText(this, "Profile Update Error !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean updateUserProfile() {
        loggedUser.setPassword(activityEditUserProfileBinding.etPasswordVal.getText().toString());
        loggedUser.setFirstName(activityEditUserProfileBinding.etFNameVal.getText().toString());
        loggedUser.setLastName(activityEditUserProfileBinding.etLNameVal.getText().toString());
        loggedUser.setPhone(activityEditUserProfileBinding.etPhoneVal.getText().toString());
        loggedUser.setEmail(activityEditUserProfileBinding.etLEmailVal.getText().toString());
        loggedUser.setStAddress(activityEditUserProfileBinding.etStreetVal.getText().toString());
        loggedUser.setCity(activityEditUserProfileBinding.etCityVal.getText().toString());
        loggedUser.setState(activityEditUserProfileBinding.etStateVal.getText().toString());
        loggedUser.setZipCode(activityEditUserProfileBinding.etZipVal.getText().toString());

        boolean result = databaseHelper.updateUser(loggedUser);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout_only, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("isUserLogin");
            editor.remove("userName");
            editor.apply();

            Intent i = new Intent(EditUserProfileActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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
