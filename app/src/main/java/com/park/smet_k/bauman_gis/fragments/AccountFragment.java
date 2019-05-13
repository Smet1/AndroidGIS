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

public class AccountFragment extends Fragment {

    public static AccountFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Account");

        return inflater.inflate(R.layout.account_fragment, container, false);
    }
}
