package com.example.smet_k.bauman_gis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RouteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    }
}
