package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

public class RouteModel {
    @SerializedName("user_id")
    private Integer user_id;
    @SerializedName("point_from")
    private Integer point_from;
    @SerializedName("point_to")
    private Integer point_to;

    public RouteModel(Integer user_id, Integer point_from, Integer point_to) {
        this.user_id = user_id;
        this.point_from = point_from;
        this.point_to = point_to;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setPoint_from(Integer point_from) {
        this.point_from = point_from;
    }

    public void setPoint_to(Integer point_to) {
        this.point_to = point_to;
    }

    public Integer getUser_id() {

        return user_id;
    }

    public Integer getPoint_from() {
        return point_from;
    }

    public Integer getPoint_to() {
        return point_to;
    }
}
