package com.team8.universitybazaar.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityPurchaseHistoryBinding;
import com.team8.universitybazaar.model.Transaction;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class PurchaseHistoryActivity extends AppCompatActivity {

    ActivityPurchaseHistoryBinding activityPurchaseHistoryBinding;
    private DatabaseHelper databaseHelper;

    private User loggedInUser;
    private List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityPurchaseHistoryBinding = com.team8.universitybazaar.databinding.ActivityPurchaseHistoryBinding.inflate(getLayoutInflater());
        View view = activityPurchaseHistoryBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Purchase History");

        databaseHelper = new DatabaseHelper(this);

        loggedInUser = (User) getIntent().getSerializableExtra("logged-user");

        loadData();
    }

    private void loadData() {

        transactionList = databaseHelper.getPurchaseHistory(loggedInUser.getUserName());
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this, transactionList, loggedInUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityPurchaseHistoryBinding.paymentHistoryRecyclerView.setLayoutManager(layoutManager);
        activityPurchaseHistoryBinding.paymentHistoryRecyclerView.setAdapter(adapter);
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

            Intent i = new Intent(PurchaseHistoryActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(PurchaseHistoryActivity.this, ViewUserProfileActivity.class);
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