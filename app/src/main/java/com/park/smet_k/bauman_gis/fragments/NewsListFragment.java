package com.park.smet_k.bauman_gis.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.compontents.AppComponent;
import com.park.smet_k.bauman_gis.model.News;
import com.park.smet_k.bauman_gis.recycler.AdapterNewsList;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {
    final String LOG_TAG = "NewsList";
    private final static String KEY = "list";

    private List<News> newsArrayList = new ArrayList<>();
    private RecyclerView numbers;

    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();

        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_fragment_news_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        numbers = view.findViewById(R.id.news_list);
    }

    private void onItemClick(News i) {
        // TODO(): здесь пока ничего нет, если будет надо, взять из RoutesListFragment
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "=== ON RESUME === ");

        List<News> listToShow = AppComponent.getInstance().dbWorker.GetAllNews();

        AdapterNewsList adapterNewsList = new AdapterNewsList(getContext(), this::onItemClick);

        numbers.setLayoutManager(new LinearLayoutManager(getContext()));
        numbers.setAdapter(adapterNewsList);
        numbers.setHasFixedSize(true);

        Bundle arguments = getArguments();
        if (arguments != null) {
            newsArrayList = listToShow;
        }

        for (News i : newsArrayList) {
            adapterNewsList.add(i);
        }

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "=== ON PAUSE === ");

        super.onPause();
    }
}
