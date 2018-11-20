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


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class RoutesListFragment extends Fragment {

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

        AdapterRoutesList numbersAdapter = new AdapterRoutesList();

        RecyclerView numbers = view.findViewById(R.id.route_list);
        numbers.setLayoutManager(new GridLayoutManager(getContext(), getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3));
        numbers.setAdapter(numbersAdapter);
        numbers.setHasFixedSize(true);


        for (Integer i = 1000; i > 0; --i) {
            numbersAdapter.add(i);
        }
    }

    private void onItemClick(Integer i) {
//        getFragmentManager().beginTransaction()
//                .replace(R.id.container, NumberFragment.newInstance(i))
//                .addToBackStack(null)
//                .commit();
    }

}
