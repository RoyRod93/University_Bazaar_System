package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityCreateClubFormBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.Clubs;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class CreateClubFormActivity extends AppCompatActivity {

    ActivityCreateClubFormBinding activityCreateClubFormBinding;
    User loggedInUser;
    String userName;
    private DatabaseHelper databaseHelper;
    private Validations validations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateClubFormBinding = ActivityCreateClubFormBinding.inflate(getLayoutInflater());
        View view = activityCreateClubFormBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Create Club Form");

        databaseHelper = new DatabaseHelper(this);
        validations = new Validations();

        if (getIntent() != null) {
            if (getIntent().hasExtra("logged-user")) {
                loggedInUser = (User) getIntent().getSerializableExtra("logged-user");
            } else if (getIntent().hasExtra("userName")) {
                userName = getIntent().getStringExtra("userName");
                loggedInUser = databaseHelper.getDetails(userName);
            }
        } else {
            Intent i = new Intent(CreateClubFormActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        activityCreateClubFormBinding.btnCreateClub.setOnClickListener(v -> {

            final Clubs clubs = new Clubs();

            clubs.setClubName(activityCreateClubFormBinding.etClubName.getText().toString().trim());
            clubs.setClubCreationDate(Calendar.getInstance().getTime().toString());
            clubs.setClubDescription(activityCreateClubFormBinding.etClubDescription.getText().toString().trim());
            String capacity = activityCreateClubFormBinding.etClubMembersCapacity.getText().toString();

            if (isValid()) {
                clubs.setClubMemberCapacity(Integer.valueOf(capacity));

            } else {
                activityCreateClubFormBinding.etClubMembersCapacity.setError("");
            }


            String ownerUsername = loggedInUser.getUserName();
            clubs.setClubOwner(ownerUsername);

            int selectedRadioButtonId = activityCreateClubFormBinding.rgClubType.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton selectedRB = findViewById(selectedRadioButtonId);
                clubs.setClubType(selectedRB.getText().toString().trim());
            } else {
                clubs.setClubType("Miscellaneous Clubs");
            }

            if (isValid()) {
                databaseHelper.createClub(clubs);
                Toast.makeText(this, "Club Created Successfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CreateClubFormActivity.this, ClubOptionsActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
                startActivity(i);
            } else {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private boolean isValid() {
        if ((validations.isBlank(activityCreateClubFormBinding.etClubName))
                && (validations.isBlank(activityCreateClubFormBinding.etClubDescription))
                && (activityCreateClubFormBinding.etClubMembersCapacity.getText().toString() == "" ||
                activityCreateClubFormBinding.etClubMembersCapacity.getText().toString().isEmpty())) {
            activityCreateClubFormBinding.etClubName.setError("Please enter the Club Name");
            activityCreateClubFormBinding.etClubDescription.setError("Enter Club Description");
            activityCreateClubFormBinding.etClubMembersCapacity.setError("Enter Members Capacity");
            return false;
        } else if (validations.isBlank(activityCreateClubFormBinding.etClubName)) {
            activityCreateClubFormBinding.etClubName.setError("Please enter the Club Name");
            return false;
        } else if (validations.isBlank(activityCreateClubFormBinding.etClubDescription)) {
            activityCreateClubFormBinding.etClubDescription.setError("Enter Club Description");
            return false;
        } else if (activityCreateClubFormBinding.etClubMembersCapacity.getText().toString() == "" ||
                activityCreateClubFormBinding.etClubMembersCapacity.getText().toString().isEmpty()) {
            activityCreateClubFormBinding.etClubMembersCapacity.setError("Enter Members Capacity");
            return false;
        } else
            return true;
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

            Intent i = new Intent(CreateClubFormActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(CreateClubFormActivity.this, ViewUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedInUser.getUserName());
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
