package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityViewUserProfileBinding;
import com.team8.universitybazaar.model.User;

public class ViewUserProfileActivity extends AppCompatActivity {

    ActivityViewUserProfileBinding activityViewUserProfileBinding;
    User loggedUser;
    String username;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewUserProfileBinding = ActivityViewUserProfileBinding.inflate(getLayoutInflater());
        View view = activityViewUserProfileBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Your Profile Details");

        databaseHelper = new DatabaseHelper(this);

        username = getIntent().getStringExtra("loggedUsernameKey");

        loggedUser = databaseHelper.getDetails(username);

        activityViewUserProfileBinding.tvUsernameDbVal.setText(loggedUser.getUserName());
        activityViewUserProfileBinding.tvPasswordDbVal.setText(loggedUser.getPassword());
        activityViewUserProfileBinding.tvFNameDbVal.setText(loggedUser.getFirstName());
        activityViewUserProfileBinding.tvLNameDbVal.setText(loggedUser.getLastName());
        activityViewUserProfileBinding.tvPhoneDbVal.setText(loggedUser.getPhone());
        activityViewUserProfileBinding.tvLEmailDbVal.setText(loggedUser.getEmail());
        activityViewUserProfileBinding.tvStreetDbVal.setText(loggedUser.getStAddress());
        activityViewUserProfileBinding.tvCityDbVal.setText(loggedUser.getCity());
        activityViewUserProfileBinding.tvStateDbVal.setText(loggedUser.getState());
        activityViewUserProfileBinding.tvZipDbVal.setText(loggedUser.getZipCode());

        activityViewUserProfileBinding.btnEditProfile.setOnClickListener(v -> {

            Intent i = new Intent(ViewUserProfileActivity.this, EditUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedUser.getUserName());
            startActivity(i);

        });
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

            Intent i = new Intent(ViewUserProfileActivity.this, LoginActivity.class);
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
