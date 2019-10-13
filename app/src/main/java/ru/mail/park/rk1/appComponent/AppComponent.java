package ru.mail.park.rk1.appComponent;

import android.content.Context;

public class AppComponent {
    private int LastNumber = 0;

    private static AppComponent instance = null;

    public static AppComponent getInstance() {
        return instance;
    }

    private AppComponent(Context context) {
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AppComponent(context);
        }
    }

    public int getLastNumber() {
        return LastNumber;
    }

    public void setLastNumber(int lastNumber) {
        LastNumber = lastNumber;
    }
}
