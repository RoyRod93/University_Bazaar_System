package com.team8.universitybazaar.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team8.universitybazaar.dao.DatabaseHelper;
import com.team8.universitybazaar.databinding.MyMsgsListBinding;
import com.team8.universitybazaar.model.Clubs;
import com.team8.universitybazaar.model.Communication;
import com.team8.universitybazaar.model.User;

import java.util.List;

public class MyMsgsAdapter extends RecyclerView.Adapter<MyMsgsAdapter.ViewHolder> {
    private Context mContext;
    private List<Communication> communicationList;
    private User loggedInUser;
    private DatabaseHelper databaseHelper;

    public MyMsgsAdapter(Context mContext, List<Communication> communicationList, User loggedInUser) {

        this.mContext = mContext;
        this.communicationList = communicationList;
        this.loggedInUser = loggedInUser;
        databaseHelper = new DatabaseHelper(mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MyMsgsListBinding myMsgsListBinding;

        public ViewHolder(@NonNull MyMsgsListBinding myMsgsListBinding) {
            super(myMsgsListBinding.getRoot());
            this.myMsgsListBinding = myMsgsListBinding;
        }
    }

    @NonNull
    @Override
    public MyMsgsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MyMsgsListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyMsgsAdapter.ViewHolder holder, int position) {

        String msgFrom = communicationList.get(position).getCommuFrom();
        String msg = communicationList.get(position).getCommuMsg();


        holder.myMsgsListBinding.tvFromName.setText(msgFrom);
        holder.myMsgsListBinding.tvMessage.setText(msg);



//        holder.myMsgsListBinding.btnClubListingJoinClub.setOnClickListener(v -> {
//            String cName =  clubsList.get(position).getClubName();
//            databaseHelper.joinClub(cName,loggedInUser.getUserName());
//
//            Intent i = new Intent(mContext, ClubOptionsActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            i.putExtra("logged-user", loggedInUser);
//            mContext.startActivity(i);
//        });
    }

    @Override
    public int getItemCount() {

        return communicationList.size();
    }
}
