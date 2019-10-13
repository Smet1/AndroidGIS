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
        });

        NumbersAdapter numbersAdapter = new NumbersAdapter(getContext(), this::onItemClick);

        RecyclerView numbers = view.findViewById(R.id.numbers_list);
        numbers.setLayoutManager(new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3));
        numbers.setAdapter(numbersAdapter);
        numbers.setHasFixedSize(true);


        for (Integer i = 1; i <= last; i++) {
            numbersAdapter.add(i);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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