package com.park.smet_k.bauman_gis;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.park.smet_k.bauman_gis.model.Message;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

// TODO(): переделать настройки на PreferenceFragmentCompat
// https://developer.android.com/guide/topics/ui/settings/?hl=ru

public class SettingsFragment extends Fragment {
    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";
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
        Button reset = Objects.requireNonNull(getActivity()).findViewById(R.id.clear_button);
//        Button reset = getActivity().findViewById(R.id.clear_button);

        reset.setOnClickListener(v -> {
            AppComponent.getInstance().dbWorker.truncate(dbWorker);
            SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
//            SharedPreferences preferences = getContext().getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

            Integer userId = preferences.getInt(KEY_OAUTH, -1);

            Callback<Message> callback = new Callback<Message>() {
                @Override
                public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                }

                @Override
                public void onFailure(@NonNull Call<Message> call, Throwable t) {
                    t.printStackTrace();
                }

            };

            // avoid static error
            AppComponent.getInstance().bgisApi.deleteHistory(userId).enqueue(callback);

            Toast toast = Toast.makeText(getActivity(),
                    "История маршрутов очищена!",
                    Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
