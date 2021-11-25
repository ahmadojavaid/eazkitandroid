package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.TimePickerFragment;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateProfile2 extends AppCompatActivity {

    private RadioGroup whiteningTimesRadio, NotificationRadio;
    private RadioButton whiten, notification;
    private String whiteningSelection = "", notificationSelection = "";
    private TextView cp2_sessionTime, cp2_Whitening4, cp2_AMPM;
    private Spinner cp2_Whitening1, cp2_Whitening2, cp2_Whitening3;
    private SpinnerAdapter adapter1, adapter2, adapter3, adapter4;
    private Button cp2_takeToothfieBtn;
    private ImageView cp2_toothfie, cp2_animatingTeeth;
    private String encoded_Img;
    private JSONParser jsonParser = new JSONParser();
    private TextView cp2_stepTwoLabel, cp2_whiteningPrefLabel, cp2_howTowhitenLabel, cp2_sessionLabel, cp2_notifiedLabel, cp2_whiteningTimesLabel,
            cp2_FirstTime_label, cp2_SecondTime_label, cp2_ThirdTimeLabel, cp2_FourthTimeLabel, cp2_countdownLabel,
            cp2_likeCountdownLabel, cp2_toothfieLabel, cp2_savFin, minutes1, minutes2, minutes3, minutes4;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private Switch swichCountDown;
    private String countDownSwitchValue = "0";
    boolean imageUploadedCheck = false;
    private final int CROP_PIC = 2;
    private Uri picUri;
//    public final static int Overlay_REQUEST_CODE = 251;





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile2);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.gray));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.gray));
        }

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");


        //initializing views
        cp2_stepTwoLabel = (TextView) findViewById(R.id.cp2_stepTwoLabel);
        cp2_stepTwoLabel.setTypeface(firaSansMedium);
        cp2_whiteningPrefLabel = (TextView) findViewById(R.id.cp2_whiteningPrefLabel);
        cp2_whiteningPrefLabel.setTypeface(firaSansBold);
        cp2_howTowhitenLabel = (TextView) findViewById(R.id.cp2_howTowhitenLabel);
        cp2_howTowhitenLabel.setTypeface(firaSansMedium);
        cp2_notifiedLabel = (TextView) findViewById(R.id.cp2_notifiedLabel);
        cp2_notifiedLabel.setTypeface(firaSansMedium);
        cp2_whiteningTimesLabel = (TextView) findViewById(R.id.cp2_whiteningTimesLabel);
        cp2_whiteningTimesLabel.setTypeface(firaSansBold);
        cp2_FirstTime_label = (TextView) findViewById(R.id.cp2_FirstTime_label);
        cp2_FirstTime_label.setTypeface(firaSansMedium);
        cp2_SecondTime_label = (TextView) findViewById(R.id.cp2_SecondTime_label);
        cp2_SecondTime_label.setTypeface(firaSansMedium);
        cp2_ThirdTimeLabel = (TextView) findViewById(R.id.cp2_ThirdTimeLabel);
        cp2_ThirdTimeLabel.setTypeface(firaSansMedium);
        cp2_FourthTimeLabel = (TextView) findViewById(R.id.cp2_FourthTimeLabel);
        cp2_FourthTimeLabel.setTypeface(firaSansMedium);
        cp2_countdownLabel = (TextView) findViewById(R.id.cp2_countdownLabel);
        cp2_countdownLabel.setTypeface(firaSansBold);
        cp2_likeCountdownLabel = (TextView) findViewById(R.id.cp2_likeCountdownLabel);
        cp2_likeCountdownLabel.setTypeface(firaSansMedium);
//        cp2_countIntervalLabel = (TextView) findViewById(R.id.cp2_countIntervalLabel);
//        cp2_countIntervalLabel.setTypeface(firaSansMedium);
        cp2_toothfieLabel = (TextView) findViewById(R.id.cp2_toothfieLabel);
        cp2_toothfieLabel.setTypeface(firaSansBold);
        cp2_savFin = (TextView) findViewById(R.id.cp2_savFin);
        cp2_savFin.setTypeface(firaSansMedium);
        cp2_sessionLabel = (TextView) findViewById(R.id.cp2_sessionLabel);
        cp2_sessionLabel.setTypeface(firaSansMedium);
        minutes1 = (TextView) findViewById(R.id.minutes1);
        minutes1.setTypeface(firaSansMedium);
        minutes2 = (TextView) findViewById(R.id.minutes2);
        minutes2.setTypeface(firaSansMedium);
        minutes3 = (TextView) findViewById(R.id.minutes3);
        minutes3.setTypeface(firaSansMedium);
        minutes4 = (TextView) findViewById(R.id.minutes4);
        minutes4.setTypeface(firaSansMedium);
//        minutes5 = (TextView) findViewById(R.id.minutes5);
//        minutes5.setTypeface(firaSansMedium);
        cp2_AMPM = (TextView) findViewById(R.id.cp2_AMPM);
        cp2_AMPM.setTypeface(firaSansMedium);

        swichCountDown = (Switch) findViewById(R.id.swichCountDown);
        swichCountDown.setChecked(false);

        swichCountDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    countDownSwitchValue = "1";
                } else {
                    countDownSwitchValue = "0";
                }


            }
        });

        whiteningTimesRadio = (RadioGroup) findViewById(R.id.cp2_rbGroup);
        NotificationRadio = (RadioGroup) findViewById(R.id.cp2_notificationRG);
        whiteningTimesRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedOption = whiteningTimesRadio.getCheckedRadioButtonId();
                whiten = (RadioButton) findViewById(selectedOption);
                // Toast.makeText(CreateProfile2.this,whiten.getText(),Toast.LENGTH_LONG).show();
//                whiteningSelection = whiten.getText().toString().replace(" ", "");
                whiteningSelection = whiten.getText().toString();
            }
        });
        NotificationRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedopt = NotificationRadio.getCheckedRadioButtonId();
                notification = (RadioButton) findViewById(selectedopt);
                // Toast.makeText(CreateProfile2.this,notification.getText(),Toast.LENGTH_LONG).show();
                notificationSelection = notification.getText().toString();
            }
        });

        cp2_Whitening4 = (TextView) findViewById(R.id.cp2_Whitening4);
        cp2_sessionTime = (TextView) findViewById(R.id.cp2_sessionTime);
        cp2_sessionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize a new time picker dialog fragment
                DialogFragment dFragment = new TimePickerFragment();

                // Show the time picker dialog fragment
                dFragment.show(getFragmentManager(), "Time Picker");
            }
        });
        cp2_Whitening1 = (Spinner) findViewById(R.id.cp2_Whitening1);
        cp2_Whitening2 = (Spinner) findViewById(R.id.cp2_Whitening2);
        cp2_Whitening3 = (Spinner) findViewById(R.id.cp2_Whitening3);
//        cp2_countDownInterval = (Spinner) findViewById(R.id.cp2_countDownInterval);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.whitening1, R.layout.spinner_item_test);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.whitening2, R.layout.spinner_item_test);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.whitening3, R.layout.spinner_item_test);
        adapter4 = ArrayAdapter.createFromResource(this, R.array.countdown_interval, R.layout.spinner_item_test);

        cp2_Whitening1.setAdapter((SpinnerAdapter) adapter1);
        cp2_Whitening2.setAdapter((SpinnerAdapter) adapter2);
        cp2_Whitening3.setAdapter((SpinnerAdapter) adapter3);
        cp2_Whitening3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cp2_Whitening4.setText(cp2_Whitening3.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        cp2_countDownInterval.setAdapter((SpinnerAdapter) adapter4);

        cp2_takeToothfieBtn = (Button) findViewById(R.id.cp2_takeToothfieBtn);
        cp2_takeToothfieBtn.setTypeface(firaSansMedium);
        cp2_takeToothfieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                startActivityForResult(intent, 30);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                picUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(intent, 30);

            }
        });
        cp2_toothfie = (ImageView) findViewById(R.id.cp2_toothfie);
        cp2_animatingTeeth = (ImageView) findViewById(R.id.cp2_animatingTeeth);
        cp2_animatingTeeth.setBackgroundResource(R.drawable.teeth_blink_animation);
        AnimationDrawable animation = (AnimationDrawable) cp2_animatingTeeth.getBackground();
        animation.start();

        RelativeLayout saveFinishButton = (RelativeLayout) findViewById(R.id.saveFinishButton);
        saveFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String whitening = "", first = "", second = "", third = "", countDownInterval = "", sessionTime = "", notification = "";
                whitening = whiteningSelection;
                first = cp2_Whitening1.getSelectedItem().toString();
                second = cp2_Whitening2.getSelectedItem().toString();
                third = cp2_Whitening3.getSelectedItem().toString();
                countDownInterval = countDownSwitchValue;


                sessionTime = cp2_sessionTime.getText().toString().trim()+" " + cp2_AMPM.getText().toString().trim();
                notification = notificationSelection;
//                if (Build.VERSION.SDK_INT >= 23) {
//                    if (!Settings.canDrawOverlays(getApplicationContext())) {
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                        startActivityForResult(intent, Overlay_REQUEST_CODE);
//
//                        return;
//
//                    }
//                }


                if (whitening.equalsIgnoreCase("") || whitening == null) {
                    Toast.makeText(CreateProfile2.this, "Please Enter Whitening Perferences", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sessionTime.equalsIgnoreCase("")) {
                    Toast.makeText(CreateProfile2.this, "Please Enter Session Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (notification.equalsIgnoreCase("") || notification == null) {
                    Toast.makeText(CreateProfile2.this, "Please Enter When you will be Notified", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (first.equalsIgnoreCase("") || first == null) {
                    Toast.makeText(CreateProfile2.this, "Please Enter First Whitening Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (second.equalsIgnoreCase("") || second == null) {
                    Toast.makeText(CreateProfile2.this, "Please Enter Second Whitening Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (third.equalsIgnoreCase("") || third == null) {
                    Toast.makeText(CreateProfile2.this, "Please Enter Third Whitening Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageUploadedCheck == false) {
                    Toast.makeText(CreateProfile2.this, "Please Upload your teeth image!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (GlobalClass.isOnline(getApplicationContext()) == true) {


                    AttemptSavePreferances attemptSavePreferances = new AttemptSavePreferances();
                    attemptSavePreferances.execute(whitening, first, second, third, countDownInterval, encoded_Img, sessionTime, notification);
                } else {
                    Toast.makeText(CreateProfile2.this, "Please Connect to Internet!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
//            case Overlay_REQUEST_CODE: {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    if (Settings.canDrawOverlays(getApplicationContext())) {
//
//                    }
//                } else {
//
//                }
//                break;
//            }

            case 30:


                if (resultCode == RESULT_OK) {

                    // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    picUri = data.getData();
                    //picUri = getImageUri(getApplicationContext(), bitmap);

//                    try {
//                        Bitmap   thumbnail = MediaStore.Images.Media.getBitmap(
//                                   getContentResolver(), picUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    performCrop(picUri);

                }


                break;
            case CROP_PIC:

                if (data != null) {

                    Uri imageUri = data.getData();


                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cp2_toothfie.setImageBitmap(bitmap);
                    Bitmap image = ((BitmapDrawable) cp2_toothfie.getDrawable()).getBitmap();
                    encoded_Img = imageToString(image);
                    imageUploadedCheck = true;
                }


                break;
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 700);
            cropIntent.putExtra("outputY", 700);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public class AttemptSavePreferances extends AsyncTask<String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(CreateProfile2.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait!");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();


        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String whitening, notification, firstTime, secondTime, thirdTime, countDownInterval, toothfie, session_time;
            whitening = strings[0];
            //notification=strings[1];
            firstTime = strings[1];
            secondTime = strings[2];
            thirdTime = strings[3];
            countDownInterval = strings[4];
            toothfie = strings[5];
            session_time = strings[6];
            notification = strings[7];
            String uId = SharedPreManager.getInstance(CreateProfile2.this).getCurrentUserId();
            //String uId="70";

            SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("timer_first_time", firstTime);
            editor.putString("timer_second_time", secondTime);
            editor.putString("timer_third_time", thirdTime);
            editor.putString("timer_interval", countDownInterval);
            editor.commit();

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("whiten_days", whitening));
            //params.add(new BasicNameValuePair("",notification));
            params.add(new BasicNameValuePair("firstTime", firstTime));
            params.add(new BasicNameValuePair("secondTime", secondTime));
            params.add(new BasicNameValuePair("thirdTime", thirdTime));
            params.add(new BasicNameValuePair("fourthTime", thirdTime));
            params.add(new BasicNameValuePair("countdown", countDownInterval));

            params.add(new BasicNameValuePair("userId", uId));
            params.add(new BasicNameValuePair("session_time", session_time));
            params.add(new BasicNameValuePair("notification", notification));
            params.add(new BasicNameValuePair("toothfieImage", toothfie));

            String url = Utils.BASE_URL + Utils.CREATE_PREFERENCE_URL;

            Log.d("session_time", params + "");
            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", params);


            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            if (jsonObject != null) {
                try {
                    String check = jsonObject.getString("statusCode");

                    Log.d("step2Response", jsonObject + "");
                    if (check.equals("1")) {


                        SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("PREF_USER", "1");
                        editor.commit();


                        Log.d("Create1Res", check + "");
//                        Toast.makeText(CreateProfile2.this,check,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateProfile2.this, AllSessions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateProfile2.this, check, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }





//    private void openFloatingWindow() {
//        Intent intent = new Intent(mActivity, FloatingWindow.class);
//        mActivity.stopService(intent);
//        mActivity.startService(intent);
//    }


}
