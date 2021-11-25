package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaveAmessage extends AppCompatActivity {

    private EditText message_messageHeading, message_messageBody;
    private ImageView leaveMessage_ToothImage;
    private TextView message_SettingsLabel, sendLabel, message_EAZKIT, message_LeaveMessage;

    private JSONParser jsonParser = new JSONParser();
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;

    private ImageView backImg;
    private ImageView bg_imag;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_amessage);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }

//        getWindow().setBackgroundDrawableResource(R.drawable.bg_test);

        bg_imag = findViewById(R.id.bg_imag);
        bg_imag.setBackgroundResource(R.drawable.bg_dark);

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        message_SettingsLabel = (TextView) findViewById(R.id.message_SettingsLabel);
        message_SettingsLabel.setTypeface(firaSansMedium);

        sendLabel = (TextView) findViewById(R.id.sendLabel);
        sendLabel.setTypeface(firaSansMedium);

        message_LeaveMessage = (TextView) findViewById(R.id.message_LeaveMessage);
        message_LeaveMessage.setTypeface(firaSansBold);

        message_EAZKIT = (TextView) findViewById(R.id.message_EAZKIT);
        message_EAZKIT.setTypeface(absoluteProReducedBlack);

        message_messageHeading = (EditText) findViewById(R.id.message_messageHeading);
        message_messageHeading.setTypeface(firaSansLight);
        message_messageBody = (EditText) findViewById(R.id.message_messageBody);
        message_messageBody.setTypeface(firaSansLight);
        leaveMessage_ToothImage = (ImageView) findViewById(R.id.leaveMessage_ToothImage);
        leaveMessage_ToothImage.setBackgroundResource(R.drawable.teeth_blink_animation);
        AnimationDrawable animation = (AnimationDrawable) leaveMessage_ToothImage.getBackground();
        animation.start();
        RelativeLayout sendMessageBtn = (RelativeLayout) findViewById(R.id.sendMessageBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String heading, body;


                heading = message_messageHeading.getText().toString();
                if (heading.equalsIgnoreCase("")) {

                    Toast.makeText(LeaveAmessage.this, "Please Enter Title.", Toast.LENGTH_SHORT).show();
                    return;
                }

                body = message_messageBody.getText().toString();

                if (body.equalsIgnoreCase("")) {


                    Toast.makeText(LeaveAmessage.this, "Please Enter Your Message", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (GlobalClass.isOnline(getApplicationContext())==true){
                    AttemptSendMessgae attemptSendMessgae = new AttemptSendMessgae();
                    attemptSendMessgae.execute(heading, body);
                }else {
                    Toast.makeText(LeaveAmessage.this, "Please Connect to Internet!", Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });


        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });
    }

    private class AttemptSendMessgae extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            String heading, message;
            heading = strings[0];
            message = strings[1];
            String uid = SharedPreManager.getInstance(LeaveAmessage.this).getCurrentUserId();
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("heading", heading));
            params.add(new BasicNameValuePair("suggestion", message));
            params.add(new BasicNameValuePair("userId", uid));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Utils.BASE_URL + Utils.POST_CUSTOMER_SUPPORT, "POST", params);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null) {
                try {
                    String check = jsonObject.getString("statusCode");
                    Toast.makeText(LeaveAmessage.this, jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                    if (check.equals("1")) {

                        Intent intent = new Intent(LeaveAmessage.this, ThankYou.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(LeaveAmessage.this, jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                Toast.makeText(LeaveAmessage.this, "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }
}
