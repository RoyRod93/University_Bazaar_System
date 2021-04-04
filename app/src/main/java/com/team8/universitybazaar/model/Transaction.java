package com.team8.universitybazaar.model;

public class Transaction {

    int transactionId;
    int saleId;
    String userName;
    String cardNo;
    String securityCode;
    String nameOnCard;
    String expiryDate;
    String zipCode;
    String transactionDate;
    String transactionAmount;

    public Transaction() {

    }

    public Transaction(int transactionId, int saleId, String userName, String cardNo, String securityCode, String nameOnCard, String expiryDate, String zipCode, String transactionDate, String transactionAmount) {

        this.transactionId = transactionId;
        this.saleId = saleId;
        this.userName = userName;
        this.cardNo = cardNo;
        this.securityCode = securityCode;
        this.nameOnCard = nameOnCard;
        this.expiryDate = expiryDate;
        this.zipCode = zipCode;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
