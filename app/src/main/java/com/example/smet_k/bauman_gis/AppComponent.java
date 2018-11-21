package com.example.smet_k.bauman_gis;

import android.content.Context;

public class AppComponent {

        private static AppComponent instance = null;
        public DBWorker dbWorker;

        public static AppComponent getInstance() {
            return instance;
        }

        private AppComponent(Context context) {
            // DBWorker init
            dbWorker = new DBWorker(context);
        }

        public static void init(Context context) {
            if (instance == null) {
                instance = new AppComponent(context);
            }
        }
}
