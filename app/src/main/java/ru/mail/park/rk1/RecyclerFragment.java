package ru.mail.park.rk1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import ru.mail.park.rk1.appComponent.AppComponent;

public class RecyclerFragment extends Fragment {
    private final static String KEY = "kek";
    private final static Integer DEFAULT = 100;
    private int last = DEFAULT;


    public static RecyclerFragment newInstance() {
        RecyclerFragment myFragment = new RecyclerFragment();

        Bundle bundle = new Bundle();
        myFragment.setArguments(bundle);

        return myFragment;
    }

    public static RecyclerFragment newInstance(int i) {
        RecyclerFragment myFragment = new RecyclerFragment();

        Bundle bundle = new Bundle();
        myFragment.setArguments(bundle);

        myFragment.last = i;

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button button = view.findViewById(R.id.add_number);
        button.setOnClickListener(v -> {
            RecyclerView numbers = view.findViewById(R.id.numbers_list);
            NumbersAdapter adapter = (NumbersAdapter) numbers.getAdapter();

            assert adapter != null;
            int newLen = adapter.getItemCount() + 1;
            adapter.add(newLen);
            last = newLen;
            AppComponent.getInstance().setLastNumber(last);
        });

        NumbersAdapter numbersAdapter = new NumbersAdapter(getContext(), this::onItemClick);

        RecyclerView numbers = view.findViewById(R.id.numbers_list);
        numbers.setLayoutManager(new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3));
        numbers.setAdapter(numbersAdapter);
        numbers.setHasFixedSize(true);


        for (Integer i = 1; i <= AppComponent.getInstance().getLastNumber(); i++) {
            numbersAdapter.add(i);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY, last);

        MainActivity m = ((MainActivity) getActivity());
        if (m != null) {
            m.SetLastNumber(last);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            last = savedInstanceState.getInt(KEY);
        } else {
            last = DEFAULT;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

        MainActivity m = ((MainActivity) getActivity());
        if (m != null) {
            m.SetLastNumber(last);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onItemClick(Integer i) {
        Objects.requireNonNull(getFragmentManager()).beginTransaction()
                .replace(R.id.container, NumberFragment.newInstance(i))
                .addToBackStack(null)
                .commit();
    }
}