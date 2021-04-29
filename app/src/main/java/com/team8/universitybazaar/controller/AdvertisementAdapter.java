package com.team8.universitybazaar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.AdvertisementItemBinding;
import com.team8.universitybazaar.model.Advertisement;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {
    private Context mContext;
    private List<Advertisement> advertisementList;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    public AdvertisementAdapter(Context mContext, List<Advertisement> advertisementList, User loggedInUser) {

        this.mContext = mContext;
        this.advertisementList = advertisementList;
        this.loggedInUser = loggedInUser;
        databaseHelper = new DatabaseHelper(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AdvertisementItemBinding advertisementItemBinding;

        public ViewHolder(@NonNull AdvertisementItemBinding advertisementItemBinding) {
            super(advertisementItemBinding.getRoot());
            this.advertisementItemBinding = advertisementItemBinding;
        }
    }

    @NonNull
    @Override
    public AdvertisementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdvertisementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdvertisementAdapter.ViewHolder holder, int position) {

        String tvAdvTitle = advertisementList.get(position).getAdvTitle();
        String tvAdvExpiry = advertisementList.get(position).getAdvExpiryDate();
        String tvAdvPublisher = advertisementList.get(position).getAdvPublisher();
        String tvAdvDescription = advertisementList.get(position).getAdvBodyMsg();
        String tvAdvPublishedDate = advertisementList.get(position).getAdvPublishDate();


        holder.advertisementItemBinding.tvAdvTitle.setText(tvAdvTitle);
        holder.advertisementItemBinding.tvAdvExpiry.setText(tvAdvExpiry);
        holder.advertisementItemBinding.tvAdvPublisher.setText(tvAdvPublisher);
        holder.advertisementItemBinding.tvAdvDescription.setText(tvAdvDescription);
        holder.advertisementItemBinding.tvAdvPublishedDate.setText(tvAdvPublishedDate);

    }

    @Override
    public int getItemCount() {

        return advertisementList.size();
    }
}
