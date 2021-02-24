package com.team8.universitybazaar.controller;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityWorkInProgressBinding;

public class WipActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    ActivityWorkInProgressBinding activityWorkInProgressBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWorkInProgressBinding = activityWorkInProgressBinding.inflate(getLayoutInflater());
        View view = activityWorkInProgressBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseHelper = new DatabaseHelper(this);


    }
}