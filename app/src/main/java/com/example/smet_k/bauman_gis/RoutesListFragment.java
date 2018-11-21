package com.example.smet_k.bauman_gis;

//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class RouteListFragment extends Fragment {
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public static RouteListFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        RouteListFragment fragment = new RouteListFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.recycler_fragment_route_list, container, false);
//        return view;
//    }
//}


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

        AdapterRoutesList numbersAdapter = new AdapterRoutesList(getContext(), this::onItemClick);

        RecyclerView numbers = view.findViewById(R.id.route_list);
        numbers.setLayoutManager(new LinearLayoutManager(getContext()));
        numbers.setAdapter(numbersAdapter);
        numbers.setHasFixedSize(true);

        Bundle arguments = getArguments();
        if (arguments != null) {
            recentRoutes = (List<Route>) arguments.get(KEY);
        }

        for (Route i : recentRoutes) {
            numbersAdapter.add(i);
        }
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
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "=== ON PAUSE === ");

        super.onPause();
    }

}
