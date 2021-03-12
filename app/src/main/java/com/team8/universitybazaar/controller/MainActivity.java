package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityMainBinding;
import com.team8.universitybazaar.model.User;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    User loggedInUser;
    String userName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Page");

        databaseHelper = new DatabaseHelper(this);

        if (getIntent() != null) {

            if (getIntent().hasExtra("logged-user")) {
                loggedInUser = (User) getIntent().getSerializableExtra("logged-user");
            } else if (getIntent().hasExtra("userName")) {
                userName = getIntent().getStringExtra("userName");
                loggedInUser = databaseHelper.getDetails(userName);
            }
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        activityMainBinding.btnSalesExchangeForm.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SalesExchangeForm.class);
            i.putExtra("logged-user", loggedInUser);
            startActivity(i);
        });

        activityMainBinding.btnClubs.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, WipActivity.class);
            startActivity(i);
        });

        activityMainBinding.btnSalesExchange.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SalesItemRecycler.class);
            i.putExtra("logged-user", loggedInUser);
            startActivity(i);
        });

        activityMainBinding.btnConfirmation.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, WipActivity.class);
            startActivity(i);
        });


//        activityMainBinding.mainTextView.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(MainActivity.this, ViewUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedInUser.getUserName());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}