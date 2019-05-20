package com.park.smet_k.bauman_gis.model;

public class RoutePoint {
    private Integer id;
    private Integer x;
    private Integer y;
    private Integer level;
    private String name;

    public RoutePoint(Integer x, Integer y, Integer level, String name) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int compare(RoutePoint o) {
        if (this.getX() < o.getX()) {
            return -1;
        } else if (this.getY() < o.getY()) {
            if (this.getX().equals(o.getX()))
                return -1;
            else
                return 1;
        } else {
            return 1;
        }
    }
}
