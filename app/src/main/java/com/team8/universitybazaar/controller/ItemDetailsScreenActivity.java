package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ActivityItemDisplayPageBinding;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.Transaction;
import com.team8.universitybazaar.model.User;

import java.util.Calendar;

public class ItemDetailsScreenActivity extends AppCompatActivity {

    ActivityItemDisplayPageBinding activityItemDisplayPageBinding;
    private User loggedInUser;
    private SaleItem saleItem;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityItemDisplayPageBinding = ActivityItemDisplayPageBinding.inflate(getLayoutInflater());
        View view = activityItemDisplayPageBinding.getRoot();
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Item Details");

        loggedInUser = (User) getIntent().getSerializableExtra("loggedUser");

        saleItem = (SaleItem) getIntent().getSerializableExtra("bazaar_sale_item");

        //final ImageView itemImg = activityItemDisplayPageBinding.cartImage;

        databaseHelper = new DatabaseHelper(this);

        String[] splitDate = saleItem.getPostDate().split(" ");

        activityItemDisplayPageBinding.userNameDBval.setText(saleItem.getUserName());
        activityItemDisplayPageBinding.itemNameDbVal.setText(saleItem.getItemName());
        activityItemDisplayPageBinding.itemDescriptionDbVal.setText(saleItem.getItemDescription());
        activityItemDisplayPageBinding.postDateDbVal.setText(splitDate[2] + "-" + splitDate[1] + "-" + splitDate[5]);
        activityItemDisplayPageBinding.itemCategoryDbVal.setText(saleItem.getItemCategory());
        activityItemDisplayPageBinding.itemOfferTypeDbVal.setText(saleItem.getOfferType());
        activityItemDisplayPageBinding.itemPriceDbVal.setText("$" + String.valueOf(saleItem.getPrice()));

        if (saleItem.getItemCategory().equalsIgnoreCase("ELECTRONICS")) {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.electronics);
        } else if (saleItem.getItemCategory().equalsIgnoreCase("FURNITURE")) {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.furniture);
        } else {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.stationary);
        }

        //itemImg.setImageResource(R.drawable.electronics);

        if (saleItem.getOfferType().equals("Sell")) {

            activityItemDisplayPageBinding.btnBuyExchange.setText("Buy");
        } else {

            activityItemDisplayPageBinding.btnBuyExchange.setText("Exchange");
        }

        activityItemDisplayPageBinding.btnBuyExchange.setOnClickListener(v -> {

            if (saleItem.getOfferType().equals("Sell")) {

                Intent i = new Intent(ItemDetailsScreenActivity.this, PaymentActivity.class);
                i.putExtra("loggedUser", loggedInUser);
                i.putExtra("bazaar_sale_item", saleItem);
                startActivity(i);
            } else {

                Transaction transaction = new Transaction();

                transaction.setSaleId(saleItem.getSaleId());
                transaction.setUserName(loggedInUser.getUserName());
                transaction.setCardNo("N/A");
                transaction.setSecurityCode("N/A");
                transaction.setNameOnCard("N/A");
                transaction.setExpiryDate("N/A");
                transaction.setZipCode("N/A");
                transaction.setTransactionDate(Calendar.getInstance().getTime().toString());
                transaction.setTransactionAmount("$0");

                databaseHelper.hideSoldItem(saleItem.getSaleId());
                databaseHelper.addTransaction(transaction);

                Intent i = new Intent(ItemDetailsScreenActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("logged-user", loggedInUser);
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

            Intent i = new Intent(ItemDetailsScreenActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (item.getItemId() == R.id.viewProfile) {

            Intent i = new Intent(ItemDetailsScreenActivity.this, ViewUserProfileActivity.class);
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
