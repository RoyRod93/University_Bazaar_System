package com.team8.universitybazaar.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.model.SaleItem;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    ItemListAdapter itemListAdapter;
    ArrayList<SaleItem> saleItemArrayList;
    ListView lvItemList;
    SaleItem saleItem;
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
                startActivity(i);

            }
        });

    }

}
