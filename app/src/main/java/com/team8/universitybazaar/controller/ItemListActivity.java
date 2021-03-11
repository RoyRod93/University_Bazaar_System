package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    ItemListAdapter itemListAdapter;
    ArrayList<SaleItem> saleItemArrayList;
    ListView lvItemList;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("ItemList");
        lvItemList = findViewById(R.id.lvItemList);

        databaseHelper = new DatabaseHelper(this);
        loggedInUser = (User) getIntent().getSerializableExtra("loggedUser");
        showAvailableItem();

    }

    public void showAvailableItem() {

        saleItemArrayList = databaseHelper.getSaleItemList();
        itemListAdapter = new ItemListAdapter(this, saleItemArrayList);
        lvItemList.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();

        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ItemListActivity.this, ItemDetailsScreenActivity.class);
                i.putExtra("bazaar_sale_item", saleItemArrayList.get(position));
                i.putExtra("loggedUser", loggedInUser);
                startActivity(i);

            }
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

            Intent i = new Intent(ItemListActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(ItemListActivity.this, ViewUserProfileActivity.class);
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
