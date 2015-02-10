package com.shivamdev.notificationalarmmanager;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    Button showNotification, stopNotification, alert;

    NotificationManager notificationManager;

    boolean isNotificationActive = false;

    int notificationId = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showNotification = (Button) findViewById(R.id.showNotificationBut);
        stopNotification = (Button) findViewById(R.id.stopNotificationBut);
        alert = (Button) findViewById(R.id.alertButton);
    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(View view) {

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Message")
                .setContentText("New Message")
                .setTicker("Alert New Message")
                .setSmallIcon(R.drawable.alertred);

        Intent moreInfoIntent = new Intent(this, MoreInfoNotification.class);

        TaskStackBuilder tsb = TaskStackBuilder.create(this);

        tsb.addParentStack(MoreInfoNotification.class);
        tsb.addNextIntent(moreInfoIntent);

        PendingIntent pendingIntent = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notiBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notiBuilder.build());

        isNotificationActive = true;
    }

    public void stopNotification(View view) {

        if(isNotificationActive) {
            notificationManager.cancel(notificationId);
        }

    }

    public void setAlarm(View view) {

        Long alertTime = new GregorianCalendar().getTimeInMillis() + 5 *1000;

        Intent alertIntent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));


    }
}
