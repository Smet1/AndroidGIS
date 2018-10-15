package com.example.smet_k.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        TextInputEditText textView = findViewById(R.id.input);

        final BroadcastReceiver timerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final String action = intent.getAction();
                if (action == null) {
                    throw new NullPointerException("Timer action is null");
                }
                switch (action) {
                    case SaveService.ACTION_LOAD:
                        TextView tw = findViewById(R.id.out);
                        tw.setText(intent.getStringExtra(SaveService.EXTRA_MSG));
                        break;
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveService.class);
                intent.setAction(SaveService.ACTION_SAVE);
                intent.putExtra(SaveService.EXTRA_MSG, textView.getText().toString());
                startService(intent);
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveService.class);
                intent.setAction(SaveService.ACTION_LOAD);
                startService(intent);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(SaveService.ACTION_LOAD);
        LocalBroadcastManager.getInstance(this).registerReceiver(timerReceiver, filter);
    }
}
