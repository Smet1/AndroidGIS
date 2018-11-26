package com.park.smet_k.bauman_gis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NavigatorActivity extends AppCompatActivity {
    final String LOG_TAG = "NavigatorActivity";
    DBWorker dbHelper;
    Integer cur_from = 0;
    Integer cur_to = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        final Button startNewActivityBtn = findViewById(R.id.Calculate);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBWorker(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.TopFrame, RoutesListFragment.newInstance())
                .commit();


        startNewActivityBtn.setOnClickListener(v -> {
            EditText check_edit = (EditText) findViewById(R.id.InputFrom);
            Integer from;
            try {
                from = Integer.parseInt(check_edit.getText().toString());
                cur_from = from;
            } catch (NumberFormatException e) {
                from = 0;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "А хуй тебе!",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            check_edit = (EditText) findViewById(R.id.InputTo);
            Integer to;
            try {
                to = Integer.parseInt(check_edit.getText().toString());
                cur_to = to;
            } catch (NumberFormatException e) {
                to = 0;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "А хуй тебе!",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            // заносим данные в БД
            AppComponent.getInstance().dbWorker.insert(dbHelper, cur_from, cur_to);

            toggleState();
        });

    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "=== ON RESUME === ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "=== ON PAUSE === ");

        super.onPause();
    }

    private void toggleState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Fragment bottom = getSupportFragmentManager().findFragmentById(R.id.TopFrame);

        RouteFragment routeFragment = RouteFragment.newInstance(cur_from, cur_to);

        if (bottom != null && bottom.isAdded()) {
            transaction.remove(bottom);
            Log.d(LOG_TAG, "=== REMOVED FRAGMENT === ");
            transaction.add(R.id.TopFrame, routeFragment);
        } else {
            transaction.add(R.id.TopFrame, routeFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

}


