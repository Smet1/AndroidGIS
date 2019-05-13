package com.park.smet_k.bauman_gis.service;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.activity.MainActivity;

public class MessageService extends FirebaseMessagingService {

    private static final String TAG = "NOTIFICATION";
    private static final int NOTIFICATION_ID_SIMPLE = 1;
    private Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + body);

            showMessageNotification(title, body);

        }

    }

    public void showMessageNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null)
            return;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_DEFAULT);

        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        manager.notify(NOTIFICATION_ID_SIMPLE, builder.build());
    }
}
