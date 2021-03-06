package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityCommunicationsOptionsScreenBinding;
import com.team8.universitybazaar.model.User;

public class CommunicationOptionsActivity extends AppCompatActivity {

    ActivityCommunicationsOptionsScreenBinding activityCommunicationsOptionsScreenBinding;
    User loggedInUser;
    String userName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCommunicationsOptionsScreenBinding = ActivityCommunicationsOptionsScreenBinding.inflate(getLayoutInflater());
        View view = activityCommunicationsOptionsScreenBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Communications");

        databaseHelper = new DatabaseHelper(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra("logged-user")) {
                loggedInUser = (User) getIntent().getSerializableExtra("logged-user");
            } else if (getIntent().hasExtra("userName")) {
                userName = getIntent().getStringExtra("userName");
                loggedInUser = databaseHelper.getDetails(userName);
            }
        } else {
            Intent i = new Intent(CommunicationOptionsActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        activityCommunicationsOptionsScreenBinding.btnNewMsg.setOnClickListener(v -> {
            Intent i = new Intent(CommunicationOptionsActivity.this, CommunicationFormActivity.class);
            i.putExtra("logged-user", loggedInUser);
            startActivity(i);
        });
        activityCommunicationsOptionsScreenBinding.btnRcvdMsgs.setOnClickListener(v -> {
            Intent i = new Intent(CommunicationOptionsActivity.this, MyMessagesRecycler.class);
            i.putExtra("logged-user", loggedInUser);
            startActivity(i);
        });
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

            Intent i = new Intent(CommunicationOptionsActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(CommunicationOptionsActivity.this, ViewUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedInUser.getUserName());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(CommunicationOptionsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("logged-user", loggedInUser);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
