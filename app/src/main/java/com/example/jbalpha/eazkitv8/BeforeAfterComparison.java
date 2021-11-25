package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class BeforeAfterComparison extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_after_comparison);

        RelativeLayout beforeAfter_dismissButton=(RelativeLayout)findViewById(R.id.beforeAfter_dismissButton);
        beforeAfter_dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeforeAfterComparison.this,AllSessions.class);
                startActivity(intent);
            }
        });
    }
}
