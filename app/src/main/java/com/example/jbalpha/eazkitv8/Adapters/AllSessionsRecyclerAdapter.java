package com.example.jbalpha.eazkitv8.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.Models.AllSessionsModel;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllSessionsRecyclerAdapter extends RecyclerView.Adapter<AllSessionsRecyclerAdapter.AllSessionsViewHolder> {

    private ArrayList<AllSessionsModel> allSessionsModels;
    private Context context;

    private ImageView
            shareButtons_Twitter, shareButtons_Facebook, shareButtons_GooglePlus, shareButtons_Mail;
    private ImageButton closeBtnDiagSocialShare;
    private Dialog shareButtonsDiag;
    private Activity activity;
    private AlertDialog alertRateAgain;
    private String session_rating;

    public AllSessionsRecyclerAdapter(ArrayList<AllSessionsModel> allSessionsModels, Context context, Activity activity) {
        this.allSessionsModels = allSessionsModels;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AllSessionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_sessions_layout, viewGroup, false);

        AllSessionsViewHolder allSessionsViewHolder = new AllSessionsViewHolder(view);
        return allSessionsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllSessionsViewHolder viewHolder, final int position) {


        //initializing typefaces
        final AllSessionsModel model = allSessionsModels.get(position);

        String toothImage = Utils.BASE_URL_IMAGE + model.getSessionImage();

        int pos = position + 1;

        Picasso.with(context).load(toothImage).into(viewHolder.session_Toothfie);
        viewHolder.session_SessionNumber.setText(pos + "");

        String SessionTImeCaridOut = model.getTimeCarriedOut();
        String SessionactualTimeCarieedOUT = model.getActualTimeCarriedOut();
        String sessionDate = parseDateToddMMyyyy(model.getDate());


        String[] split = sessionDate.split(" ");
        String Date = split[0];
        String Time = split[1];
        String amPM = split[2];
        viewHolder.sessions_timeTv.setText(Time + " " + amPM);
        viewHolder.sessions_labelToday.setText(Date);


        viewHolder.session_SessionCarriedOut.setText(SessionTImeCaridOut + " minutes");
        viewHolder.sessions_actualTime.setText(SessionactualTimeCarieedOUT);
        viewHolder.allSessions_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displaying dialog for share buttons here
                shareButtonsDiag = new Dialog(context);
                shareButtonsDiag.setContentView(R.layout.activity_circle_buttons);


                shareButtons_Twitter = shareButtonsDiag.findViewById(R.id.shareButtons_Twitter);

                shareButtons_Facebook = shareButtonsDiag.findViewById(R.id.shareButtons_Facebook);
                shareButtons_GooglePlus = shareButtonsDiag.findViewById(R.id.shareButtons_GooglePlus);
                shareButtons_Mail = shareButtonsDiag.findViewById(R.id.shareButtons_Mail);
                closeBtnDiagSocialShare = shareButtonsDiag.findViewById(R.id.closeBtn);


                shareButtonsDiag.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                shareButtonsDiag.show();


                shareButtons_Facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {


                            boolean isAppInstalled = appInstalledOrNot("com.facebook.katana");

                            if (isAppInstalled) {

                                String url = Utils.BASE_URL_IMAGE + "" + model.getSessionImage();
                                Bitmap bitmapImage = getBitmapFromURL(url);
                                Uri uri = getImageUri(context, bitmapImage);
                                share("com.facebook.katana", uri, "your comment", 1);
                            } else {
                                Toast.makeText(context, "Install Facebook app first", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Unable to get this image", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                shareButtons_GooglePlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {


                            boolean isAppInstalled = appInstalledOrNot("com.google.android.apps.plus");

                            if (isAppInstalled) {

                                String url = Utils.BASE_URL_IMAGE + "" + model.getSessionImage();
                                Bitmap bitmapImage = getBitmapFromURL(url);
                                Uri uri = getImageUri(context, bitmapImage);

                                share("com.google.android", uri, "your comment", 2);
                            } else {
                                Toast.makeText(context, "Install Google+ app first", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Unable to get this image", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                shareButtons_Mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {

                            boolean isAppInstalled = appInstalledOrNot("com.instagram.android");

                            if (isAppInstalled) {

                                String url = Utils.BASE_URL_IMAGE + "" + model.getSessionImage();
                                Bitmap bitmapImage = getBitmapFromURL(url);
                                Uri uri = getImageUri(context, bitmapImage);
                                share("com.instagram.android", uri, "your comment", 3);

                            } else {
                                Toast.makeText(context, "Install Instagram app first", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Unable to get this image", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                shareButtons_Twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            boolean isAppInstalled = appInstalledOrNot("com.twitter.android");
                            if (isAppInstalled) {

                                String url = Utils.BASE_URL_IMAGE + "" + model.getSessionImage();
                                Bitmap bitmapImage = getBitmapFromURL(url);
                                Uri uri = getImageUri(context, bitmapImage);
                                share("com.twitter.android", uri, "your comment", 0);

                            } else {
                                Toast.makeText(context, "Install Instagram app first", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Unable to get this image", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                closeBtnDiagSocialShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        shareButtonsDiag.dismiss();
                    }
                });
            }
        });
        if (!model.getSessionRating().equals("null")) {
            float rating = Float.parseFloat(model.getSessionRating());


            viewHolder.session_SessionRating.setVisibility(View.VISIBLE);
            viewHolder.session_VerbalRating.setVisibility(View.VISIBLE);
            viewHolder.session_RateNowBtn.setVisibility(View.GONE);

            if (rating == 1)
                viewHolder.session_VerbalRating.setText("Normal");
            else if (rating == 2) {
                viewHolder.session_VerbalRating.setText("Above Normal");
            } else if (rating == 3) {
                viewHolder.session_VerbalRating.setText("Good");
            } else if (rating == 4) {
                viewHolder.session_VerbalRating.setText("Satisfactory");
            } else {
                viewHolder.session_VerbalRating.setText("Excellent");
            }


            viewHolder.session_SessionRating.setClickable(false);
            viewHolder.session_SessionRating.setFocusable(false);
            viewHolder.session_SessionRating.setFocusableInTouchMode(false);


            if (rating > 3 || rating == 3) {
                viewHolder.Mycircle.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.blue_circle_stroking));

//                Drawable drawable = viewHolder.session_SessionRating.getProgressDrawable();
//                drawable.setColorFilter(Color.parseColor("#0074CD"),PorterDuff.Mode.SRC_ATOP);
            } else {

//                Drawable drawable = viewHolder.session_SessionRating.getProgressDrawable();
//                drawable.setColorFilter(Color.parseColor("#FF473D"),PorterDuff.Mode.SRC_ATOP);
                viewHolder.Mycircle.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.red_circle_stroking));
            }
            viewHolder.session_SessionRating.setRating(rating);
        } else {

            viewHolder.Mycircle.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.red_circle_stroking));
            viewHolder.session_SessionRating.setVisibility(View.GONE);
            viewHolder.session_VerbalRating.setVisibility(View.GONE);
            viewHolder.session_RateNowBtn.setVisibility(View.VISIBLE);
            viewHolder.sessionAnim.setBackgroundResource(R.drawable.animation_blinktongue);
            AnimationDrawable animation = (AnimationDrawable) viewHolder.sessionAnim.getBackground();
            animation.start();


        }


        viewHolder.session_RateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sessionID = model.getSession_id();


                showChangeLangDialog(sessionID, position, model);

            }
        });



        Log.d("positions",position+"");
    }


    public void showChangeLangDialog(final String sessionID, final int position, final AllSessionsModel model) {
        session_rating = "null";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dialogView = inflater.inflate(R.layout.custom_dialog_rate_again, null);
        dialogBuilder.setView(dialogView);
        RatingBar rateSession_sessionRating = (RatingBar) dialogView.findViewById(R.id.rateSession_sessionRating);
        final TextView rateSession_ratingSubtitle = (TextView) dialogView.findViewById(R.id.rateSession_ratingSubtitle);
        final TextView not_now_tv = (TextView) dialogView.findViewById(R.id.not_now_tv);
        not_now_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertRateAgain.dismiss();
            }
        });

        rateSession_sessionRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 1) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Normal");
                } else if (rating == 2) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Above Normal");
                } else if (rating == 3) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Good");
                } else if (rating == 4) {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Satisfactory");
                } else {
                    session_rating = String.valueOf(rating);
                    rateSession_ratingSubtitle.setText("Excellent");
                }
            }
        });
        RelativeLayout SubmitRating = (RelativeLayout) dialogView.findViewById(R.id.SubmitRating);
        SubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session_rating.equalsIgnoreCase("null")) {

                    Toast.makeText(activity, "Please enter ratting", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertRateAgain.dismiss();
                model.setSessionRating(String.valueOf(session_rating));
                notifyDataSetChanged();
                Log.d("listvalues", allSessionsModels + "");
                SharedPreferences myPrefs = activity.getSharedPreferences("mysharedpref12", Context.MODE_PRIVATE);
                String userId = myPrefs.getString("user_id", "");
                postRatingForSession(sessionID, userId, session_rating, position);


            }
        });


        alertRateAgain = dialogBuilder.create();
        alertRateAgain.show();
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return allSessionsModels.size();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }

    }

    private String getImagePath(String ImageLink) {

        File storagePath = null;


        return storagePath.toString();
    }

    public static class AllSessionsViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout Mycircle, session_RateNowBtn, allSessions_share;
        TextView session_SessionNumber, session_SessionCarriedOut, sessions_actualTime, session_VerbalRating;
        ImageView session_Toothfie, sessionAnim;
        RatingBar session_SessionRating;
        Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
        TextView sessions_timeTv;
        //labels
        TextView sessions_TimeCarriedOutLabel, sessions_ActualCarriedOutLabel, sessions_labelToday, session_RateNowLabel;

        public AllSessionsViewHolder(@NonNull View itemView) {
            super(itemView);

            //initializing typefaces
            firaSansMedium = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/FiraSans_Medium.ttf");
            firaSansBold = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/FiraSans_Bold.ttf");
            firaSansRegular = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/FiraSans_Regular.ttf");
            firaSansSemiBold = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/FiraSans_SemiBold.ttf");
            absoluteProReducedBlack = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/absolut_pro_black_reduced.ttf");
            firaSansLight = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/FiraSans_Light.ttf");

            //initializing labels
            sessions_TimeCarriedOutLabel = itemView.findViewById(R.id.sessions_TimeCarriedOutLabel);
            sessions_TimeCarriedOutLabel.setTypeface(firaSansBold);

            sessions_ActualCarriedOutLabel = itemView.findViewById(R.id.sessions_ActualCarriedOutLabel);
            sessions_ActualCarriedOutLabel.setTypeface(firaSansLight);

            sessions_labelToday = itemView.findViewById(R.id.sessions_labelToday);
            sessions_labelToday.setTypeface(firaSansMedium);

            Mycircle = (RelativeLayout) itemView.findViewById(R.id.Mycircle);
            sessionAnim = (ImageView) itemView.findViewById(R.id.sessionAnim);
            session_RateNowBtn = (RelativeLayout) itemView.findViewById(R.id.session_RateNowBtn);
            allSessions_share = (RelativeLayout) itemView.findViewById(R.id.allSessions_share);


            session_RateNowLabel = (TextView) itemView.findViewById(R.id.session_RateNowLabel);
            session_RateNowLabel.setTypeface(firaSansMedium);

            session_SessionNumber = (TextView) itemView.findViewById(R.id.session_SessionNumber);
            session_SessionNumber.setTypeface(firaSansSemiBold);

            session_SessionCarriedOut = (TextView) itemView.findViewById(R.id.session_SessionCarriedOut);
            session_SessionCarriedOut.setTypeface(firaSansBold);

            sessions_actualTime = (TextView) itemView.findViewById(R.id.sessions_actualTime);
            sessions_actualTime.setTypeface(firaSansLight);

            session_VerbalRating = (TextView) itemView.findViewById(R.id.session_VerbalRating);
            session_VerbalRating.setTypeface(firaSansMedium);

            session_Toothfie = (ImageView) itemView.findViewById(R.id.session_Toothfie);
            session_SessionRating = (RatingBar) itemView.findViewById(R.id.session_SessionRating);


            sessions_timeTv = itemView.findViewById(R.id.sessions_timeTv);
        }
    }

    private void postRatingForSession(String sessionId, final String userId, final String rattingValue, final int position) {
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.UPDATE_SESSION_URL + sessionId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("statusCode");
                            if (check.equals("1")) {
                                Toast.makeText(context, "Your session has been successfully updated!", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(context, jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("sessionRating", rattingValue);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void share(String nameApp, Uri uriImage, String message, int appStatus) {
        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("image/jpeg");
            List<ResolveInfo> resInfo = context.getPackageManager()
                    .queryIntentActivities(share, 0);

            boolean appFound = false;
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(
                            android.content.Intent.ACTION_SEND);
                    targetedShare.setType("image/jpeg"); // put here your mime
                    // type
                    if (info.activityInfo.packageName.toLowerCase().contains(

                            nameApp)
                            || info.activityInfo.name.toLowerCase().contains(
                            nameApp)) {

                        appFound = true;

                        targetedShare.putExtra(Intent.EXTRA_SUBJECT,
                                "Session Photo");
                        targetedShare.putExtra(Intent.EXTRA_TEXT, message);
                        targetedShare.putExtra(Intent.EXTRA_STREAM,
                                uriImage);
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);


                    } else {


//                        if (appFound==false){
//                            switch (appStatus) {
//
//                                case 0:
//                                    Toast.makeText(context, "Please Intall Twitter App!", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 1:
//                                    Toast.makeText(context, "Please Intall FaceBook App!", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 2:
//                                    Toast.makeText(context, "Please Intall Gmail App!", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 3:
//                                    Toast.makeText(context, "Please Intall Instagram App!", Toast.LENGTH_SHORT).show();
//                                    break;
//
//                            }
//                        }

                    }
                }


                ///////////////
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }
        } catch (Exception e) {
            Log.v("VM",
                    "Exception while sending image on" + nameApp + " "
                            + e.getMessage());
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
