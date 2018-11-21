package com.example.smet_k.bauman_gis;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoutesListFragment extends Fragment {

    private final static String KEY = "list";

    private List<Route> recentRoutes = new ArrayList<>();
    RecyclerView numbers;
    DBWorker dbHelper;


    public static RoutesListFragment newInstance(Collection<Route> in) {
        RoutesListFragment myFragment = new RoutesListFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, (Serializable) in);
        myFragment.setArguments(bundle);

//        recentRoutes.addAll(in);

        return myFragment;
    }

    public static RoutesListFragment newInstance() {
        RoutesListFragment myFragment = new RoutesListFragment();

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
        return inflater.inflate(R.layout.recycler_fragment_route_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        numbers = view.findViewById(R.id.route_list);
//        AdapterRoutesList numbersAdapter = new AdapterRoutesList(getContext(), this::onItemClick);
//
//        RecyclerView numbers = view.findViewById(R.id.route_list);
//        numbers.setLayoutManager(new LinearLayoutManager(getContext()));
//        numbers.setAdapter(numbersAdapter);
//        numbers.setHasFixedSize(true);
//
//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            recentRoutes = (List<Route>) arguments.get(KEY);
//        }
//
//        for (Route i : recentRoutes) {
//            numbersAdapter.add(i);
//        }
    }

    private void onItemClick(Route i) {


        // getChildFragmentManager не работает, нельзя бросить данные в другой фрагмент
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Fragment bottom = getFragmentManager().findFragmentById(R.id.TopFrame);

        RouteFragment routeFragment = RouteFragment.newInstance(i.getFrom(), i.getTo());

        // выставляю значения в эдит текст в нижнем баре
        EditText editText = (EditText) getActivity().findViewById(R.id.InputFrom);
        editText.setText(Integer.toString(i.getFrom()));

        editText = (EditText) getActivity().findViewById(R.id.InputTo);
        editText.setText(Integer.toString(i.getTo()));

        assert bottom != null;
        transaction.remove(bottom);
        transaction.add(R.id.TopFrame, routeFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }


    final String LOG_TAG = "RoutesList";
    @Override
    public void onResume() {
        Log.d(LOG_TAG, "=== ON RESUME === ");

        List<Route> listToShow = new ArrayList<>();
        dbHelper = new DBWorker(getActivity());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("RecentRoutes", null, null, null, null, null, null);
        Log.d(LOG_TAG, "--- NavigatorActivity.onCreate: ---");
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
        c.close();

        AdapterRoutesList numbersAdapter = new AdapterRoutesList(getContext(), this::onItemClick);

//        RecyclerView numbers = view.findViewById(R.id.route_list);
        numbers.setLayoutManager(new LinearLayoutManager(getContext()));
        numbers.setAdapter(numbersAdapter);
        numbers.setHasFixedSize(true);

        Bundle arguments = getArguments();
        if (arguments != null) {
            recentRoutes = listToShow;
        }

        for (Route i : recentRoutes) {
            numbersAdapter.add(i);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "=== ON PAUSE === ");

        super.onPause();
    }

}
