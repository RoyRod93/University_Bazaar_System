package com.team8.universitybazaar.controller;

import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.model.SaleItem;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    Context context;
    List<SaleItem> saleItemList;
     public ItemListAdapter(Context context, List<SaleItem> saleItemList){
         this.context = context;
         this.saleItemList = saleItemList;
     }

    @Override
    public int getCount() {
        return this.saleItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return saleItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_view_sale_item, null);

        TextView tvSaleId = view.findViewById(R.id.tvSaleId);
        TextView tvItemName = view.findViewById(R.id.tvItemName);
        TextView tvItemCategory = view.findViewById(R.id.tvItemCategory);
        TextView tvOfferType = view.findViewById(R.id.tvOfferType);
        SaleItem saleItem = saleItemList.get(i);

        tvSaleId.setText(String.valueOf(saleItem.getSaleId()));
        tvItemName.setText(String.valueOf(saleItem.getItemName()));
        tvItemCategory.setText(String.valueOf(saleItem.getItemCategory()));
        tvOfferType.setText(String.valueOf(saleItem.getOfferType()));
        return view;
    }

}
