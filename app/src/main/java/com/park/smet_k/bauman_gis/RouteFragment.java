package com.park.smet_k.bauman_gis;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteFragment extends Fragment {
    // просчет маршрута при создании фрагмента
    String LOG_TAG = "RouteFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue, container, false);
        return view;
    }

    public static RouteFragment newInstance(int from, int to) {
        Bundle args = new Bundle();
        args.putInt("from", from);
        args.putInt("to", to);

        RouteFragment fragment = new RouteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Integer from = 0;
        Integer to = 0;

        Bundle args = getArguments();
        if (args != null) {
            from = args.getInt("from");
            to = args.getInt("to");
        }

        TextView textView = view.findViewById(R.id.from);
        textView.setText(from.toString());

        textView = view.findViewById(R.id.to);
        textView.setText(to.toString());

        // фиксируем прибыль (уличная магия)
        ArrayList<Integer> route = AppComponent.getInstance().StairsGraph.dijkstra(from - 1, to - 1);

        StringBuilder result = new StringBuilder();
        for (Integer val : route) {
            val += 1;
            result.append(", ").append(val.toString());
        }

        result.append("|");

        for (int i = 0; i < route.size() - 1; i++) {
            result.append("run A star on: ");
            if (AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel().equals(AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getLevel())) {
                Log.d("kek", "same level, run A star on " + String.valueOf(route.get(i) + 1) + " " + String.valueOf(route.get(i + 1) + 1));
                result.append(route.get(i)).append(" ").append(route.get(i) + 1).append(", ");
            }
        }

        TextView routeView = view.findViewById(R.id.route);
        routeView.setText(result.toString());
    }
}

