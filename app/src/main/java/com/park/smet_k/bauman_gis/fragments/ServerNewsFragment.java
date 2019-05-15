package com.park.smet_k.bauman_gis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.activity.MainActivity;

import java.util.Objects;

public class ServerNewsFragment extends Fragment {
    private View view;
    private Button update_btn;
    private Animation animAlpha;

    public static Fragment newInstance() {
        Bundle args = new Bundle();

        ServerNewsFragment fragment = new ServerNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("News");

        animAlpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        view = inflater.inflate(R.layout.server_news_fragment, container, false);

        update_btn = Objects.requireNonNull(getActivity()).findViewById(R.id.update);
        assert update_btn != null;

//        update_btn.setOnClickListener(v -> {
//            AppComponent.getInstance().GetNewsInit();
//            update_btn.startAnimation(animAlpha);
//
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.TopFrameNews, NewsListFragment.newInstance())
//                    .commit();
//
//        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.TopFrameNews, NewsListFragment.newInstance())
                .commit();
    }
}
