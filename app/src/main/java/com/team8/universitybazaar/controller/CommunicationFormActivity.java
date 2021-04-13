package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityCommunicationFormBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class CommunicationFormActivity extends AppCompatActivity {

    private static final String TAG = CommunicationFormActivity.class.getSimpleName();

    ActivityCommunicationFormBinding activityCommunicationFormBinding;
    private Validations validations;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCommunicationFormBinding = ActivityCommunicationFormBinding.inflate(getLayoutInflater());
        View view = activityCommunicationFormBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Send Message");

        databaseHelper = new DatabaseHelper(this);
        validations = new Validations();


        loggedInUser = (User) getIntent().getSerializableExtra("logged-user");

        activityCommunicationFormBinding.btnSendMessage.setOnClickListener(v -> {
            String tos =  activityCommunicationFormBinding.etMsgTo.getText().toString().trim();
            String[] usernames = tos.split(",");
            String message = activityCommunicationFormBinding.etYourMsg.getText().toString();
            String from  = loggedInUser.getUserName();

            if (isValid()) {
                for(String to : usernames){
                    databaseHelper.sendMessage(from,to,message);
                }
                Toast.makeText(this, "Message sent  successfully...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CommunicationFormActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
                startActivity(i);
            } else {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValid() {
        if (validations.isBlank(activityCommunicationFormBinding.etMsgTo)) {
            activityCommunicationFormBinding.etMsgTo.setError("Please enter valid username");
            return false;
        } else if (validations.isBlank(activityCommunicationFormBinding.etYourMsg)) {
            activityCommunicationFormBinding.etYourMsg.setError("Message is blank");
            return false;
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

            Intent i = new Intent(CommunicationFormActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(CommunicationFormActivity.this, ViewUserProfileActivity.class);
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

    private void changeEditTextAvailability(EditText editText, boolean status) {
        editText.setEnabled(status);
        editText.setCursorVisible(status);
        editText.setFocusableInTouchMode(status);
    }
}