package com.team8.universitybazaar.model;

import java.io.Serializable;

public class Clubs implements Serializable {

    int clubId;
    String clubName;
    String clubType;
    String clubCreationDate;
    String clubDescription;
    String clubOwner;
    int clubMemberCapacity;

    public Clubs() {

    }

    public Clubs(int clubId, String clubName, String clubType, String clubCreationDate, String clubDescription,
                 String clubOwner, int clubMemberCapacity) {

        this.clubId = clubId;
        this.clubName = clubName;
        this.clubType = clubType;
        this.clubCreationDate = clubCreationDate;
        this.clubDescription = clubDescription;
        this.clubOwner = clubOwner;
        this.clubMemberCapacity = clubMemberCapacity;
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

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getClubCreationDate() {
        return clubCreationDate;
    }

    public void setClubCreationDate(String clubCreationDate) {
        this.clubCreationDate = clubCreationDate;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubOwner() {
        return clubOwner;
    }

    public void setClubOwner(String clubOwner) {
        this.clubOwner = clubOwner;
    }

    public int getClubMemberCapacity() {
        return clubMemberCapacity;
    }

    public void setClubMemberCapacity(int clubMemberCapacity) {
        this.clubMemberCapacity = clubMemberCapacity;
    }


}
