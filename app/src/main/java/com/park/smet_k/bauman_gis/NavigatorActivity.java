package com.park.smet_k.bauman_gis;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.park.smet_k.bauman_gis.model.RouteModel;
import com.park.smet_k.bauman_gis.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigatorActivity extends AppCompatActivity {
    final String LOG_TAG = "NavigatorActivity";
    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";
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
            EditText check_edit = findViewById(R.id.InputFrom);
            Integer from;
            try {
                from = Integer.parseInt(check_edit.getText().toString());
                cur_from = from;
            } catch (NumberFormatException e) {
                from = 0;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid values!",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            check_edit = findViewById(R.id.InputTo);
            Integer to;
            try {
                to = Integer.parseInt(check_edit.getText().toString());
                cur_to = to;
            } catch (NumberFormatException e) {
                to = 0;
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid values!",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            // заносим данные в БД
            AppComponent.getInstance().dbWorker.insert(dbHelper, cur_from, cur_to);

            // пушим на сервер
            Callback<RouteModel> callback = new Callback<RouteModel>() {

                @Override
                public void onResponse(@NonNull Call<RouteModel> call, Response<RouteModel> response) {
                    RouteModel body = response.body();
                    if (body != null) {
                        Log.d(LOG_TAG, "--- Login OK body != null ---");

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Success, now login please",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Log.d(LOG_TAG, "--- Login OK body == null ---");

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Invalid login/password",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<RouteModel> call, Throwable t) {
                    Log.d(LOG_TAG, "--- Login ERROR onFailure ---");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Server Error",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    t.printStackTrace();
                }
            };

            SharedPreferences preferences = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

            Integer userId = preferences.getInt(KEY_OAUTH, -1);
            // avoid static error
            AppComponent.getInstance().bgisApi.pushRoute(new RouteModel(userId, cur_from, cur_to)).enqueue(callback);

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


