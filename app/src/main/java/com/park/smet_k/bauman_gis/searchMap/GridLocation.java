package com.park.smet_k.bauman_gis.searchMap;

public class GridLocation {
    private Integer x;
    private Integer y;

    public GridLocation(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public int compare(GridLocation o) {
//        if (this.getX() < o.getX() && this.getY() < o.getY()) {
//            return 0;
//        } else {
//            return 1;
//        }

        if (this.getX().equals(o.getX()) && this.getY().equals(o.getY())) {
            return 0;
        }

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

//        return (this.getX() < o.getX()) ? -1 : ((this.getY().equals(o.getY())) ? )
    }
}
