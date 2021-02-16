package com.team8.universitybazaar.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityLoginBinding;
import com.team8.universitybazaar.model.User;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseHelper = new DatabaseHelper(this);

        activityLoginBinding.btnLogin.setOnClickListener(v -> {

            final User user = new User();

            String userName = activityLoginBinding.etUserName.getText().toString().trim();
            String password = activityLoginBinding.etPassword.getText().toString().trim();

            user.setUserName(userName);
            user.setPassword(password);

            if (databaseHelper.checkValidUser(user)) {

                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isUserLogin", true);
                editor.putString("userName", userName);
                editor.apply();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", databaseHelper.getDetails(userName));
                startActivity(i);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            }
        });

        activityLoginBinding.tvRegister.setOnClickListener(v -> {

            Intent i = new Intent(LoginActivity.this, RegisterUser.class);
            startActivity(i);
        });
        
        activityLoginBinding.tvForgotPwd.setOnClickListener(v -> {

            Toast.makeText(this, "Clicked forgot password...", Toast.LENGTH_SHORT).show();
        });
    }
}