package com.park.smet_k.bauman_gis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    private DBWorker dbWorker;

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbWorker = new DBWorker(getActivity());
        Button reset = getActivity().findViewById(R.id.clear_button);

        reset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AppComponent.getInstance().dbWorker.truncate(dbWorker);
                Toast toast = Toast.makeText(getActivity(),
                        "История маршрутов очищена!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
