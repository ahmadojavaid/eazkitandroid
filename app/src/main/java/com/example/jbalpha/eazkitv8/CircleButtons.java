package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import at.markushi.ui.CircleButton;

public class CircleButtons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_buttons);

        ImageView shareButtons_Twitter=(ImageView)findViewById(R.id.shareButtons_Twitter);
        shareButtons_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CircleButtons.this,ThankYou.class);
                startActivity(intent);
            }
        });
    }
}
