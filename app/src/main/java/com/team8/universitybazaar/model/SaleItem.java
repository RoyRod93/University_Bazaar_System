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
    int price;
    int isVisible;

    public SaleItem() {

    }

    public SaleItem(int saleId, String userName, String itemName, String itemDescription, String postDate, String itemCategory, String offerType, int price, int isVisible) {

        this.saleId = saleId;
        this.userName = userName;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.postDate = postDate;
        this.itemCategory = itemCategory;
        this.offerType = offerType;
        this.price = price;
        this.isVisible = isVisible;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
}
