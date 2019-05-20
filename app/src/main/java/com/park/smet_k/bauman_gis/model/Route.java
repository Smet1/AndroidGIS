package com.park.smet_k.bauman_gis.model;

public class Route {
    private String from;
    private String to;

    // TODO(): после алгоритма построения хранить маршрут
//    private string route;
    public Route(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

