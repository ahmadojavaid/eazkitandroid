package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class DiagRateYourSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rate_your_session);

        RelativeLayout SubmitRating=(RelativeLayout)findViewById(R.id.SubmitRating);
        SubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiagRateYourSession.this,DiagNextWhiteningSession.class);
                startActivity(intent);
            }
        });
    }
}
