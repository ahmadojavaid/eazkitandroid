package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ThankYou extends AppCompatActivity {

    private TextView thankYou_SettingsLabel, thankYou_ThankYouLabel, thankYou_thankYouBody, thankYou_EAZKIT;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private ImageView backImg;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }


        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        thankYou_SettingsLabel = (TextView) findViewById(R.id.thankYou_SettingsLabel);
        thankYou_SettingsLabel.setTypeface(firaSansMedium);

        thankYou_ThankYouLabel = (TextView) findViewById(R.id.thankYou_ThankYouLabel);
        thankYou_ThankYouLabel.setTypeface(firaSansBold);

        thankYou_thankYouBody = (TextView) findViewById(R.id.thankYou_thankYouBody);
        thankYou_thankYouBody.setTypeface(firaSansRegular);

        thankYou_EAZKIT = (TextView) findViewById(R.id.thankYou_EAZKIT);
        thankYou_EAZKIT.setTypeface(absoluteProReducedBlack);
    }
}
