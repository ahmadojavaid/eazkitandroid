package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    private TextView settings_ChangePassword;
    private TextView settings_LeaveMessage;
    private TextView settings_CheckforUpdates;
    private TextView settings_EditProfile, settings_LeavAmazonReply;
    private TextView settings_AboutEazkit;
    private JSONParser jsonParser = new JSONParser();
    private String pinValue;
    private ImageView settings_ProfileSettingsLabel;
    private TextView settings_settingsLabel, settings_EAZKIT, settings_SupportLabel, settings_PushNotificationsLabel;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private Switch aSwitch;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

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

        settings_settingsLabel = (TextView) findViewById(R.id.settings_settingsLabel);
        settings_settingsLabel.setTypeface(firaSansMedium);
        settings_ProfileSettingsLabel = (ImageView) findViewById(R.id.settings_ProfileSettingsLabel);

        settings_ProfileSettingsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        settings_SupportLabel = (TextView) findViewById(R.id.settings_SupportLabel);
        settings_SupportLabel.setTypeface(firaSansBold);
        settings_PushNotificationsLabel = (TextView) findViewById(R.id.settings_PushNotificationsLabel);
        settings_PushNotificationsLabel.setTypeface(firaSansMedium);
        settings_EAZKIT = (TextView) findViewById(R.id.settings_EAZKIT);
        settings_EAZKIT.setTypeface(absoluteProReducedBlack);


        settings_ChangePassword = (TextView) findViewById(R.id.settings_ChangePassword);
        settings_LeaveMessage = (TextView) findViewById(R.id.settings_LeaveMessage);
        settings_CheckforUpdates = (TextView) findViewById(R.id.settings_CheckforUpdates);
        settings_AboutEazkit = (TextView) findViewById(R.id.settings_AboutEazkit);

        settings_ChangePassword.setTypeface(firaSansMedium);
        settings_LeaveMessage.setTypeface(firaSansMedium);
        settings_CheckforUpdates.setTypeface(firaSansMedium);
        settings_AboutEazkit.setTypeface(firaSansMedium);

        settings_AboutEazkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutEazkit.class);
                startActivity(intent);
            }
        });
        settings_LeavAmazonReply = (TextView) findViewById(R.id.settings_LeavAmazonReply);
        settings_LeavAmazonReply.setTypeface(firaSansMedium);

        settings_LeavAmazonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.com"));
                startActivity(intent);
            }
        });
        settings_EditProfile = (TextView) findViewById(R.id.settings_EditProfile);
        settings_EditProfile.setTypeface(firaSansMedium);

        settings_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });
        settings_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this,R.style.custom_alert_dialog);
                View mview = getLayoutInflater().inflate(R.layout.activity_change_password, null);
                mBuilder.setView(mview);
                final AlertDialog dialog = mBuilder.create();






                TextView changePass_changePassHeading = (TextView) mview.findViewById(R.id.changePass_changePassHeading);
                changePass_changePassHeading.setTypeface(firaSansBold);
                TextView changePass_body = (TextView) mview.findViewById(R.id.changePass_body);
                changePass_body.setTypeface(firaSansMedium);
                TextView changePass_resetLabel = (TextView) mview.findViewById(R.id.changePass_resetLabel);
                changePass_resetLabel.setTypeface(firaSansMedium);

                RelativeLayout changePass_resetBtn = (RelativeLayout) mview.findViewById(R.id.changePass_resetBtn);
                TextView changePass_closDiag = (TextView) mview.findViewById(R.id.changePass_closDiag);
                ImageView changePass_SmilingTooth = (ImageView) mview.findViewById(R.id.changePass_SmilingTooth);
                changePass_SmilingTooth.setBackgroundResource(R.drawable.teeth_blink_animation);
                AnimationDrawable animation = (AnimationDrawable) changePass_SmilingTooth.getBackground();
                animation.start();
                changePass_resetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(SettingActivity.this, "Reset button pressed!", Toast.LENGTH_LONG).show();
                        ForgotPassword forgotPassword = new ForgotPassword();
                        String email = SharedPreManager.getInstance(SettingActivity.this).getUserEmail();
                        //  Toast.makeText(SettingActivity.this,email,Toast.LENGTH_LONG).show();
                        forgotPassword.execute(email);
                        dialog.dismiss();
                    }
                });


                dialog.show();

                dialog.setCancelable(false);
                changePass_closDiag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                TextView alreadyHavePin = (TextView) mview.findViewById(R.id.alreadyHavePin);
                alreadyHavePin.setTypeface(firaSansMedium);
                alreadyHavePin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(SettingActivity.this,R.style.custom_alert_dialog);
                        View view1 = getLayoutInflater().inflate(R.layout.activity_input_pin, null);
                        TextView inputPin_InputHeading = (TextView) view1.findViewById(R.id.inputPin_InputHeading);
                        inputPin_InputHeading.setTypeface(firaSansBold);
                        TextView inputPass_ResetLabel = (TextView) view1.findViewById(R.id.inputPass_ResetLabel);
                        inputPass_ResetLabel.setTypeface(firaSansMedium);

                        TextView closeInputPin = (TextView) view1.findViewById(R.id.closeInputPin);
                        final PinView pinView = (PinView) view1.findViewById(R.id.pinView);

                        ImageView inputPin_teethImage = (ImageView) view1.findViewById(R.id.inputPin_teethImage);
                        inputPin_teethImage.setBackgroundResource(R.drawable.teeth_blink_animation);
                        AnimationDrawable animation = (AnimationDrawable) inputPin_teethImage.getBackground();
                        animation.start();
                        RelativeLayout resetButton = (RelativeLayout) view1.findViewById(R.id.resetButton);
                        resetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String pin = pinView.getText().toString();
                                pinValue = pinView.getText().toString();
                                Toast.makeText(SettingActivity.this, pin, Toast.LENGTH_LONG).show();
                                sendPin(pin);
                            }
                        });
                        mBuilder1.setView(view1);
                        final AlertDialog dialog1 = mBuilder1.create();
                        dialog.dismiss();
                        dialog1.show();
                        dialog1.setCancelable(false);

                        closeInputPin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                    }
                });
            }
        });

        settings_LeaveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LeaveAmessage.class);
                startActivity(intent);
            }
        });
        settings_CheckforUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(SettingActivity.this,R.style.custom_alert_dialog);
                View view2 = getLayoutInflater().inflate(R.layout.dialog_check4_updates, null);
                TextView checkUpdates_checkUpdatesHeading = (TextView) view2.findViewById(R.id.checkUpdates_checkUpdatesHeading);
                checkUpdates_checkUpdatesHeading.setTypeface(firaSansBold);
                TextView checkUpdates_versionLabel = (TextView) view2.findViewById(R.id.checkUpdates_versionLabel);
                checkUpdates_versionLabel.setTypeface(absoluteProReducedBlack);
                TextView checkUpdates_updateInfo = (TextView) view2.findViewById(R.id.checkUpdates_updateInfo);
                checkUpdates_updateInfo.setTypeface(firaSansMedium);
                TextView checkUpdates_continueLabel = (TextView) view2.findViewById(R.id.checkUpdates_continueLabel);
                checkUpdates_continueLabel.setTypeface(firaSansMedium);
                TextView closeUpdatesDiag = (TextView) view2.findViewById(R.id.closeUpdatesDiag);
                RelativeLayout continueButton = (RelativeLayout) view2.findViewById(R.id.continueButton);
                ImageView checkUpdates_ToothImage = (ImageView) view2.findViewById(R.id.checkUpdates_ToothImage);
                checkUpdates_ToothImage.setBackgroundResource(R.drawable.teeth_blink_animation);
                AnimationDrawable animation = (AnimationDrawable) checkUpdates_ToothImage.getBackground();
                animation.start();
                mBuilder2.setView(view2);
                final AlertDialog dialog2 = mBuilder2.create();
                dialog2.show();
                dialog2.setCancelable(false);

                closeUpdatesDiag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });

                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
            }
        });


        aSwitch = findViewById(R.id.settings_PushoNotificationsButton);


        sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
        String switchValue = sharedpreferences.getString("noti_switch", "1");

        if (switchValue.equalsIgnoreCase("1")) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    editor = sharedpreferences.edit();
                    editor.putString("noti_switch", "1");

                    editor.commit();
                } else {
                    editor = sharedpreferences.edit();
                    editor.putString("noti_switch", "0");

                    editor.commit();
                }

            }
        });

    }

    private class ForgotPassword extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            String email = strings[0];
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Utils.BASE_URL + Utils.FORGOT_PASSWORD_URL, "POST", params);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null) {
                try {
                    String status = jsonObject.getString("statusCode");
                    if (status.equals("1")) {
                        Toast.makeText(SettingActivity.this, jsonObject.getString("statusMessage") + " for PIN."
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SettingActivity.this, jsonObject.getString("statusMessage")
                                , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendPin(final String pin) {
        final String email = SharedPreManager.getInstance(SettingActivity.this).getUserEmail();
        StringRequest request = new StringRequest(Request.Method.GET, Utils.BASE_URL + Utils.VERIFY_PIN_URL + "email="
                + email + "&pinValue=" + pin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String statusCode = object.getString("statusCode");
                            if (statusCode.equals("1")) {
                                AlertDialog.Builder resetDialog = new AlertDialog.Builder(SettingActivity.this,R.style.custom_alert_dialog);
                                View resetLayout = getLayoutInflater().inflate(R.layout.dialog_set_new_pass, null);
                                resetDialog.setView(resetLayout);
                                final AlertDialog alertDialog = resetDialog.create();
                                alertDialog.show();
                                alertDialog.setCancelable(false);

                                //views of inflated layout
                                final EditText newPasswordETPass, newPasswordETConfirmPass;
                                TextView newPass_newPassHeading = (TextView) resetLayout.findViewById(R.id.newPass_newPassHeading);
                                newPass_newPassHeading.setTypeface(firaSansBold);
                                TextView newPass_newPasswdLabel = (TextView) resetLayout.findViewById(R.id.newPass_newPasswdLabel);
                                newPass_newPasswdLabel.setTypeface(firaSansMedium);
                                TextView newPass_confirmPasswd = (TextView) resetLayout.findViewById(R.id.newPass_confirmPasswd);
                                newPass_confirmPasswd.setTypeface(firaSansMedium);
                                TextView newPass_saveLabel = (TextView) resetLayout.findViewById(R.id.newPass_saveLabel);
                                newPass_saveLabel.setTypeface(firaSansMedium);

                                TextView closeInputPin;
                                RelativeLayout resetButton;
                                ImageView setPass_toothImage = (ImageView) resetLayout.findViewById(R.id.setPass_toothImage);
                                setPass_toothImage.setBackgroundResource(R.drawable.teeth_blink_animation);
                                AnimationDrawable animation = (AnimationDrawable) setPass_toothImage.getBackground();
                                animation.start();
                                newPasswordETPass = (EditText) resetLayout.findViewById(R.id.newPasswordETPass);
                                newPasswordETPass.setTypeface(firaSansLight);
                                newPasswordETConfirmPass = (EditText) resetLayout.findViewById(R.id.newPasswordETConfirmPass);
                                newPasswordETConfirmPass.setTypeface(firaSansLight);
                                resetButton = (RelativeLayout) resetLayout.findViewById(R.id.savePASSBTN);
                                closeInputPin = (TextView) resetLayout.findViewById(R.id.closeSetNewPassDiag);
                                closeInputPin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });

                                resetButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String newPass, confirmPass;
                                        newPass = newPasswordETPass.getText().toString();
                                        confirmPass = newPasswordETConfirmPass.getText().toString();
                                        if (!newPass.equals("") && !confirmPass.equals("") && newPass.equals(confirmPass)) {
                                            updatePassword(newPass, email);
                                            alertDialog.dismiss();
                                        } else {
                                            Toast.makeText(SettingActivity.this, "Password mismatch!", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });

                            } else {
                                Toast.makeText(SettingActivity.this, object.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void updatePassword(final String newPass, final String email) {
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.UPDATE_PASSWORD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String statusCode = jsonObject.getString("statusCode");
                            if (statusCode.equals("1")) {
                                Toast.makeText(SettingActivity.this, jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SettingActivity.this, jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(SettingActivity.this, "Not connected to internet!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", newPass);
                params.put("pinValue", pinValue);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
