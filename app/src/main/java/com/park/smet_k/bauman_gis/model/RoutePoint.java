package com.park.smet_k.bauman_gis.model;

public class RoutePoint {
    private Integer id;
    private Character type;  // S, L, W
    private Integer x;
    private Integer y;
    private Integer level;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Character type) {
        this.type = type;
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

    public void setName(String name) {
        this.name = name;
    }



    public Integer getId() {
        return id;
    }

    public Character getType() {
        return type;
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

    public String getName() {
        return name;
    }
}
