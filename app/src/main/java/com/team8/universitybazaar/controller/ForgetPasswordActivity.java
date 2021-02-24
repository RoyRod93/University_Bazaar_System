package com.team8.universitybazaar.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityForgetpasswordBinding;
import com.team8.universitybazaar.model.User;

public class ForgetPasswordActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    ActivityForgetpasswordBinding activityForgetPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgetPasswordBinding = activityForgetPasswordBinding.inflate(getLayoutInflater());
        View view = activityForgetPasswordBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseHelper = new DatabaseHelper(this);

        activityForgetPasswordBinding.btnResetPassword.setOnClickListener(v -> {

            User user = new User();

            String fpUserName = activityForgetPasswordBinding.fpUserName.getText().toString().trim();
            String fpNewPass = activityForgetPasswordBinding.fpNewPass.getText().toString().trim();
            String fpConfNewPass = activityForgetPasswordBinding.fpConfNewPass.getText().toString().trim();
            boolean isValidUsername = databaseHelper.isValidUserName(fpUserName);
            System.out.println(" the  user password obtained is ");

            if(!isValidUsername){
                Toast.makeText(ForgetPasswordActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            }else if(!fpNewPass.equals(fpConfNewPass)){
                Toast.makeText(ForgetPasswordActivity.this, "New password & confirm password does not match", Toast.LENGTH_SHORT).show();
            }else {
                User usr = databaseHelper.getDetails(fpUserName);
                usr.setPassword(fpNewPass);
                Boolean updatePassword = databaseHelper.updateUser(usr);
                if (updatePassword) {
                    Toast.makeText(ForgetPasswordActivity.this, "Password has been reset successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}