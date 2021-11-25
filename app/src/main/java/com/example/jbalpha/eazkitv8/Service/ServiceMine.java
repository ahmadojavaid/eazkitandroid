package com.example.jbalpha.eazkitv8.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.jbalpha.eazkitv8.BroadCastReceiverMine.AlarmReceiver;
import com.example.jbalpha.eazkitv8.Models.NotiModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceMine extends Service {
    private String sessionTime;
    private SharedPreferences sharedpreferences;

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
        sessionTime = sharedpreferences.getString("session_time", "");

        return START_STICKY;
    }




}
