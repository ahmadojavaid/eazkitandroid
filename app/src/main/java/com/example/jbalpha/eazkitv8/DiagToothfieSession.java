package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class DiagToothfieSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_toothfie_session);

        RelativeLayout toothfieSession_captureButton=(RelativeLayout)findViewById(R.id.toothfieSession_captureButton);
        toothfieSession_captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiagToothfieSession.this,BeforeAfterComparison.class);
                startActivity(intent);
            }
        });
    }
}
