package com.example.termproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Set;

public class SetAlert extends AppCompatActivity {

    private long curTime = System.currentTimeMillis();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_alert);
    }

    public void mOnClick_alert(View v){
        Intent intentAlert = new Intent();
        switch (v.getId()){
            case R.id.btn_alert_back:
                intentAlert.setClass(SetAlert.this, Daily.class);
                break;
            case R.id.btn_set_alert:

                long second = curTime / 1000;
                long minute = curTime / (1000 * 60);
                long hour = curTime / (1000 * 60 * 60);

                EditText e_hour = (EditText)findViewById(R.id.edit_set_hour);
                EditText e_minute = (EditText)findViewById(R.id.edit_set_minute);

                long m_hour = Long.parseLong(e_hour.getText().toString());
                long m_minute = Long.parseLong(e_minute.getText().toString());

                long time = ((((m_hour-hour)*60+(m_minute-minute))*60)+second)*1000;

                NotificationManager notificationManager =(NotificationManager)SetAlert.this.
                        getSystemService(SetAlert.this.NOTIFICATION_SERVICE);
                Intent intent = new Intent(this,Daily.class);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                        "notification_ch_id");
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pend = PendingIntent.getActivity(
                        SetAlert.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setWhen(time);
                builder.setContentTitle("식단 어플");
                builder.setContentText("식단을 올려주세요.");
                builder.setContentIntent(pend);
                builder.setAutoCancel(true);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    CharSequence channelName = "notification channel";
                    String description = "오레오 이상을 위한 것임";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    NotificationChannel channel = new NotificationChannel("notification_ch_id",
                            channelName, importance);
                    channel.setDescription(description);

                    assert notificationManager != null;
                    notificationManager.createNotificationChannel(channel);
            }
                else
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(1, builder.build());
                break;
        }
        startActivity(intentAlert);
    }
}
