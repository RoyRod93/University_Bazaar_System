package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.databinding.ActivityItemDisplayPageBinding;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

public class ItemDetailsScreenActivity extends AppCompatActivity {

    ActivityItemDisplayPageBinding activityItemDisplayPageBinding;
    private User loggedInUser;
    private SaleItem saleItem;

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

        activityItemDisplayPageBinding.userNameDBval.setText(saleItem.getUserName());
        activityItemDisplayPageBinding.itemNameDbVal.setText(saleItem.getItemName());
        activityItemDisplayPageBinding.itemDescriptionDbVal.setText(saleItem.getItemDescription());
        activityItemDisplayPageBinding.postDateDbVal.setText(saleItem.getPostDate());
        activityItemDisplayPageBinding.itemCategoryDbVal.setText(saleItem.getItemCategory());
        activityItemDisplayPageBinding.itemOfferTypeDbVal.setText(saleItem.getOfferType());

        if (saleItem.getItemCategory().equalsIgnoreCase("ELECTRONICS")) {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.electronics);
        } else if (saleItem.getItemCategory().equalsIgnoreCase("FURNITURE")) {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.furniture);
        } else {
            activityItemDisplayPageBinding.cartImage.setImageResource(R.drawable.stationary);
        }

        //itemImg.setImageResource(R.drawable.electronics);

        activityItemDisplayPageBinding.btnBuyExchange.setOnClickListener(v -> {
            Intent i = new Intent(ItemDetailsScreenActivity.this, WipActivity.class);
            startActivity(i);
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
