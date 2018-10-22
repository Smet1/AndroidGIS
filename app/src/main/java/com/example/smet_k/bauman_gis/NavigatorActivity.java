package com.example.smet_k.bauman_gis;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NavigatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        final Button startNewActivityBtn = findViewById(R.id.Calculate);

        startNewActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState();
            }
        });

    }
    private void toggleState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment bottom = getSupportFragmentManager().findFragmentById(R.id.TopFrame);

        if (bottom != null && bottom.isAdded()) {
            transaction.remove(bottom);
            transaction.add(R.id.TopFrame, new RouteFragment());
        } else {
            transaction.add(R.id.TopFrame, new RouteFragment());
        }



        transaction.addToBackStack(null);
        transaction.commit();
    }
}
