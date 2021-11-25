package com.example.jbalpha.eazkitv8.BroadCastReceiverMine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.jbalpha.eazkitv8.AllSessions;
import com.example.jbalpha.eazkitv8.FloatingWindow;
import com.example.jbalpha.eazkitv8.LetsStartWhitening;
import com.example.jbalpha.eazkitv8.Models.NotiModel;
import com.example.jbalpha.eazkitv8.PopUpBackgroundActivity;
import com.example.jbalpha.eazkitv8.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    private Intent classIntetnt;
    private Context context;
    private SharedPreferences sharedpreferences;
    private String notiType, switchValue;
    private Bundle bundle;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("alaramTrigger", "Alarm Triggered");
        this.context = context;
        sharedpreferences = context.getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
        switchValue = sharedpreferences.getString("noti_switch", "1");
        notiType = sharedpreferences.getString("noti_type", "1");


        Log.d("alaramTrigger", "Alarm Triggered" + "      type= " + notiType);
        if (switchValue.equalsIgnoreCase("1")) {

            switch (notiType) {

                case "Image alert on screen":

                    CreateNoti();
                    break;
                case "Sound alert":
                    CreateNoti();
                    break;
                case "Sound and Image alert":
                    CreateNoti();

                    break;


            }


        }


    }

    private void openFloatingWindow() {
//        Intent intent = new Intent(context, FloatingWindow.class);
//        context.stopService(intent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, FloatingWindow.class));
        } else {
//            context.startService(new Intent(context, FloatingWindow.class));
            Intent serviceIntent = new Intent(context, FloatingWindow.class);
            context.startService(serviceIntent);

        }
//        context.startService(new Intent(context, FloatingWindow.class));
//        context.startService(intent);
    }


    private void CreateNoti() {


        Random r = new Random();
        int randomNo = r.nextInt(9999999 - 0) + 0;
        String NOTIFICATION_CHANNEL_ID = String.valueOf(randomNo);
        Intent intent = new Intent(context, LetsStartWhitening.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500,
                    1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.noti_icon_48x48)
                .setContentTitle("EAZIKIT")
                .setContentText("Whiten your teeth kit by Briyte");

        builder.setContentIntent(pendingIntent);
        notificationManager.notify(randomNo, builder.build());

    }

}