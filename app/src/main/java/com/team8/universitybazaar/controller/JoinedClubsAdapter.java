package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ClubsListBinding;
import com.team8.universitybazaar.databinding.JoinedClubListBinding;
import com.team8.universitybazaar.model.Clubs;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class JoinedClubsAdapter extends RecyclerView.Adapter<JoinedClubsAdapter.ViewHolder> {
    private Context mContext;
    private List<Clubs> clubsList;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    public JoinedClubsAdapter(Context mContext, List<Clubs> clubsList, User loggedInUser) {

        this.mContext = mContext;
        this.clubsList = clubsList;
        this.loggedInUser = loggedInUser;
        databaseHelper = new DatabaseHelper(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private JoinedClubListBinding joinedClubListBinding;

        public ViewHolder(@NonNull JoinedClubListBinding joinedClubListBinding) {
            super(joinedClubListBinding.getRoot());
            this.joinedClubListBinding = joinedClubListBinding;
        }

    }

    @NonNull
    @Override
    public JoinedClubsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(JoinedClubListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull JoinedClubsAdapter.ViewHolder holder, int position) {

        String clubName = clubsList.get(position).getClubName();
        String clubType = clubsList.get(position).getClubType();


        holder.joinedClubListBinding.tvJoinedClubName.setText(clubName);
        holder.joinedClubListBinding.tvJoinedClubType.setText(clubType);

    }

    @Override
    public int getItemCount() {

        return clubsList.size();
    }
}
