package com.park.smet_k.bauman_gis.model;

import com.google.gson.annotations.SerializedName;

public class StairsLink {
    @SerializedName("link_id")
    private Integer id;
    @SerializedName("id_from")
    private Integer idFrom;
    @SerializedName("id_to")
    private Integer idTo;
    @SerializedName("weight")
    private Integer weight;
    @SerializedName("open")
    private Boolean open;

    public StairsLink() { }

    public StairsLink(Integer id, Integer idFrom, Integer idTo, Integer weight, Boolean open) {
        this.id = id;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.weight = weight;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdFrom() {
        return idFrom;
    }

    public Integer getIdTo() {
        return idTo;
    }

    public Integer getWeight() {
        return weight;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
