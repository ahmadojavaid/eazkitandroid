package com.example.jbalpha.eazkitv8;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeScreen4Aggrement extends AppCompatActivity {

    TextView agreeButton, homeScreen4_Number, homeScreen4_Subtitle, homeScreen4_Label, homeScreen4_Eazkit;
    ImageView winkingTeethImage;
    Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack;
    private final int REQUEST_WRITE_PERMISSION = 92;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen4);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.red));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }
        //initializing typeface
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");

        //applying typeFace
        homeScreen4_Number = (TextView) findViewById(R.id.homeScreen4_Number);
        homeScreen4_Subtitle = (TextView) findViewById(R.id.homeScreen4_Subtitle);
        homeScreen4_Label = (TextView) findViewById(R.id.homeScreen4_Label);
        homeScreen4_Eazkit = (TextView) findViewById(R.id.homeScreen4_Eazkit);

        homeScreen4_Number.setTypeface(firaSansMedium);
        homeScreen4_Subtitle.setTypeface(firaSansBold);
        homeScreen4_Label.setTypeface(firaSansRegular);
        homeScreen4_Eazkit.setTypeface(absoluteProReducedBlack);

        //adding animation to tooth Image
        winkingTeethImage = (ImageView) findViewById(R.id.winkingTeethImage);
        winkingTeethImage.setBackgroundResource(R.drawable.winking_teeth_animation);
        AnimationDrawable drawable = (AnimationDrawable) winkingTeethImage.getBackground();
        drawable.start();
        agreeButton = (TextView) findViewById(R.id.agreeButton);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();


            }
        });
        agreeButton.setTypeface(firaSansMedium);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            GoToNext();
        }
    }

    private void GoToNext(){
        Intent intent = new Intent(HomeScreen4Aggrement.this, SignUp.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeScreen4Aggrement.this, R.anim.intro_slide_up, R.anim.no_animation);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent, options.toBundle());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Do the stuff that requires permission...
                GoToNext();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeScreen4Aggrement.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show permission explanation dialog...
                } else {
                    //Never ask again selected, or device policy prohibits the app from having that permission.
                    //So, disable that feature, or fall back to another situation...
                }
            }
        }


    }


}
