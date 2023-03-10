package com.example.lab09gohome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Build;
import android.app.NotificationChannel;
import android.view.View;
import android.widget.Button;

import static android.content.Intent.ACTION_MAIN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        PendingIntent contentIntent = PendingIntent.getActivity(this,0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "notification_ch_id");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Go Home Application");
        builder.setContentText("Go Home");
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(false);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            CharSequence channelName = "notification_ch_id";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("notification_ch_id",
                    channelName, importance);
            channel.setDescription(description);

            // notification channel을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        else
            builder.setSmallIcon(R.mipmap.ic_launcher);

        assert notificationManager !=null;

        notificationManager.notify(0,builder.build());
    }
}