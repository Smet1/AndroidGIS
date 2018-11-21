package com.example.smet_k.bauman_gis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBWorker extends SQLiteOpenHelper {

    private final String LOG_TAG = "DBWorker";

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

    public void insert(DBWorker dbWorker, Integer point_from, Integer point_to) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbWorker.getWritableDatabase();

        // подключаемся к БД
        Log.d(LOG_TAG, "--- Insert in RecentRoutes: ---");
        cv.put("point_from", point_from);
        cv.put("point_to", point_to);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("RecentRoutes", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
    }

    public List<Route> select(SQLiteDatabase db, String tag, String query) {
        List<Route> listToShow = new ArrayList<>();
        ContentValues cv = new ContentValues();
        Cursor c = null;

        switch (tag) {
            case "all": {
                c = db.query("RecentRoutes", null, null, null, null, null, null);
                Log.d(LOG_TAG, "--- RecentRoutes select all: ---");
                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int point_from = c.getColumnIndex("point_from");
                    int point_to = c.getColumnIndex("point_to");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", from = " + c.getString(point_from) +
                                        ", to = " + c.getString(point_to));
                        // переход на следующую строку

                        listToShow.add(new Route(Integer.parseInt(c.getString(point_from)), Integer.parseInt(c.getString(point_to))));

                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
            }
        }

        if (c != null) {
            c.close();
        }
        return listToShow;
    }

    //insert
    //selet
    //update
}
