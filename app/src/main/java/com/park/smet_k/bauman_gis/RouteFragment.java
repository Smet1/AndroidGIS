package com.park.smet_k.bauman_gis;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.park.smet_k.bauman_gis.model.StairsLink;

import java.util.ArrayList;
import java.util.List;

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


        // TODO(): размер графа и количество связей
        WeightedGraph graph = new WeightedGraph(AppComponent.getInstance().StairsArray.size(),
                AppComponent.getInstance().StairsLinksArray.size());

        // TODO(): не генерить граф здесь
//        List<StairsLink> links = AppComponent.getInstance().StairsLinksArray;
//        for (StairsLink val : links) {
//            // id в бд начинаются с 1
//
//            // проверка на валидность связи (открыто или нет)
//            if (val.getOpen()) {
//                graph.addEdge(val.getIdFrom() - 1, val.getIdTo() - 1, val.getWeight());
//            }
//        }


        // фиксируем прибыль (уличная магия)
        ArrayList<Integer> route = AppComponent.getInstance().StairsGraph.dijkstra(from - 1, to - 1);

        String result = "";
        for (Integer val : route) {
            val += 1;
            result += (", " + val.toString());
        }

        TextView routeView = view.findViewById(R.id.route);
        routeView.setText(result);
    }
}

