package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.R;
import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.ClubsListBinding;
import com.team8.universitybazaar.model.Clubs;
import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder> {
    private Context mContext;
    private List<Clubs> clubsList;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    public ClubsAdapter(Context mContext, List<Clubs> clubsList, User loggedInUser) {

        this.mContext = mContext;
        this.clubsList = clubsList;
        this.loggedInUser = loggedInUser;
        databaseHelper = new DatabaseHelper(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ClubsListBinding clubsListBinding;

        public ViewHolder(@NonNull ClubsListBinding clubsListBinding) {
            super(clubsListBinding.getRoot());
            this.clubsListBinding = clubsListBinding;
        }
    }

    @NonNull
    @Override
    public ClubsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ClubsListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ClubsAdapter.ViewHolder holder, int position) {

        String clubName = clubsList.get(position).getClubName();
        String clubType = clubsList.get(position).getClubType();


        holder.clubsListBinding.tvClubName.setText(clubName);
        holder.clubsListBinding.tvClubType.setText(clubType);



        holder.clubsListBinding.btnClubListingJoinClub.setOnClickListener(v -> {
            String cName =  clubsList.get(position).getClubName();
            databaseHelper.joinClub(cName,loggedInUser.getUserName());

            Intent i = new Intent(mContext, ClubOptionsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("logged-user", loggedInUser);
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {

        return clubsList.size();
    }
}
