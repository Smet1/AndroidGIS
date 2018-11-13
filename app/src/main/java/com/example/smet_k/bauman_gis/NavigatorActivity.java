package com.example.smet_k.bauman_gis;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.jar.JarException;

public class NavigatorActivity extends AppCompatActivity {
    final String LOG_TAG = "NavigatorActivity";
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        final Button startNewActivityBtn = findViewById(R.id.Calculate);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        startNewActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText check_edit = (EditText) findViewById(R.id.InputFrom);
                Integer from;
                try {
                    from = Integer.parseInt(check_edit.getText().toString());
                } catch (NumberFormatException e) {
                    from = 0;
                }

                check_edit = (EditText) findViewById(R.id.InputTo);
                Integer to;
                try {
                    to = Integer.parseInt(check_edit.getText().toString());
                } catch (NumberFormatException e) {
                    to = 0;
                }

                // создаем объект для данных
                ContentValues cv = new ContentValues();

                // подключаемся к БД
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Log.d(LOG_TAG, "--- Insert in RecentRoutes: ---");
                cv.put("point_from", from);
                cv.put("point_to", to);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("RecentRoutes", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);



                Log.d(LOG_TAG, "--- Rows in RecentRoutes: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("RecentRoutes", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
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
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();

                toggleState();
            }
        });

    }
    private void toggleState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Fragment bottom = getSupportFragmentManager().findFragmentById(R.id.TopFrame);

        RouteFragment routeFragment = RouteFragment.newInstance(5, 12);

        if (bottom != null && bottom.isAdded()) {
            transaction.remove(bottom);
            transaction.add(R.id.TopFrame, routeFragment);
        } else {
            transaction.add(R.id.TopFrame, routeFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
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

}


