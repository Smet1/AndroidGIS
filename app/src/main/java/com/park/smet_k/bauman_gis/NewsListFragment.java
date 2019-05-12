package com.park.smet_k.bauman_gis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

//        dbHelper = new DBWorker(getActivity());

//        List<NewsModel> listToShow = AppComponent.getInstance().dbWorker.select(dbHelper, "all", "");
        List<News> listToShow = new ArrayList<>();
        listToShow.add(new News("kek1", "kek2", new java.util.Date(29368498236L)));
        listToShow.add(new News("kek2", "kek3", new java.util.Date(2936848236L)));

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
