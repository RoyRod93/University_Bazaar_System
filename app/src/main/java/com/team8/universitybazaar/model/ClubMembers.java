package com.team8.universitybazaar.model;

import java.io.Serializable;

public class ClubMembers implements Serializable {

    int clubId;
    String clubName;
    String clubMemberUserId;
    String clubMemberFirstName;
    String clubMemberLastName;
    String joiningDate;

    public ClubMembers() {

    }

    public ClubMembers(int clubId, String clubName, String joiningDate, String clubMemberUserId,
                       String clubMemberFirstName, String clubMemberLastName) {

        this.clubId = clubId;
        this.clubName = clubName;
        this.clubMemberUserId = clubMemberUserId;
        this.joiningDate = joiningDate;
        this.clubMemberFirstName = clubMemberFirstName;
        this.clubMemberLastName = clubMemberLastName;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubMemberUserId() {
        return clubMemberUserId;
    }

    public void setClubMemberUserId(String clubMemberUserId) {
        this.clubMemberUserId = clubMemberUserId;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getClubMemberFirstName() {
        return clubMemberFirstName;
    }

    public void setClubMemberFirstName(String clubMemberFirstName) {
        this.clubMemberFirstName = clubMemberFirstName;
    }

    public String getClubMemberLastName() {
        return clubMemberLastName;
    }

    public void setClubMemberLastName(String clubMemberLastName) {
        this.clubMemberLastName = clubMemberLastName;
    }
}


