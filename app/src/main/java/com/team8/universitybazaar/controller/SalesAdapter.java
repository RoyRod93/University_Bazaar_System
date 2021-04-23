package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.databinding.SalesItemBinding;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private List<SaleItem> salesItems;
    private List<SaleItem> salesItemsFull;
    private User loggedInUser;

    public SalesAdapter(Context mContext, List<SaleItem> salesItems, User loggedInUser) {

        this.mContext = mContext;
        this.salesItems = salesItems;
        this.loggedInUser = loggedInUser;

        salesItemsFull = new ArrayList<>(salesItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SalesItemBinding salesItemBinding;

        public ViewHolder(@NonNull SalesItemBinding salesItemBinding) {
            super(salesItemBinding.getRoot());
            this.salesItemBinding = salesItemBinding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SalesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = salesItems.get(position).getItemName();
        String category = salesItems.get(position).getItemCategory();
        String seller = salesItems.get(position).getUserName();
        String offerType = salesItems.get(position).getOfferType();

        holder.salesItemBinding.tvItemName.setText(title);
        holder.salesItemBinding.tvItemCategory.setText(category);
        holder.salesItemBinding.soldBy.setText(seller);
        holder.salesItemBinding.tvOfferType.setText(offerType);

        if (category.equalsIgnoreCase("ELECTRONICS")) {
            holder.salesItemBinding.itemImage.setImageResource(R.drawable.electronics);
        } else if (category.equalsIgnoreCase("FURNITURE")) {
            holder.salesItemBinding.itemImage.setImageResource(R.drawable.furniture);
        } else {
            holder.salesItemBinding.itemImage.setImageResource(R.drawable.stationary);
        }

        if (offerType.equals("Sell")) {

            holder.salesItemBinding.tvOfferPrice.setText("$" + salesItems.get(position).getPrice());
        } else {

            holder.salesItemBinding.tvOfferPrice.setText("N/A");
        }

        holder.itemView.setOnClickListener(v -> {

            Intent i = new Intent(mContext, ItemDetailsScreenActivity.class);
            i.putExtra("bazaar_sale_item", salesItems.get(position));
            i.putExtra("loggedUser", loggedInUser);
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {

        return salesItems.size();
    }

    @Override
    public Filter getFilter() {
        return itemsFilter;
    }

    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<SaleItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(salesItemsFull);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (SaleItem item: salesItemsFull) {
                    if (item.getItemName().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            salesItems.clear();
            salesItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
