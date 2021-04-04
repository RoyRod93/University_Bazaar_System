package com.team8.universitybazaar.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivitySalesExchangeFormBinding;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class SalesExchangeForm extends AppCompatActivity {

    private static final String TAG = SalesExchangeForm.class.getSimpleName();

    ActivitySalesExchangeFormBinding activitySalesExchangeFormBinding;
    private Validations validations;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySalesExchangeFormBinding = ActivitySalesExchangeFormBinding.inflate(getLayoutInflater());
        View view = activitySalesExchangeFormBinding.getRoot();
        setContentView(view);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Add Listing");

        databaseHelper = new DatabaseHelper(this);
        validations = new Validations();
        SaleItem newItem = new SaleItem();
        newItem.setOfferType("Sell");

        loggedInUser = (User) getIntent().getSerializableExtra("logged-user");

        activitySalesExchangeFormBinding.rgSalesExchange.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbSellItem) {
                changeEditTextAvailability(activitySalesExchangeFormBinding.etItemPrice, true);
                newItem.setOfferType("Sell");
            }

            if (checkedId == R.id.rbExchangeItem) {
                changeEditTextAvailability(activitySalesExchangeFormBinding.etItemPrice, false);
                newItem.setOfferType("Exchange");
            }
        });

        activitySalesExchangeFormBinding.btnSaveItem.setOnClickListener(v -> {

            newItem.setUserName(loggedInUser.getUserName());
            newItem.setItemName(activitySalesExchangeFormBinding.etItemName.getText().toString().trim());
            newItem.setItemDescription(activitySalesExchangeFormBinding.etItemDescription.getText().toString().trim());
            newItem.setPostDate(Calendar.getInstance().getTime().toString());

            int selectedRadioButtonId = activitySalesExchangeFormBinding.rgItemType.getCheckedRadioButtonId();

            if (selectedRadioButtonId != -1) {

                RadioButton selectedRB = findViewById(selectedRadioButtonId);
                newItem.setItemCategory(selectedRB.getText().toString().trim());
            } else {
                newItem.setItemCategory("Other");
            }

            if (isValid()) {
                /*Write database insertion here.... call addListing method and pass the newItem object*/
                if (newItem.getOfferType().equals("Sell")) {

                    newItem.setPrice(Integer.parseInt(activitySalesExchangeFormBinding.etItemPrice.getText().toString().trim()));
                } else {

                    newItem.setPrice(0);
                }
                newItem.setIsVisible(1);
                databaseHelper.addListing(newItem);
                Toast.makeText(this, "Listing added successfully...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SalesExchangeForm.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
                startActivity(i);
            } else {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValid() {
        if (validations.isBlank(activitySalesExchangeFormBinding.etItemName)) {
            activitySalesExchangeFormBinding.etItemName.setError("Please enter the Item name");
            return false;
        } else if (validations.isBlank(activitySalesExchangeFormBinding.etItemDescription)) {
            activitySalesExchangeFormBinding.etItemDescription.setError("Enter Item Description");
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

            Intent i = new Intent(SalesExchangeForm.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(SalesExchangeForm.this, ViewUserProfileActivity.class);
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