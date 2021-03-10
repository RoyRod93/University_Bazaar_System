package com.team8.universitybazaar.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.misc.Validations;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    LinearLayout llGuestRsIp;
    LinearLayout llGuestRsOp;
    ItemListAdapter itemListAdapter;
    ArrayList<SaleItem> saleItemArrayList;
    ListView lvItemList;


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
        showAvailableItem();

    }

    public void showAvailableItem(){

        saleItemArrayList = databaseHelper.getSaleItemList();
         itemListAdapter= new ItemListAdapter(this, saleItemArrayList);
        lvItemList.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();

    }

}
