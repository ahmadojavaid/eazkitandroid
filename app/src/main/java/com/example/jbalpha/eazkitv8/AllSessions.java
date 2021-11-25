package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.Adapters.AllSessionsRecyclerAdapter;
import com.example.jbalpha.eazkitv8.BroadCastReceiverMine.AlarmReceiver;
import com.example.jbalpha.eazkitv8.EventModels.MenuModel;
import com.example.jbalpha.eazkitv8.Models.AllSessionsModel;
import com.example.jbalpha.eazkitv8.Models.MenuFragment;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AllSessions extends BaseActivity {

    private RecyclerView sessionsRecycler;
    private AllSessionsRecyclerAdapter holder;
    private ArrayList<AllSessionsModel> models = new ArrayList<>();

    //inflating side menu
    private ImageView sessions_MenuButton;
    private Fragment menuFragment;
    private boolean isFragmentLoaded;
    private String userId;
    private SharedPreferences myPrefs;

    private String whitenDays, sessionTime, notificationTime, scheduledValue, createdValue, UpdatedValue;
    private String HoursNoti, MinsNoti;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private TextView cleaningTime_tv;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sessions);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);


        sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        myPrefs = getSharedPreferences("mysharedpref12", MODE_PRIVATE);
        userId = myPrefs.getString("user_id", "");


        Log.d("userid", userId);
        Window window = getWindow();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }

        sessionsRecycler = (RecyclerView) findViewById(R.id.sessionsRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sessionsRecycler.setLayoutManager(layoutManager);
        sessionsRecycler.setHasFixedSize(true);

        sessions_MenuButton = (ImageView) findViewById(R.id.sessions_MenuButton);
        sessions_MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFragmentLoaded == true) {
                    if (menuFragment != null) {

//                        if (menuFragment.isAdded()) {
                        hideFragment();
//                        }
                    }
                } else {
                    loadFragment();


                }
            }
        });


        cleaningTime_tv = findViewById(R.id.cleaningTime_tv);


        if (GlobalClass.isOnline(getApplicationContext())) {
            getAllSessions();
        } else {
            Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_SHORT).show();
        }


        ImageView goToLetsActivity;

        goToLetsActivity = findViewById(R.id.goToLetsActivity);
        goToLetsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(AllSessions.this, LetsStartWhitening.class);
                startActivity(i);

            }
        });


        if (GlobalClass.isOnline(getApplicationContext()) == true) {


            JSONAsyncTask task = new JSONAsyncTask();
            task.execute();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void getAllSessions() {
        final ProgressDialog progressDialog = new ProgressDialog(AllSessions.this);
        progressDialog.setMessage("Loading..!");
        progressDialog.show();
//        String userId = SharedPreManager.getInstance(AllSessions.this).getCurrentUserId();
        SharedPreferences myPrefs = getSharedPreferences("mysharedpref12", MODE_PRIVATE);
        String userId = myPrefs.getString("user_id", "");
        String url = Utils.BASE_URL + Utils.ALL_SESSIONS_URL + userId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("allSessionsRes", response);
                            JSONArray array = jsonObject.getJSONArray("Result");
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);


                                    AllSessionsModel model = new AllSessionsModel();
                                    model.setSession_id(object.getString("id"));
                                    model.setUser_id(object.getString("userId"));
                                    model.setSessionImage(object.getString("sessionImage"));
                                    model.setSessionRating(object.getString("sessionRating"));
                                    model.setTimeCarriedOut(object.getString("timeCarriedOut"));
                                    model.setActualTimeCarriedOut(object.getString("actualTimeCarriedOut"));
                                    model.setDate(object.getString("created_at"));

                                    models.add(model);
                                }
                                holder = new AllSessionsRecyclerAdapter(models, AllSessions.this, AllSessions.this);
                                sessionsRecycler.setAdapter(holder);
                            } else {
                                Toast.makeText(AllSessions.this, "No Sessions Found!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllSessions.this, "Unable to retrieve sessions data!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void hideFragment() {
        isFragmentLoaded = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.remove(menuFragment);

        fragmentTransaction.commit();
        // menu.setImageResource(R.drawable.ic_menu_black_24dp);
        // Toast.makeText(MainActivity.this,"Main Activity hide frag",Toast.LENGTH_LONG).show();


    }

    public void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        menuFragment = fm.findFragmentById(R.id.letStartWhiteningContainer);
        // menu.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
//        if (menuFragment == null) {
        menuFragment = new MenuFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.replace(R.id.letStartWhiteningContainer, menuFragment);
        fragmentTransaction.commit();
        //Toast.makeText(MainActivity.this,"Main Activity load frag",Toast.LENGTH_LONG).show();
//        }

        isFragmentLoaded = true;
    }

    @Override
    public void onBackPressed() {


        MenuFragment articleFrag = (MenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.letStartWhiteningContainer);

        if (articleFrag != null && articleFrag.isVisible()) {

            hideFragment();

        } else {
            finish();
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MenuModel event) {
        hideFragment();


    }


    private class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {


                String url = Utils.BASE_URL + "/preference?userId=" + userId;
                HttpGet httppost = new HttpGet(url);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);


                if (response != null) {


                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsonObject = new JSONObject(data);
                    Log.d("responseAna", jsonObject + "");

                    sessionTime = jsonObject.getJSONObject("Result").getString("session_time");
                    whitenDays = jsonObject.getJSONObject("Result").getString("whiten_days");
                    notificationTime = jsonObject.getJSONObject("Result").getString("notification");

                    scheduledValue = jsonObject.getJSONObject("Result").getString("scheduled");
                    createdValue = jsonObject.getJSONObject("Result").getString("created_at");
                    UpdatedValue = jsonObject.getJSONObject("Result").getString("updated_at");

                    cleaningTime_tv.setText("Next session time: " + sessionTime);
//                    String jsonInner= jsonObject.getJSONObject("Result").getString("whiten_days_other");


                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {

                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {

//            Once a day
//            Twice a day
//            Once every two days

            SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = sharedpreferences.edit();
            editor.putString("noti_type", notificationTime);
            editor.commit();
//            Image alert on screen
//            Sound alert
//            Sound and Image alert


            String notitime = sharedpreferences.getString("session_time", "");

            if (!notitime.equalsIgnoreCase("")) {


                if (notitime.equalsIgnoreCase(sessionTime)) {

                } else {
                    TriggerAlram();
                }


            } else {
                TriggerAlram();
            }


            editor.putString("session_time", sessionTime);
            editor.commit();


        }
    }

    public void cancelAlarm(Context context, int notificationId) {
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        PendingIntent displayIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmManager, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        alarmManager.cancel(displayIntent);


        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

    }

    private void TriggerAlram() {

        cancelAlarm(getApplicationContext(), 0);
        cancelAlarm(getApplicationContext(), 1);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if (!prefs.getBoolean("firstTime", false)) {

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);


        String time = sessionTime;

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            final Date dateObj = sdf.parse(time);


            String date24 = new SimpleDateFormat("HH:mm").format(dateObj);
            Log.d("date24", date24);


            String[] separated = date24.split(":");
            HoursNoti = separated[0];
            MinsNoti = separated[1];


        } catch (final ParseException e) {
            e.printStackTrace();
        }


        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();


        if (whitenDays.equalsIgnoreCase("Once a day")) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HoursNoti));
            calendar.set(Calendar.MINUTE, Integer.valueOf(MinsNoti));
            calendar.set(Calendar.SECOND, 0);


            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);


        } else if (whitenDays.equalsIgnoreCase("Twice a day")) {


            //Alaram 1
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HoursNoti));
            calendar.set(Calendar.MINUTE, Integer.valueOf(MinsNoti));
            calendar.set(Calendar.SECOND, 0);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            Intent alarm2Intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pending2Intent = PendingIntent.getBroadcast(this, 1, alarm2Intent, PendingIntent.FLAG_UPDATE_CURRENT);

            try {
                manager.cancel(pending2Intent);
            } catch (Exception e) {
                e.printStackTrace();
            }


            //Alaram 2
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HoursNoti + 12));
            calendar.set(Calendar.MINUTE, Integer.valueOf(MinsNoti));
            calendar.set(Calendar.SECOND, 0);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pending2Intent);

        } else if (whitenDays.equalsIgnoreCase("Once every two days")) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HoursNoti));
            calendar.set(Calendar.MINUTE, Integer.valueOf(MinsNoti));
            calendar.set(Calendar.SECOND, 0);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        }

    }
}
