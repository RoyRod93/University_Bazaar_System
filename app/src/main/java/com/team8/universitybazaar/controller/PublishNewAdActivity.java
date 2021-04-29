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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityPublishAdFormBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.Advertisement;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class PublishNewAdActivity extends AppCompatActivity {

    ActivityPublishAdFormBinding activityPublishAdFormBinding;
    User loggedInUser;
    String userName;
    private DatabaseHelper databaseHelper;
    private Validations validations;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPublishAdFormBinding = ActivityPublishAdFormBinding.inflate(getLayoutInflater());
        View view = activityPublishAdFormBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Publish Ad Form");

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
            Intent i = new Intent(PublishNewAdActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

//        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                    updateDate();
//            }
//        };

        activityPublishAdFormBinding.btnPublishAd.setOnClickListener(v -> {

            final Advertisement advertisement = new Advertisement();

            advertisement.setAdvTitle(activityPublishAdFormBinding.etAdTitle.getText().toString().trim());
            advertisement.setAdvBodyMsg(activityPublishAdFormBinding.etAdBodyMsg.getText().toString().trim());
            advertisement.setAdvPublisher(activityPublishAdFormBinding.etAdPublisher.getText().toString().trim());
            advertisement.setAdvPublishDate(Calendar.getInstance().getTime().toString());
            advertisement.setAdvExpiryDate(activityPublishAdFormBinding.etAdExpiryDate.getText().toString().trim());

//            String myFormat = "MM/dd/yy";
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

//            activityPublishAdFormBinding.etAdExpiryDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new DatePickerDialog(PublishNewAdActivity.this, dateSetListener,
//                            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                }
//            });

//            activityPublishAdFormBinding.etAdExpiryDate.setText(sdf.format(myCalendar.getTime()));


            if (isValid()) {
                databaseHelper.createAdvertisement(advertisement);
                Toast.makeText(this, "Ad Published Successfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PublishNewAdActivity.this, AdvertisementOptionsActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
                startActivity(i);
            } else {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }

        });


    }
//
//    private void updateDate() {
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
//        activityPublishAdFormBinding.etAdExpiryDate.setText(sdf.format(myCalendar.getTime()));
//    }


    private boolean isValid() {
        if (validations.isBlank(activityPublishAdFormBinding.etAdTitle)) {
            activityPublishAdFormBinding.etAdTitle.setError("Ad Title Missing");
            return false;
        } else if (validations.isBlank(activityPublishAdFormBinding.etAdBodyMsg)) {
            activityPublishAdFormBinding.etAdBodyMsg.setError("Ad Message Missing");
            return false;
        } else if (validations.isBlank(activityPublishAdFormBinding.etAdPublisher)) {
            activityPublishAdFormBinding.etAdPublisher.setError("Ad Publisher Missing");
        }
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

            Intent i = new Intent(PublishNewAdActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(PublishNewAdActivity.this, ViewUserProfileActivity.class);
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
