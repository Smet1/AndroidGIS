package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsModel {
    @SerializedName("title")
    private String title;
    @SerializedName("payload")
    private String payload;
    @SerializedName("time")
    private Date time;

    public NewsModel() {
    }

    public NewsModel(String title, String payload, Date time) {
        this.title = title;
        this.payload = payload;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getPayload() {
        return payload;
    }

    public Date getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
