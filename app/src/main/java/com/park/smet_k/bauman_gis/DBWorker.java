package com.park.smet_k.bauman_gis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.park.smet_k.bauman_gis.model.Stairs;

import java.util.ArrayList;
import java.util.List;

public class DBWorker extends SQLiteOpenHelper {
    // Logcat tag
    private final String LOG_TAG = "DBWorker";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GISManager";

    // Table Names
    private static final String TABLE_RECENT_ROUTES = "RecentRoutes";
    private static final String TABLE_MAP_STAIRS = "map_stairs";

    // RECENT_ROUTES Table - column names
    private static final String KEY_RR_ID = "id INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String KEY_RR_POINT_FROM = "point_from INTEGER";
    private static final String KEY_RR_POINT_TO = "point_to INTEGER";

    // MAP_STAIRS Table - column names
    private static final String KEY_MS_ID = "id INTEGER";
    private static final String KEY_MS_X = "x INTEGER";
    private static final String KEY_MS_Y = "y INTEGER";
    private static final String KEY_MS_LEVEL = "level INTEGER";
    private static final String KEY_MS_OPEN = "open INTEGER";

    // Table Create Statements
    // RECENT_ROUTES table create statement
    private static final String CREATE_TABLE_RECENT_ROUTES = "CREATE TABLE " + TABLE_RECENT_ROUTES +
            "( " + KEY_RR_ID + ", " + KEY_RR_POINT_FROM + ", " + KEY_RR_POINT_TO + ")";

    // MAP_STAIRS table create statement
    private static final String CREATE_TABLE_MAP_STAIRS = "CREATE TABLE " + TABLE_MAP_STAIRS +
            "( " + KEY_MS_ID + ", " + KEY_MS_X + ", " + KEY_MS_Y + ", " + KEY_MS_LEVEL +
            ", " + KEY_MS_OPEN + ")";

    public DBWorker(Context context) {
//        private final String table = "RecentRoutes";

        // конструктор суперкласса
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL(CREATE_TABLE_RECENT_ROUTES);
        db.execSQL(CREATE_TABLE_MAP_STAIRS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP_STAIRS);

        // create new tables
        onCreate(db);
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

    public List<Route> select(DBWorker dbWorker, String tag, String query) {
        List<Route> listToShow = new ArrayList<>();
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbWorker.getWritableDatabase();
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

    public void truncate(DBWorker dbWorker) {
        Log.d(LOG_TAG, "--- RecentRoutes truncate: ---");

        SQLiteDatabase db = dbWorker.getWritableDatabase();
        db.execSQL("delete FROM " + "RecentRoutes");

        Log.d(LOG_TAG, "--- RecentRoutes truncate DONE ---");
    }

    public void InsertStairs(List<Stairs> stairsList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Stairs val : stairsList) {
            ContentValues values = new ContentValues();

//            KEY_MS_ID
//            KEY_MS_X
//            KEY_MS_Y
//            KEY_MS_LEVEL
//            KEY_MS_OPEN

            values.put("id", val.getId());
            values.put("x", val.getX());
            values.put("y", val.getY());
            values.put("level", val.getLevel());
            values.put("open", val.getOpen() ? 1 : 0);

            long rowID = db.insert(TABLE_MAP_STAIRS, null, values);
            Log.d(LOG_TAG, "row inserted map_stairs, ID = " + rowID);
        }
    }

    public void TruncateStairs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MAP_STAIRS);
    }

    public List<Stairs> GetAllStairs() {
        List<Stairs> stairs = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MAP_STAIRS;

        Log.e(LOG_TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Stairs st = new Stairs();
                st.setId(c.getInt((c.getColumnIndex(KEY_MS_ID))));
                st.setX((c.getInt(c.getColumnIndex(KEY_MS_X))));
                st.setY((c.getInt(c.getColumnIndex(KEY_MS_Y))));
                st.setLevel((c.getInt(c.getColumnIndex(KEY_MS_LEVEL))));

                // analyse true or false from int
                int tmp = c.getInt(c.getColumnIndex(KEY_MS_OPEN));
                st.setOpen(tmp == 1);

                // adding to stairs list
                stairs.add(st);
            } while (c.moveToNext());
        }

        return stairs;
    }

    //insert
    //select
    //update
}
