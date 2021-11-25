package com.example.jbalpha.eazkitv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chaos.view.PinView;

public class InputPin extends AppCompatActivity {

    PinView pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pin);

        pin=(PinView)findViewById(R.id.pinView);
        pin.setItemCount(4);
        pin.setAnimationEnable(true);
        pin.setHideLineWhenFilled(false);
        pin.setCursorVisible(true);
        //pinView.addTextChangedListener(new TextWatcher() {...});

        RelativeLayout resetButton=(RelativeLayout)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InputPin.this,DiagSetNewPass.class);
                startActivity(intent);
            }
        });
    }
}
