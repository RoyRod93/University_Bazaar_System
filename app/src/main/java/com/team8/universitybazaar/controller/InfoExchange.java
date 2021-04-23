package com.team8.universitybazaar.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityInfoExchangeBinding;
import com.team8.universitybazaar.model.Info;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;
import java.util.List;

public class InfoExchange extends AppCompatActivity {

    ActivityInfoExchangeBinding activityInfoExchangeBinding;

    DatabaseHelper databaseHelper;
    User loggedInUser;
    String userName;
    List<Info> infoList;

    private Dialog addDialog;
    InfoExchangeAdapter infoExchangeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInfoExchangeBinding = ActivityInfoExchangeBinding.inflate(getLayoutInflater());
        View view = activityInfoExchangeBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Information Exchange");

        databaseHelper = new DatabaseHelper(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra("logged-user")) {
                loggedInUser = (User) getIntent().getSerializableExtra("logged-user");
            } else if (getIntent().hasExtra("userName")) {
                userName = getIntent().getStringExtra("userName");
                loggedInUser = databaseHelper.getDetails(userName);
            }
        } else {
            Intent i = new Intent(InfoExchange.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        infoList = databaseHelper.getInfoExchangePosts();
        infoExchangeAdapter = new InfoExchangeAdapter(this, infoList, loggedInUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        activityInfoExchangeBinding.infoExchangeRecyclerView.setLayoutManager(layoutManager);
        activityInfoExchangeBinding.infoExchangeRecyclerView.setAdapter(infoExchangeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info_exchange, menu);

        MenuItem searchItem = menu.findItem(R.id.searchPosts);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                infoExchangeAdapter.getFilter().filter(newText);
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

            Intent i = new Intent(InfoExchange.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(InfoExchange.this, ViewUserProfileActivity.class);
            i.putExtra("loggedUsernameKey", loggedInUser.getUserName());
            startActivity(i);
        } else if (item.getItemId() == R.id.addNewInfo) {

            addDialog = new Dialog(InfoExchange.this);
            addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            addDialog.setContentView(R.layout.add_new_info);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            addDialog.show();

            Button btnAddInfo = addDialog.findViewById(R.id.btnAddInfo);

            EditText etInfoTitle = addDialog.findViewById(R.id.etInfoTitle);
            EditText etInfoContent = addDialog.findViewById(R.id.etInfoContent);

            btnAddInfo.setOnClickListener(v -> {

                String title = etInfoTitle.getText().toString().trim();
                String content = etInfoContent.getText().toString().trim();

                if (title.length() != 0 && content.length() != 0) {

                    Info info = new Info();
                    info.setAuthor(loggedInUser.getUserName());
                    info.setTitle(title);
                    info.setContent(content);
                    info.setDate(Calendar.getInstance().getTime().toString());

                    if (databaseHelper.addInfo(info)) {

                        infoList.add(0, info);
                    }
                    infoExchangeAdapter.notifyDataSetChanged();
                    addDialog.dismiss();
                }

                if (title.length() == 0 && content.length() == 0) {
                    etInfoTitle.setError("Enter a title!");
                    etInfoContent.setError("Enter content!");
                } else if (title.length() == 0) {
                    etInfoTitle.setError("Enter a title!");
                } else if (content.length() == 0) {
                    etInfoContent.setError("Enter content!");
                }
            });
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