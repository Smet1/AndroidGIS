package com.park.smet_k.bauman_gis;

public class Route {
    private Integer from;
    private Integer to;

    // TODO(): после алгоритма построения хранить маршрут
//    private string route;
    public Route(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }
    public int getTo() {
        return to;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
    public void setTo(Integer to) {
        this.to = to;
    }
}
