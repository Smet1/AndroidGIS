package com.park.smet_k.bauman_gis.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.park.smet_k.bauman_gis.activity.MainActivity;
import com.park.smet_k.bauman_gis.R;

public class ServerNewsFragment extends Fragment {

    public static ServerNewsFragment newInstance() {

        Bundle args = new Bundle();

        ServerNewsFragment fragment = new ServerNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("News");
        return inflater.inflate(R.layout.server_news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.TopFrameNews, NewsListFragment.newInstance())
                .commit();
    }
}
