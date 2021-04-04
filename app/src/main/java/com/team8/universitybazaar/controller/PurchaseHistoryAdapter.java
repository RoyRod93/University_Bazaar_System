package com.team8.universitybazaar.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.databinding.PurchasedItemBinding;
import com.team8.universitybazaar.model.Transaction;
import com.team8.universitybazaar.model.User;

import java.util.Arrays;
import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {

    private static final String TAG = PurchaseHistoryAdapter.class.getSimpleName();

    private Context mContext;
    private List<Transaction> transactionList;
    private User loggedUser;

    public PurchaseHistoryAdapter(Context mContext, List<Transaction> transactionList, User loggedUser) {
        this.mContext = mContext;
        this.transactionList = transactionList;
        this.loggedUser = loggedUser;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private PurchasedItemBinding purchasedItemBinding;


        public ViewHolder(@NonNull PurchasedItemBinding purchasedItemBinding) {

            super(purchasedItemBinding.getRoot());
            this.purchasedItemBinding = purchasedItemBinding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PurchaseHistoryAdapter.ViewHolder(PurchasedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int transactionId = transactionList.get(position).getTransactionId();
        int saleId = transactionList.get(position).getSaleId();
        String cardNo = transactionList.get(position).getCardNo();

        if (cardNo.equals("N/A")) {
            cardNo = "Exchange, contact seller.";
        } else {
            cardNo = "Paid with card ending with: " + cardNo.split(" ")[3];
        }

        String[] splitDate = transactionList.get(position).getTransactionDate().split(" ");
        String transactionDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[5];
        String transactionCost = transactionList.get(position).getTransactionAmount();

        holder.purchasedItemBinding.tvTransactionId.setText(String.valueOf(transactionId));
        holder.purchasedItemBinding.tvSaleId.setText(String.valueOf(saleId));
        holder.purchasedItemBinding.tvPaymentMethod.setText(cardNo);
        holder.purchasedItemBinding.tvPurchaseDate.setText(transactionDate);

        if (transactionCost.equals("$0")) {

            holder.purchasedItemBinding.tvItemPrice.setText("N/A");
        } else {

            holder.purchasedItemBinding.tvItemPrice.setText(transactionCost);
        }

    }

    @Override
    public int getItemCount() {

        return transactionList.size();
    }
}
