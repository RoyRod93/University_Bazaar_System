package com.team8.universitybazaar.model;

import java.io.Serializable;

public class Communication implements Serializable {

    int commuId;
    String commuFrom;
    String commuTo;
    String commuMsg;
    public Communication() {

    }

    public Communication(int commuId, String commuFrom, String commuTo, String commuMsg) {

        this.commuId = commuId;
        this.commuFrom = commuFrom;
        this.commuTo = commuTo;
        this.commuMsg = commuMsg;
    }

    public int getCommuId() {
        return commuId;
    }
    public void setCommuId(int commuId) {
        this.commuId = commuId;
    }
    public String getCommuFrom() {
        return commuFrom;
    }

    public void setCommuFrom(String commuFrom) {
        this.commuFrom = commuFrom;
    }

    public String getCommuTo() {
        return commuTo;
    }

    public void setCommuTo(String commuTo) {
        this.commuTo = commuTo;
    }

    public String getCommuMsg() {
        return commuMsg;
    }

    public void setCommuMsg(String commuMsg) {
        this.commuMsg = commuMsg;
    }
}
