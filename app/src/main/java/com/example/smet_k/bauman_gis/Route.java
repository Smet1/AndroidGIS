package com.example.smet_k.bauman_gis;

public class Route {
    private int from;
    private int to;

    // TODO(): после алгоритма построения хранить маршрут
//    private string route;
    public Route(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }
    public int getTo() {
        return to;
    }

    public void setFrom(int from) {
        this.from = from;
    }
    public void setTo(int to) {
        this.to = to;
    }
}
