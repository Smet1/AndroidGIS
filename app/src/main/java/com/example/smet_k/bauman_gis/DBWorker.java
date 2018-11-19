package com.example.smet_k.bauman_gis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBWorker extends SQLiteOpenHelper {

    private final String LOG_TAG = "NavigatorActivity";

    public DBWorker(Context context) {
//        private final String table = "RecentRoutes";

        // конструктор суперкласса
        super(context, "RecentRoutes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table RecentRoutes ("
                + "id integer primary key autoincrement,"
                + "point_from integer,"
                + "point_to integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
