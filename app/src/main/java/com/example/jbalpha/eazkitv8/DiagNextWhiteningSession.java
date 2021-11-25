package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class DiagNextWhiteningSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_next_whitening_session);

        RelativeLayout scheduleButton=(RelativeLayout)findViewById(R.id.scheduleButton);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiagNextWhiteningSession.this,DiagToothfieSession.class);
                startActivity(intent);
            }
        });
    }
}
