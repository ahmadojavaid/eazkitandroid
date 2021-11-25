package com.example.jbalpha.eazkitv8;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.FragmentServices.DateDialog;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WhiteningSession extends BaseActivity {

    private ImageView whitening_smilingTooth;
    private String firstTime, secondTime, thirdTime, countdownInterval;
    private TextView whitening_tv_Session;
    private TextView sessionTotalTime, sessionTimeElapsed;
    //    ImageView whiteningSessionMenuB;
//    private CircularProgressBar whitening_Progress;
    private int sessionSize;
    private int counter, counter3;
    private int counter2 = 1;
    private String session_rating = "null";
    private String session_time;
    private RelativeLayout scheduleButton;
    private String preference_Id, scheduled;

    private TextView whiteningSessionCountdownLabel, whiteningSession_EAZKIT;

    //views of new session dialog
    private Fragment menuFragment;
    private boolean isFragmentLoaded;
    private AlertDialog showSessionDialog, toothfieDialog;
    private TextView newScheduleDate;
    private TextView nextSessionTime, nextSessionAMPM, previousSessionTime, previousSessionAmPm;
    private RelativeLayout toothfieSession_captureButton, toothfieSession_DismissDiag;
    private String preference_toothfieImage;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private String encoded_Img = "null";//encoded toothfie image of current session
    //date dialog test
    private TextView resultText;
    private ImageView backImg;
    private int progressbarValue = 0;
    private int timeCountInMilliSeconds;
    private ProgressBar progressBarCircle;
    private int timerValue;
    private CountDownTimer countDownTimer;
    private boolean timerTicking = false;
    private TextView stopTimerTv;
    private long milisecondsDone;
    private long minutes;
    private RelativeLayout whitening_ProgressContainer;

    ArrayList<String> titlesList = new ArrayList<>();


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitening_session);


        titlesList.add("You have completed your session");
        titlesList.add("This session has been completed.");
        titlesList.add("Current session completed.");


        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }
        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

//        whiteningSessionMenuB = (ImageView) findViewById(R.id.whiteningSessionMenuB);
//        whiteningSessionMenuB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isFragmentLoaded) {
//                    loadFragment();
//                } else {
//                    if (menuFragment != null) {
//                        if (menuFragment.isAdded()) {
//                            hideFragment();
//                        }
//                    }
//                }
//            }
//        });


//        whitening_Progress = (CircularProgressBar) findViewById(R.id.whitening_Progress);

        whitening_ProgressContainer = findViewById(R.id.whitening_ProgressContainer);
        whitening_ProgressContainer.setVisibility(View.INVISIBLE);

        whitening_smilingTooth = (ImageView) findViewById(R.id.whitening_smilingTooth);
        whitening_tv_Session = (TextView) findViewById(R.id.whitening_tv_Session);
        whitening_tv_Session.setTypeface(firaSansMedium);
        sessionTotalTime = (TextView) findViewById(R.id.sessionTotalTime);
        sessionTotalTime.setTypeface(firaSansBold);
        sessionTimeElapsed = (TextView) findViewById(R.id.sessionTimeElapsed);
        sessionTimeElapsed.setTypeface(firaSansRegular);
        whiteningSession_EAZKIT = (TextView) findViewById(R.id.whiteningSession_EAZKIT);
        whiteningSession_EAZKIT.setTypeface(absoluteProReducedBlack);

        whiteningSessionCountdownLabel = (TextView) findViewById(R.id.whiteningSessionCountdownLabel);
        whiteningSessionCountdownLabel.setTypeface(firaSansBold);

        whitening_smilingTooth.setBackgroundResource(R.drawable.animation_bigteeth);
        AnimationDrawable animation = (AnimationDrawable) whitening_smilingTooth.getBackground();
        animation.start();

//        Toast.makeText(WhiteningSession.this, "Tap in the middle of RING to start TIMER!", Toast.LENGTH_LONG).show();
        if (GlobalClass.isOnline(getApplicationContext()) == true) {
            getPreferences();
        } else {
            Toast.makeText(this, "Connect to Internet!", Toast.LENGTH_SHORT).show();
        }


        backImg = (ImageView) findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);


        AlertDialog.Builder toothfieDiagBuilder = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
        View toothfieLayout = getLayoutInflater().inflate(R.layout.dialog_toothfie_session, null);
        toothfieDiagBuilder.setView(toothfieLayout);
        toothfieDiagBuilder.setCancelable(false);
        toothfieDialog = toothfieDiagBuilder.create();

        //initializing views
        ImageView toothfieDiagAnim = (ImageView) toothfieLayout.findViewById(R.id.toothfieDiagAnim);
        toothfieDiagAnim.setBackgroundResource(R.drawable.animation_closeeyes_openmouth);
        AnimationDrawable anim = (AnimationDrawable) toothfieDiagAnim.getBackground();
        anim.start();
        toothfieSession_captureButton = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_captureButton);
        toothfieSession_DismissDiag = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_DismissDiag);
        //take toothfie dialog code ends here

      /*  LinearLayout totalTimeContainer=(LinearLayout)findViewById(R.id.totalTimeContainer);
        totalTimeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhiteningSession.this,DiagRateYourSession.class);
                startActivity(intent);
            }
        });*/


        stopTimerTv = findViewById(R.id.stopTimerTv);
        stopTimerTv.setVisibility(View.INVISIBLE);

        stopTimerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.onFinish();
            }
        });
    }


    private void setTimerValues() {
        int time = timerValue;
//        int time = 1;
        timeCountInMilliSeconds = time * 60 * 1000;
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private void startCountDownTimer() {

        stopTimerTv.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTicking = true;
                sessionTimeElapsed.setText(hmsTimeFormatter(millisUntilFinished));

                milisecondsDone = millisUntilFinished;

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));


            }

            @Override
            public void onFinish() {

                timerTicking = false;
                sessionTimeElapsed.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
//                setProgressBarValues();
                progressBarCircle.setProgress(100);
                countDownTimer.cancel();
                afterCountDownTimerFinish();
                stopTimerTv.setVisibility(View.GONE);

            }

        }.start();
//        countDownTimer.start();
    }


    @Override
    protected void onDestroy() {


        if (timerTicking == true) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private void afterCountDownTimerFinish() {
        AlertDialog.Builder ratingDialog = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
        View dialogLayout = getLayoutInflater().inflate(R.layout.dialog_rate_your_session, null);
        ratingDialog.setView(dialogLayout);
        final AlertDialog dialog = ratingDialog.create();
        dialog.show();
        dialog.setCancelable(false);

        //initializing views of dialogLayout
        RatingBar rateSession_sessionRating = (RatingBar) dialogLayout.findViewById(R.id.rateSession_sessionRating);
        final TextView rateSession_ratingSubtitle = (TextView) dialogLayout.findViewById(R.id.rateSession_ratingSubtitle);
        rateSession_ratingSubtitle.setTypeface(firaSansMedium);
        TextView rateSession_Heading = (TextView) dialogLayout.findViewById(R.id.rateSession_Heading);
        rateSession_Heading.setTypeface(firaSansMedium);
        TextView rateSession_ratNowLabel = (TextView) dialogLayout.findViewById(R.id.rateSession_ratNowLabel);
        rateSession_ratNowLabel.setTypeface(firaSansMedium);
        final RelativeLayout SubmitRating = (RelativeLayout) dialogLayout.findViewById(R.id.SubmitRating);
        ImageView rateSessionAnimatingTooth = (ImageView) dialogLayout.findViewById(R.id.rateSessionAnimatingTooth);

        TextView not_now_tv = (TextView) dialogLayout.findViewById(R.id.not_now_tv);

        not_now_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session_rating = "null";
                SubmitRating.callOnClick();
            }
        });

        rateSessionAnimatingTooth.setBackgroundResource(R.drawable.teeth_blink_animation);
        AnimationDrawable animation = (AnimationDrawable) rateSessionAnimatingTooth.getBackground();
        animation.start();

        //displaying next dialog box
        SubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nextSessionDiag = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
                View nextSessionLayout = getLayoutInflater().inflate(R.layout.dialog_next_whitening_session, null);
                nextSessionDiag.setView(nextSessionLayout);
                dialog.dismiss();
                showSessionDialog = nextSessionDiag.create();
                showSessionDialog.show();
                showSessionDialog.setCancelable(false);

                //declaring views of this layout
                RadioGroup nextSession_radioBtn = (RadioGroup) nextSessionLayout.findViewById(R.id.nextSession_radioBtn);
                RadioButton previousSchedule, newSchedule;
                ImageView whiteningDiagAnim;
                newSchedule = (RadioButton) nextSessionLayout.findViewById(R.id.newSchedule);
                newSchedule.setTypeface(firaSansLight);
                previousSchedule = (RadioButton) nextSessionLayout.findViewById(R.id.previousSchedule);
                previousSchedule.setTypeface(firaSansLight);
                TextView nextSession_Heading = (TextView) nextSessionLayout.findViewById(R.id.nextSession_Heading);
                nextSession_Heading.setTypeface(firaSansBold);
                TextView nextSession_whenLabel = (TextView) nextSessionLayout.findViewById(R.id.nextSession_whenLabel);
                nextSession_whenLabel.setTypeface(firaSansMedium);
                TextView nextSession_scheduleLabel = (TextView) nextSessionLayout.findViewById(R.id.nextSession_scheduleLabel);
                nextSession_scheduleLabel.setTypeface(firaSansMedium);

                //adding animation to tooth image for this dialog
                whiteningDiagAnim = (ImageView) nextSessionLayout.findViewById(R.id.whiteningDiagAnim);
                whiteningDiagAnim.setBackgroundResource(R.drawable.animation_tongueout);
                AnimationDrawable anim = (AnimationDrawable) whiteningDiagAnim.getBackground();
                anim.start();

                newScheduleDate = (TextView) nextSessionLayout.findViewById(R.id.newScheduleDate);
                newScheduleDate.setTypeface(firaSansMedium);


                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);
                newScheduleDate.setText(formattedDate);


                scheduleButton = (RelativeLayout) nextSessionLayout.findViewById(R.id.scheduleButton);
                nextSessionTime = (TextView) nextSessionLayout.findViewById(R.id.nextSessionTime);
                nextSessionTime.setTypeface(firaSansMedium);
                nextSessionAMPM = (TextView) nextSessionLayout.findViewById(R.id.nextSessionAMPM2);
                nextSessionAMPM.setTypeface(firaSansMedium);
                previousSessionAmPm = (TextView) nextSessionLayout.findViewById(R.id.previousSessionAmPm);
                previousSessionAmPm.setTypeface(firaSansMedium);
                previousSessionTime = (TextView) nextSessionLayout.findViewById(R.id.previousSessionTime);
                previousSessionTime.setTypeface(firaSansMedium);
                String[] split = session_time.split(" ");
                //  String[] split="06:00 AM".split(" ");
                String tim = split[0];
                String amPm = split[1];
                previousSessionTime.setText(tim);
                previousSessionAmPm.setText(amPm);


            }
        });

        //getRating from rating bar by adding a RatingChangeListener
        rateSession_sessionRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 1) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Normal");
                } else if (rating == 2) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Above Normal");
                } else if (rating == 3) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Good");
                } else if (rating == 4) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Satisfactory");
                } else {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Excellent");
                }
            }
        });
    }

    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d",
//                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

    private void trackAllSessions() {
        String userId = SharedPreManager.getInstance(WhiteningSession.this).getCurrentUserId();
        StringRequest request = new StringRequest(Request.Method.GET, Utils.BASE_URL + Utils.ALL_SESSIONS_URL + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("responseSeeesion", response + "");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("Result");
                            sessionSize = array.length();
                            if (sessionSize == 0) {
                                whitening_tv_Session.setText("Session " + String.valueOf(sessionSize + 1));
                                sessionTotalTime.setText(firstTime + " " + "minutes");
                                counter = Integer.parseInt(firstTime);
                                counter3 = counter;
                                timerValue = Integer.valueOf(firstTime);
                            } else if (sessionSize == 1) {
                                whitening_tv_Session.setText("Session " + String.valueOf(sessionSize + 1));
                                sessionTotalTime.setText(secondTime + " " + "minutes");
                                counter = Integer.parseInt(secondTime);
                                counter3 = counter;
                                timerValue = Integer.valueOf(secondTime);
                            } else {
                                whitening_tv_Session.setText("Session " + String.valueOf(sessionSize + 1));
                                sessionTotalTime.setText(thirdTime + " " + "minutes");
                                counter = Integer.parseInt(thirdTime);
                                counter3 = counter;
                                timerValue = Integer.valueOf(thirdTime);
                            }
                            // Toast.makeText(WhiteningSession.this,String.valueOf(sessionSize),Toast.LENGTH_LONG).show();

                            setTimerValues();
                            setProgressBarValues();
                            startCountDownTimer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(WhiteningSession.this, "Not connected to internet!", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getPreferences() {
        final ProgressDialog progressDialog = new ProgressDialog(WhiteningSession.this);
        progressDialog.setMessage("Getting things ready!");
        progressDialog.show();
        String userId = SharedPreManager.getInstance(WhiteningSession.this).getCurrentUserId();

        String url = Utils.BASE_URL + Utils.GET_USERPREFERANCES_URL + userId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("responseSession", jsonObject + "");
                            JSONObject result = jsonObject.getJSONObject("Result");
                            firstTime = result.getString("firstTime");

                            secondTime = result.getString("secondTime");
                            thirdTime = result.getString("thirdTime");
                            countdownInterval = result.getString("countdown");
                            session_time = result.getString("session_time");
                            preference_Id = result.getString("id");
                            scheduled = result.getString("scheduled");
                            preference_toothfieImage = result.getString("toothfieImage");


                            if (countdownInterval.equalsIgnoreCase("1")) {
                                whitening_ProgressContainer.setVisibility(View.VISIBLE);
                            } else {
                                whitening_ProgressContainer.setVisibility(View.INVISIBLE);
                            }


                            trackAllSessions();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Log.d("responseSession", error + "");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    //call to save the new session that just finished along with its rating and toothfie
    private void saveSession() {


        long totalMilis = Long.valueOf(timeCountInMilliSeconds) - Long.valueOf(milisecondsDone);

        minutes = TimeUnit.MILLISECONDS.toMinutes(totalMilis);
        if (minutes == 0) {
            minutes = 1;
        }


        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.SAVE_SESSION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("statusCode");

                            Log.d("saveSessions", "onResponse: ");
                            if (check.equals("1")) {
//                                Toast.makeText(WhiteningSession.this, jsonObject.getString("statusMessage")
//                                        , Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(WhiteningSession.this, jsonObject.getString("statusMessage")
                                        , Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(WhiteningSession.this, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uId = SharedPreManager.getInstance(WhiteningSession.this).getCurrentUserId();
                String enc_Image;

                Map<String, String> params = new HashMap<>();
                params.put("userId", uId);
                params.put("sessionImage", encoded_Img);
                params.put("sessionRating", session_rating);
                params.put("timeCarriedOut", minutes + "");
                params.put("actualTimeCarriedOut", String.valueOf(counter3) + " " + "minutes");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(WhiteningSession.this);
        queue.add(request);
    }

    public void onRadioBtnClicked(final View view) {

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.previousSchedule:
                        if (checked) {


                            // Toast.makeText(WhiteningSession.this,"Carry previous",Toast.LENGTH_LONG).show();
                            sendPreviousSchedule();

                            AlertDialog.Builder toothfieDiagBuilder = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
                            View toothfieLayout = getLayoutInflater().inflate(R.layout.dialog_toothfie_session, null);
                            toothfieDiagBuilder.setView(toothfieLayout);
                            toothfieDiagBuilder.setCancelable(false);
                            toothfieDialog = toothfieDiagBuilder.create();
                            toothfieDialog.show();

                            //initializing views
                            TextView toothfieSession_Heading = (TextView) toothfieLayout.findViewById(R.id.toothfieSession_Heading);
                            toothfieSession_Heading.setTypeface(firaSansBold);
                            TextView toothfieSession_selfieLabel = (TextView) toothfieLayout.findViewById(R.id.toothfieSession_selfieLabel);
                            toothfieSession_selfieLabel.setTypeface(firaSansMedium);
                            TextView toothfieSession_captureLabel = (TextView) toothfieLayout.findViewById(R.id.toothfieSession_captureLabel);
                            toothfieSession_captureLabel.setTypeface(firaSansMedium);
                            TextView toothfieSession_dismissLabel = (TextView) toothfieLayout.findViewById(R.id.toothfieSession_dismissLabel);
                            toothfieSession_dismissLabel.setTypeface(firaSansMedium);
                            RelativeLayout toothfieSession_DismissDiag = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_DismissDiag);
                            RelativeLayout toothfieSession_captureButton = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_captureButton);
                            toothfieSession_DismissDiag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toothfieDialog.dismiss();

                                    saveSession();
                                    Intent i = new Intent(WhiteningSession.this, AllSessions.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                            });
                            toothfieSession_captureButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, 20);
                                }
                            });
                        }
                        break;
                    case R.id.newSchedule:
                        if (checked) {


                            // Toast.makeText(WhiteningSession.this,"Carry new",Toast.LENGTH_LONG).show();
                            sendNewSchedule();
                            AlertDialog.Builder toothfieDiagBuilder = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
                            View toothfieLayout = getLayoutInflater().inflate(R.layout.dialog_toothfie_session, null);
                            toothfieDiagBuilder.setView(toothfieLayout);
                            toothfieDiagBuilder.setCancelable(false);
                            toothfieDialog = toothfieDiagBuilder.create();
                            toothfieDialog.show();

                            //initializing views
                            RelativeLayout toothfieSession_DismissDiag = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_DismissDiag);
                            RelativeLayout toothfieSession_captureButton = (RelativeLayout) toothfieLayout.findViewById(R.id.toothfieSession_captureButton);
                            toothfieSession_DismissDiag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toothfieDialog.dismiss();
                                    saveSession();

                                    Intent i = new Intent(WhiteningSession.this, AllSessions.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                            });
                            toothfieSession_captureButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, 20);
                                }
                            });

                        }
                        break;
                }
            }
        });

        //check if a radio button is checked and perform its corresponding functionality
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.previousSchedule:
                if (checked) {


                    previousSessionTime.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                    nextSessionTime.setTextColor(getApplicationContext().getResources().getColor(R.color.gray));
                    newScheduleDate.setTextColor(getApplicationContext().getResources().getColor(R.color.gray));
                }
                break;
            case R.id.newSchedule:
                if (checked) {

                    previousSessionTime.setTextColor(getApplicationContext().getResources().getColor(R.color.gray));
                    nextSessionTime.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));
                    newScheduleDate.setTextColor(getApplicationContext().getResources().getColor(R.color.blue));

                    newScheduleDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DateDialog dialog = new DateDialog(v);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            dialog.show(ft, "DatePicker");
                        }
                    });
                    nextSessionTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // DialogFragment dFragment = new SecondTimePickerFragment();
                            // Show the time picker dialog fragment
                            //dFragment.show(getFragmentManager(),"Time Picker");

                            //new test for dialog fragment
                            showTimePicker(nextSessionTime);

                            // ThreeButtonFragment dialog = new ThreeButtonFragment();
                            //dialog.show(getSupportFragmentManager(), DIALOG_THREE_BUTTON);
                        }
                    });

                }
                break;
        }
    }

    private void sendNewSchedule() {
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.UPDATE_PREFERENCE + preference_Id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("statusCode");
                            if (check.equals("1")) {
                                showSessionDialog.dismiss();
//                                Toast.makeText(WhiteningSession.this,
//                                        jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                                toothfieDialog.show();

                            } else {
                                Toast.makeText(WhiteningSession.this,
                                        jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WhiteningSession.this,
                        "No internet connection!!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nextSession = nextSessionTime.getText().toString() + " " + nextSessionAMPM.getText().toString().toUpperCase();
//                String nextSession = "06:00 AM";


                Map<String, String> params = new HashMap<>();
                params.put("session_time", nextSession);
                String newScheduleValue = newScheduleDate.getText().toString();

                params.put("scheduled", newScheduleValue);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(WhiteningSession.this);
        queue.add(request);
    }

    private void sendPreviousSchedule() {
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.UPDATE_PREFERENCE + preference_Id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("statusCode");
                            if (check.equals("1")) {
//                                Toast.makeText(WhiteningSession.this, jsonObject.getString("statusMessage"),
//                                        Toast.LENGTH_LONG).show();
                                showSessionDialog.dismiss();
                            } else {
                                Toast.makeText(WhiteningSession.this, jsonObject.getString("statusMessage"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(WhiteningSession.this, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String dt = scheduled;  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 1);  // number of days to add
                dt = sdf.format(c.getTime());

                Map<String, String> params = new HashMap<>();
                params.put("scheduled", dt);

                String value = previousSessionTime.getText().toString() + " " + previousSessionAmPm.getText().toString();

                params.put("session_time", value);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(WhiteningSession.this);
        queue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //display before/after dialog box here!!
        AlertDialog.Builder beforeAfterDiagBuilder = new AlertDialog.Builder(WhiteningSession.this, R.style.custom_alert_dialog);
        View beforeAfterLayout = getLayoutInflater().inflate(R.layout.activity_before_after_comparison, null);
        beforeAfterDiagBuilder.setView(beforeAfterLayout);
        beforeAfterDiagBuilder.setCancelable(false);

        int min = 0;
        int max = 2;

        Random r = new Random();
        int number = r.nextInt(max - min + 1) + min;
        Log.d("numberRnadom", number + "");

        TextView comparision_title_tv = (TextView) beforeAfterLayout.findViewById(R.id.comparision_title_tv);
        comparision_title_tv.setTypeface(firaSansBold);

        comparision_title_tv.setText(titlesList.get(number));

        TextView beforeAfter_beforeLabel = (TextView) beforeAfterLayout.findViewById(R.id.beforeAfter_beforeLabel);
        beforeAfter_beforeLabel.setTypeface(firaSansBold);
        TextView beforeAfter_afterLabel = (TextView) beforeAfterLayout.findViewById(R.id.beforeAfter_afterLabel);
        beforeAfter_afterLabel.setTypeface(firaSansBold);
        TextView beforeAfter_dismissLabel = (TextView) beforeAfterLayout.findViewById(R.id.beforeAfter_dismissLabel);
        beforeAfter_dismissLabel.setTypeface(firaSansMedium);

        ImageView beforeAfter_afterImage = (ImageView) beforeAfterLayout.findViewById(R.id.beforeAfter_afterImage);
        ImageView comparisonToothAnim = (ImageView) beforeAfterLayout.findViewById(R.id.comparisonToothAnim);
        comparisonToothAnim.setBackgroundResource(R.drawable.animation_closeeyes_openmouth);
        AnimationDrawable anima = (AnimationDrawable) comparisonToothAnim.getBackground();
        anima.start();
        ImageView beforeAfter_beforeImage = (ImageView) beforeAfterLayout.findViewById(R.id.beforeAfter_beforeImage);
        RelativeLayout beforeAfter_dismissButton = (RelativeLayout) beforeAfterLayout.findViewById(R.id.beforeAfter_dismissButton);

        beforeAfter_afterImage.setImageBitmap(bitmap);
        Picasso.with(WhiteningSession.this).load(Utils.BASE_URL_IMAGE + preference_toothfieImage).into(beforeAfter_beforeImage);
        Bitmap image = ((BitmapDrawable) beforeAfter_afterImage.getDrawable()).getBitmap();
        encoded_Img = imageToString(image);

        final AlertDialog beforeAfterDialog = beforeAfterDiagBuilder.create();
        beforeAfterDialog.show();
        saveSession();
        beforeAfter_dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeAfterDialog.dismiss();
                toothfieDialog.dismiss();

                Intent intent = new Intent(WhiteningSession.this, AllSessions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


    //time picker test
    public void showTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment2(nextSessionTime, nextSessionAMPM);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment2 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TextView mResultText;
        TextView mResultText2;

        public TimePickerFragment2(TextView textView, TextView textView2) {
            mResultText = textView;
            mResultText2 = textView2;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(),
                    android.app.AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String status = "AM";

            if (hourOfDay > 11) {
                // If the hour is greater than or equal to 12
                // Then the current AM PM status is PM
                status = "PM";
            }

            // Initialize a new variable to hold 12 hour format hour value
            int hour_of_12_hour_format;

            if (hourOfDay > 11) {

                // If the hour is greater than or equal to 12
                // Then we subtract 12 from the hour to make it 12 hour format time

                if (hourOfDay - 12 == 0) {
                    hour_of_12_hour_format = 12;
                } else
                    hour_of_12_hour_format = hourOfDay - 12;
            } else {
                hour_of_12_hour_format = hourOfDay;
            }

            // Get the calling activity TextView reference
            String str = hour_of_12_hour_format + " : " + minute + " " + status;
            // TextView tv = (TextView) getActivity().findViewById(R.id.nextSessionTime);
            // TextView tv2 = (TextView) getActivity().findViewById(R.id.nextSessionAMPM2);
            // Display the 12 hour format time in app interface
            // nextSessionTime.setText(hour_of_12_hour_format + " : " + minute);
            //tv2.setText(status);


            mResultText.setText(hour_of_12_hour_format + ":" + minute);
            mResultText2.setText(status);
        }
    }

//    public void loadFragment() {
//        FragmentManager fm = getSupportFragmentManager();
//        menuFragment = fm.findFragmentById(R.id.whiteningContentContainer);
//        // menu.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
//        if (menuFragment == null) {
//            menuFragment = new WhiteningSessionSideMenu();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//            fragmentTransaction.add(R.id.whiteningContentContainer, menuFragment);
//            fragmentTransaction.commit();
//            //Toast.makeText(MainActivity.this,"Main Activity load frag",Toast.LENGTH_LONG).show();
//        }
//
//        isFragmentLoaded = true;
//    }
//
//    public void hideFragment() {
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
//        fragmentTransaction.remove(menuFragment);
//        fragmentTransaction.commit();
//        // menu.setImageResource(R.drawable.ic_menu_black_24dp);
//        // Toast.makeText(MainActivity.this,"Main Activity hide frag",Toast.LENGTH_LONG).show();
//        isFragmentLoaded = false;
//    }


}
