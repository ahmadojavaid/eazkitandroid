package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        RelativeLayout changePass_resetBtn=(RelativeLayout)findViewById(R.id.changePass_resetBtn);
        changePass_resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangePassword.this,InputPin.class);
                startActivity(intent);
            }
        });
    }
}
