package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbalpha.eazkitv8.BroadCastReceiverMine.AlarmReceiver;
import com.example.jbalpha.eazkitv8.EventModels.MenuModel;
import com.example.jbalpha.eazkitv8.Models.MenuFragment;
import com.example.jbalpha.eazkitv8.Models.StartWhiteningPreferences;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Service.ServiceMine;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.squareup.picasso.Picasso;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class LetsStartWhitening extends AppCompatActivity {

    private ImageView menu;
    private boolean isFragmentLoaded = false;
    private Fragment menuFragment;
    private ImageView bateesiContainer;
    private Button imageButton;
    private RelativeLayout startWhitening_StartWhiteningBtn;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private TextView startWhitening_Step1, startWhitening_instructionsLabe2, startWhitening_instructionsLabe3, startWhitening_touchHereLabel;
    private TextView startWhitening_startWhiteningLabel, startWhitening_instructionsLabel;
    private CircleImageView startWhitening_Step1Image, startWhitening_Step2Image, startWhitening_Step3Image;
    private AnimationDrawable mouthTrayAnimation, insertCableAnimation, placeTrayAnimation, bateesiAnimation;
    private CheckBox putGelCheckBox, mouthLightInsertionBox, placeTrayCheckBox;
    boolean isChecked1, isChecked2, isChecked3;
    private RelativeLayout mainActivityLayout;
    private TextView eazkit_txt;
    private String userId;
    private SharedPreferences myPrefs;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initAddLayout(R.layout.activity_lets_start_whitening);
        setContentView(R.layout.activity_lets_start_whitening);
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }


//        Intent serviceInent = new Intent(LetsStartWhitening.this, ServiceMine.class);
//        startService(serviceInent);

        myPrefs = getSharedPreferences("mysharedpref12", MODE_PRIVATE);
        userId = myPrefs.getString("user_id", "");
        Log.d("userID", userId + "");


        mainActivityLayout = (RelativeLayout) findViewById(R.id.mainActivityLayout);

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        startWhitening_Step1 = (TextView) findViewById(R.id.startWhitening_Step1);
        startWhitening_instructionsLabe2 = (TextView) findViewById(R.id.startWhitening_instructionsLabe2);
        startWhitening_instructionsLabe3 = (TextView) findViewById(R.id.startWhitening_instructionsLabe3);
        startWhitening_touchHereLabel = (TextView) findViewById(R.id.startWhitening_touchHereLabel);

        startWhitening_Step1.setTypeface(firaSansMedium);
        startWhitening_instructionsLabe2.setTypeface(firaSansMedium);
        startWhitening_instructionsLabe3.setTypeface(firaSansMedium);
        startWhitening_touchHereLabel.setTypeface(firaSansMedium);

        startWhitening_startWhiteningLabel = (TextView) findViewById(R.id.startWhitening_startWhiteningLabel);
        startWhitening_startWhiteningLabel.setTypeface(firaSansMedium);
        startWhitening_instructionsLabel = (TextView) findViewById(R.id.startWhitening_instructionsLabel);
        startWhitening_instructionsLabel.setTypeface(firaSansBold);

        bateesiContainer = (ImageView) findViewById(R.id.bateesiContainer);
        startWhitening_Step1Image = (CircleImageView) findViewById(R.id.startWhitening_Step1Image);
        startWhitening_Step2Image = (CircleImageView) findViewById(R.id.startWhitening_Step2Image);
        startWhitening_Step3Image = (CircleImageView) findViewById(R.id.startWhitening_Step3Image);

        startWhitening_Step1Image.setBackgroundResource(R.drawable.mouth_tray_animation);
        startWhitening_Step2Image.setBackgroundResource(R.drawable.insert_cable_animation);
        startWhitening_Step3Image.setBackgroundResource(R.drawable.place_tray_animation);

        bateesiContainer.setBackgroundResource(R.drawable.animation_bateesi);

        mouthTrayAnimation = (AnimationDrawable) startWhitening_Step1Image.getBackground();
        insertCableAnimation = (AnimationDrawable) startWhitening_Step2Image.getBackground();
        placeTrayAnimation = (AnimationDrawable) startWhitening_Step3Image.getBackground();
        bateesiAnimation = (AnimationDrawable) bateesiContainer.getBackground();
        bateesiAnimation.start();

        putGelCheckBox = (CheckBox) findViewById(R.id.putGelCheckBox);
        mouthLightInsertionBox = (CheckBox) findViewById(R.id.mouthLightInsertionBox);
        placeTrayCheckBox = (CheckBox) findViewById(R.id.placeTrayCheckBox);
        startWhitening_StartWhiteningBtn = (RelativeLayout) findViewById(R.id.startWhitening_StartWhiteningBtn);

        putGelCheckBox.setChecked(StartWhiteningPreferences.getInstance(LetsStartWhitening.this).getPutGel());
        mouthLightInsertionBox.setChecked(StartWhiteningPreferences.getInstance(LetsStartWhitening.this).getmouthLight());
        placeTrayCheckBox.setChecked(StartWhiteningPreferences.getInstance(LetsStartWhitening.this).getPlaceTray());


        eazkit_txt = findViewById(R.id.eazkit_txt);

        eazkit_txt.setTypeface(absoluteProReducedBlack);

        putGelCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (putGelCheckBox.isChecked()) {
                    //Toast.makeText(LetsStartWhitening.this,"Checked",Toast.LENGTH_LONG).show();
                    isChecked1 = true;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).savePrefs(isChecked1);
                } else {
                    isChecked1 = false;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).savePrefs(isChecked1);
                    //  Toast.makeText(LetsStartWhitening.this,"UnChecked",Toast.LENGTH_LONG).show();
                }
            }
        });

        mouthLightInsertionBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (putGelCheckBox.isChecked() && mouthLightInsertionBox.isChecked()) {
                    isChecked1 = true;
                    isChecked2 = true;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).savePrefs(isChecked1, isChecked2);
                } else if (!putGelCheckBox.isChecked() && mouthLightInsertionBox.isChecked()) {
                    isChecked1 = false;
                    isChecked2 = true;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setPutGel(isChecked1);
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setMouthLight(isChecked2);
                } else if (!putGelCheckBox.isChecked() && !mouthLightInsertionBox.isChecked()) {
                    isChecked1 = false;
                    isChecked2 = false;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setPutGel(isChecked1);
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setMouthLight(isChecked2);
                }
            }
        });
        placeTrayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (putGelCheckBox.isChecked() && mouthLightInsertionBox.isChecked() && placeTrayCheckBox.isChecked()) {
                    isChecked1 = true;
                    isChecked2 = true;
                    isChecked3 = true;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).savePrefs(isChecked1, isChecked2, isChecked3);
                } else if (!putGelCheckBox.isChecked() && !mouthLightInsertionBox.isChecked() && !placeTrayCheckBox.isChecked()) {
                    isChecked1 = false;
                    isChecked2 = false;
                    isChecked3 = false;
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setPutGel(isChecked1);
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setMouthLight(isChecked2);
                    StartWhiteningPreferences.getInstance(LetsStartWhitening.this).setPlaceTray(isChecked3);
                }
            }
        });

        startWhitening_StartWhiteningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (putGelCheckBox.isChecked() && mouthLightInsertionBox.isChecked() && placeTrayCheckBox.isChecked()) {

                    if (GlobalClass.isOnline(getApplicationContext())) {
                        Intent intent = new Intent(LetsStartWhitening.this, WhiteningSession.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(LetsStartWhitening.this, "Please Connect to Internet!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(LetsStartWhitening.this, "You are not ready yet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        menu = (ImageView) findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mouthTrayAnimation.start();
        insertCableAnimation.start();
        placeTrayAnimation.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStart() {
        super.onStart();

    }


}

