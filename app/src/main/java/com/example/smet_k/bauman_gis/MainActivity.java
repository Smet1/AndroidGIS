package com.example.smet_k.bauman_gis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton startNewActivityBtn = findViewById(R.id.imageButton);

        startNewActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent navIntent = new Intent(MainActivity.this, NavigatorActivity.class);

                startActivity(navIntent);
            }
        });
    }


}
