package com.team8.universitybazaar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.databinding.InfoExchangeItemBinding;
import com.team8.universitybazaar.model.Info;
import com.team8.universitybazaar.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InfoExchangeAdapter extends RecyclerView.Adapter<InfoExchangeAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private List<Info> infoList;
    private List<Info> infoListFull;
    private User loggedInUser;

    public InfoExchangeAdapter(Context mContext, List<Info> infoList, User loggedInUser) {
        this.mContext = mContext;
        this.infoList = infoList;
        this.loggedInUser = loggedInUser;

        infoListFull = new ArrayList<>(infoList);
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

    @Override
    public Filter getFilter() {
        return postsFilter;
    }

    private Filter postsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Info> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(infoListFull);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (Info item: infoListFull) {
                    if (item.getTitle().toLowerCase().contains(filteredPattern) || item.getContent().toLowerCase().contains(filteredPattern)) {
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

            infoList.clear();
            infoList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
