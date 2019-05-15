package com.park.smet_k.bauman_gis.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.activity.MainActivity;
import com.park.smet_k.bauman_gis.compontents.AppComponent;
import com.park.smet_k.bauman_gis.database.DBWorker;
import com.park.smet_k.bauman_gis.model.RouteModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NavigatorFragment extends Fragment {
    private final String LOG_TAG = "NavigatorActivity";
    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";
    private DBWorker dbHelper;
    private Integer cur_from = 0;
    private Integer cur_to = 0;

    public static NavigatorFragment newInstance() {
        NavigatorFragment myFragment = new NavigatorFragment();

        Bundle bundle = new Bundle();
        myFragment.setArguments(bundle);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Navigation");

        return inflater.inflate(R.layout.activity_navigator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button startNewActivityBtn = view.findViewById(R.id.Calculate);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBWorker(getActivity());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.TopFrame, RoutesListFragment.newInstance())
                .commit();


        startNewActivityBtn.setOnClickListener(v -> {
            EditText check_edit = view.findViewById(R.id.InputFrom);
            Integer from;
            try {
                from = Integer.parseInt(check_edit.getText().toString());
                cur_from = from;
            } catch (NumberFormatException e) {
                check_edit.setError("Invalid value");
                check_edit.requestFocus();
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        "Invalid values!",
//                        Toast.LENGTH_SHORT);
//                toast.show();
                return;
            }

            check_edit = view.findViewById(R.id.InputTo);
            Integer to;
            try {
                to = Integer.parseInt(check_edit.getText().toString());
                cur_to = to;
            } catch (NumberFormatException e) {
                check_edit.setError("Invalid value");
                check_edit.requestFocus();
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        "Invalid values!",
//                        Toast.LENGTH_SHORT);
//                toast.show();
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

//                        Toast toast = Toast.makeText(getContext(),
//                                "Success, now login please",
//                                Toast.LENGTH_SHORT);
//                        toast.show();
                    } else {
                        Log.d(LOG_TAG, "--- Login OK body == null ---");

//                        Toast toast = Toast.makeText(getContext(),
//                                "Invalid login/password",
//                                Toast.LENGTH_SHORT);
//                        toast.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RouteModel> call, Throwable t) {
                    Log.d(LOG_TAG, "--- Login ERROR onFailure ---");
                    Toast toast = Toast.makeText(getContext(),
                            "Server Error",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    t.printStackTrace();
                }
            };

            SharedPreferences preferences = getContext().getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

            Integer userId = preferences.getInt(KEY_OAUTH, -1);
            // avoid static error
            AppComponent.getInstance().bgisApi.pushRoute(new RouteModel(userId, cur_from, cur_to)).enqueue(callback);

            toggleState();
        });

    }

    private void toggleState() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Fragment bottom = getActivity().getSupportFragmentManager().findFragmentById(R.id.TopFrame);

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
