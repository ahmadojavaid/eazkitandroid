package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutEazkit extends AppCompatActivity {


    private TextView aboutEazkitSettingsLabel, about_usEAZKIT;
    private String color = "#ffffff";
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private ImageView backImg;
    private TextView textToload, abouttxt;
    private ImageView shareButtons_Twitter, shareButtons_Facebook, shareButtons_insta;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_eazkit);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        aboutEazkitSettingsLabel = (TextView) findViewById(R.id.aboutEazkitSettingsLabel);
        aboutEazkitSettingsLabel.setTypeface(firaSansMedium);
        about_usEAZKIT = (TextView) findViewById(R.id.about_usEAZKIT);
        about_usEAZKIT.setTypeface(absoluteProReducedBlack);
        abouttxt = findViewById(R.id.abouttxt);
        textToload = findViewById(R.id.textToload);
        abouttxt.setTypeface(firaSansMedium);
        textToload.setTypeface(firaSansLight);
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (GlobalClass.isOnline(getApplicationContext()) == true) {
            GetAboutUs();

        } else {
            Toast.makeText(this, "Please Connect to Internet!", Toast.LENGTH_SHORT).show();
        }


        shareButtons_Twitter = findViewById(R.id.shareButtons_Twitter);
        shareButtons_Facebook = findViewById(R.id.shareButtons_Facebook);
        shareButtons_insta = findViewById(R.id.shareButtons_insta);

        shareButtons_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/";
                OPenExternalWebView(url);
            }
        });
        shareButtons_Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/";
                OPenExternalWebView(url);
            }
        });
        shareButtons_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.instagram.com/";
                OPenExternalWebView(url);

            }
        });


    }

    private void OPenExternalWebView(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));

// Always use string resources for UI text. This says something like "Share this photo with"
        String title = getApplicationContext().getResources().getString(R.string.open_link_using);
// Create and start the chooser
        Intent chooser = Intent.createChooser(intent, title);
        startActivity(chooser);
    }

    private void GetAboutUs() {
        final ProgressDialog progressDialog = new ProgressDialog(AboutEazkit.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.BASE_URL + Utils.ABOUT_US_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            Log.d("aboutREs", response + "");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject obj = jsonObject.getJSONObject("Result");
                            //Toast.makeText(AboutUs.this,obj.toString(),Toast.LENGTH_LONG).show();
                            String privacy = obj.getString("aboutUs");
                            String privacy2 = "<font color=\"" + color + "\">" + privacy + "</font>";
                            textToload.setText(privacy);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
