package com.example.jbalpha.eazkitv8;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.andreilisun.swipedismissdialog.OnCancelListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;

public class PopUpBackgroundActivity extends Activity {
    private ImageView close_btn;
    View dialog;
    private String soundcheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_background);


        Bundle bundle = getIntent().getExtras();

        soundcheck = bundle.getString("soundcheck");

        if (soundcheck.equalsIgnoreCase("1")) {
//play sound
            Playsound();
        }


        dialog = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);

        new SwipeDismissDialog.Builder(this)
                .setView(dialog)
                .build()
                .show();

        close_btn = dialog.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }


    @Override
    protected void onResume() {
//        if (checkActivity == false) {
//
//        } else {
//
//            finish();
//            Intent i = new Intent(PopUpBackgroundActivity.this, LetsStartWhitening.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//
//
//        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent i = new Intent(PopUpBackgroundActivity.this, AllSessions.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

    }

    private void Playsound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
