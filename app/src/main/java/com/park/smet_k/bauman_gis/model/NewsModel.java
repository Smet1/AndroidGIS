package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsModel {
    @SerializedName("id")
    private Integer ID;
    @SerializedName("title")
    private String title;
    @SerializedName("payload")
    private String payload;
//    @SerializedName("time")
//    private java.sql.Date time;

    public NewsModel() {
    }

    public NewsModel(String title, String payload, java.sql.Date time) {
        this.title = title;
        this.payload = payload;
//        this.time = time;
    }

    public Integer getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getPayload() {
        return payload;
    }

//    public Date getTime() {
//        return time;
//    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

//    public void setTime(java.sql.Date time) {
//        this.time = time;
//    }

}
