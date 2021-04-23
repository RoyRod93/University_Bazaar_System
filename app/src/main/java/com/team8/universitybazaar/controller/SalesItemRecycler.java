package com.team8.universitybazaar.controller;

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
import android.widget.SearchView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivitySalesItemRecyclerBinding;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class SalesItemRecycler extends AppCompatActivity {

    private User loggedInUser;
    private DatabaseHelper databaseHelper;
    private List<SaleItem> saleItemList;

    private SalesAdapter adapter;

    ActivitySalesItemRecyclerBinding activitySalesItemRecyclerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySalesItemRecyclerBinding = ActivitySalesItemRecyclerBinding.inflate(getLayoutInflater());
        View view = activitySalesItemRecyclerBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Sales/Exchange");

        databaseHelper = new DatabaseHelper(this);
        loggedInUser = (User) getIntent().getSerializableExtra("logged-user");

        loadData();
    }

    private void loadData() {

        saleItemList = databaseHelper.getSaleItemList();
        adapter = new SalesAdapter(SalesItemRecycler.this, saleItemList, loggedInUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activitySalesItemRecyclerBinding.itemListRecyclerView.setLayoutManager(layoutManager);
        activitySalesItemRecyclerBinding.itemListRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sales_exchange, menu);

        MenuItem searchItem = menu.findItem(R.id.searchItems);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

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

            Intent i = new Intent(SalesItemRecycler.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(SalesItemRecycler.this, ViewUserProfileActivity.class);
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