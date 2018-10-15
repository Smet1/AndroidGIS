package com.example.smet_k.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class SaveService extends Service {

    public static final String ACTION_SAVE = "save";
    public static final String ACTION_LOAD = "load";
    public static final String EXTRA_MSG = "msg";

    String message = null;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

        final String action = intent.getAction();

        if (action == null) {
            throw new NullPointerException("Timer action is null");
        }
        switch (action) {
            case ACTION_SAVE:

                this.message = intent.getStringExtra(EXTRA_MSG);
                intent.getExtras();
                break;

            case ACTION_LOAD:
                Intent msg_intent = new Intent(ACTION_LOAD);
                msg_intent.putExtra(EXTRA_MSG, this.message);
                broadcastManager.sendBroadcast(msg_intent);
                break;
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
