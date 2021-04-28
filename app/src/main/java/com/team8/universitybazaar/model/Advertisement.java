package com.team8.universitybazaar.model;

import java.io.Serializable;

public class Advertisement implements Serializable {

    int advId;
    String advTitle;
    String advBodyMsg;
    String advPublishDate;
    String advExpiryDate;
    String advPublisher;

    public Advertisement() {
    }

    public Advertisement(int advId, String advTitle, String advBodyMsg, String advPublishDate, String advExpiryDate, String advPublisher) {
        this.advId = advId;
        this.advTitle = advTitle;
        this.advBodyMsg = advBodyMsg;
        this.advPublishDate = advPublishDate;
        this.advExpiryDate = advExpiryDate;
        this.advPublisher = advPublisher;
    }

    public int getAdvId() {
        return advId;
    }

    public void setAdvId(int advId) {
        this.advId = advId;
    }

    public String getAdvTitle() {
        return advTitle;
    }

    public void setAdvTitle(String advTitle) {
        this.advTitle = advTitle;
    }

    public String getAdvBodyMsg() {
        return advBodyMsg;
    }

    public void setAdvBodyMsg(String advBodyMsg) {
        this.advBodyMsg = advBodyMsg;
    }

    public String getAdvPublishDate() {
        return advPublishDate;
    }

    public void setAdvPublishDate(String advPublishDate) {
        this.advPublishDate = advPublishDate;
    }

    public String getAdvExpiryDate() {
        return advExpiryDate;
    }

    public void setAdvExpiryDate(String advExpiryDate) {
        this.advExpiryDate = advExpiryDate;
    }

    public String getAdvPublisher() {
        return advPublisher;
    }

    public void setAdvPublisher(String advPublisher) {
        this.advPublisher = advPublisher;
    }
}
