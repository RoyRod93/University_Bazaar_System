package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityClubItemRecyclerBinding;
import com.team8.universitybazaar.databinding.ActivityMyMsgsRecyclerBinding;
import com.team8.universitybazaar.model.Clubs;
import com.team8.universitybazaar.model.Communication;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class MyMessagesRecycler extends AppCompatActivity {
    private User loggedInUser;
    private DatabaseHelper databaseHelper;
    private List<Communication> communicationList;

    ActivityMyMsgsRecyclerBinding activityMyMsgsRecyclerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyMsgsRecyclerBinding = ActivityMyMsgsRecyclerBinding.inflate(getLayoutInflater());
        View view = activityMyMsgsRecyclerBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Received Messages");

        databaseHelper = new DatabaseHelper(this);
        loggedInUser = (User) getIntent().getSerializableExtra("logged-user");

        loadData();
    }

    private void loadData() {

        communicationList = databaseHelper.getReceivedMessages( loggedInUser.getUserName());
        MyMsgsAdapter adapter = new MyMsgsAdapter(MyMessagesRecycler.this, communicationList, loggedInUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityMyMsgsRecyclerBinding.myMsgsRecyclerView.setLayoutManager(layoutManager);
        activityMyMsgsRecyclerBinding.myMsgsRecyclerView.setAdapter(adapter);
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

            Intent i = new Intent(MyMessagesRecycler.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(MyMessagesRecycler.this, ViewUserProfileActivity.class);
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
