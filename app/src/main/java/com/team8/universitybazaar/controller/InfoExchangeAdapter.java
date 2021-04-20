package com.team8.universitybazaar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.databinding.InfoExchangeItemBinding;
import com.team8.universitybazaar.model.Info;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class InfoExchangeAdapter extends RecyclerView.Adapter<InfoExchangeAdapter.ViewHolder> {

    private Context mContext;
    private List<Info> infoList;
    private User loggedInUser;

    public InfoExchangeAdapter(Context mContext, List<Info> infoList, User loggedInUser) {
        this.mContext = mContext;
        this.infoList = infoList;
        this.loggedInUser = loggedInUser;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private InfoExchangeItemBinding infoExchangeItemBinding;

        public ViewHolder(@NonNull InfoExchangeItemBinding infoExchangeItemBinding) {
            super(infoExchangeItemBinding.getRoot());
            this.infoExchangeItemBinding = infoExchangeItemBinding;
        }
    }

    @NonNull
    @Override
    public InfoExchangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InfoExchangeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfoExchangeAdapter.ViewHolder holder, int position) {

        String title = infoList.get(position).getTitle();
        String author = infoList.get(position).getAuthor();
        String[] splitDate = infoList.get(position).getDate().split(" ");
        String date = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[5];
        String content = infoList.get(position).getContent();

        holder.infoExchangeItemBinding.tvTitle.setText(title.trim());
        holder.infoExchangeItemBinding.tvAuthorPostDate.setText("Posted by: " + author.trim() + " on " + date.trim());
        holder.infoExchangeItemBinding.tvContent.setText(content.trim());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
