package com.park.smet_k.bauman_gis;

import java.util.Date;

public class News {
    private String title;
    private String payload;
    private Date time;

    public News(String title, String payload, Date time) {
        this.title = title;
        this.payload = payload;
        this.time = time;
    }

    public News() {
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
