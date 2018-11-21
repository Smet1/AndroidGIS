package com.example.smet_k.bauman_gis;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent.init(this);
    }
}
