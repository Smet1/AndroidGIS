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
import android.widget.EditText;

import java.util.jar.JarException;

public class NavigatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        final Button startNewActivityBtn = findViewById(R.id.Calculate);

        startNewActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int from = Integer.parseInt(findViewById(R.id.InputFrom).toString());
                EditText check_edit = (EditText) findViewById(R.id.InputFrom);
                Integer from;
                try {
                    from = Integer.parseInt(check_edit.getText().toString());
                } catch (NumberFormatException e) {
                    from = 0;
                }

                check_edit = (EditText) findViewById(R.id.InputTo);
                Integer to;
                try {
                    to = Integer.parseInt(check_edit.getText().toString());
                } catch (NumberFormatException e) {
                    to = 0;
                }
                toggleState();
            }
        });

    }
    private void toggleState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
//        bundle.putInt("from", 12);
        Fragment bottom = getSupportFragmentManager().findFragmentById(R.id.TopFrame);

//        RouteFragment routeFragment = new RouteFragment();  // fragment to show
//        routeFragment.setArguments(bundle);

        RouteFragment routeFragment = RouteFragment.newInstance(5, 12);

        if (bottom != null && bottom.isAdded()) {
            transaction.remove(bottom);
            transaction.add(R.id.TopFrame, routeFragment);
        } else {
            transaction.add(R.id.TopFrame, routeFragment);
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

}


