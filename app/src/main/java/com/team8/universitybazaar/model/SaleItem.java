package com.team8.universitybazaar.model;

import java.io.Serializable;

public class SaleItem implements Serializable {

    int saleId;
    String userName;
    String itemName;
    String itemDescription;
    String postDate;
    String itemCategory;
    String offerType;

    public SaleItem() {

    }

    public SaleItem(int saleId, String userName, String itemName, String itemDescription, String postDate, String itemCategory, String offerType) {

        this.saleId = saleId;
        this.userName = userName;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.postDate = postDate;
        this.itemCategory = itemCategory;
        this.offerType = offerType;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
}
