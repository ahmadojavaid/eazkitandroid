package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class DiagSetNewPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_new_pass);

        RelativeLayout savePASSBTN=(RelativeLayout)findViewById(R.id.savePASSBTN);
        savePASSBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiagSetNewPass.this,DiagCheck4Updates.class);
                startActivity(intent);
            }
        });
    }
}
