package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

public class Stairs {
    @SerializedName("stairs_id")
    private Integer id;
    @SerializedName("x")
    private Integer x;
    @SerializedName("y")
    private Integer y;
    @SerializedName("level")
    private Integer level;
    @SerializedName("open")
    private Boolean open;

    public Stairs() { }

    public Stairs(Integer id, Integer x, Integer y, Integer level, Boolean open) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.level = level;
        this.open = open;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getLevel() {
        return level;
    }

    public Boolean getOpen() {
        return open;
    }
}
