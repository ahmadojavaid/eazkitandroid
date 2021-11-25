package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.UserProfileSharedPreferences;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.example.jbalpha.eazkitv8.Utils.WebReq;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class SignIn extends AppCompatActivity {

    private EditText SI_password, SI_email;
    private ImageView SI_SmileBlinkImage;
    private JSONParser jsonParser = new JSONParser();
    private TextView SI_emailLabel, SI_passwordLabel, SI_signUpLabel, SI_Eazkit;
    private Button SI_signinBtn;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private JSONObject jsonObject;
    private CallbackManager callbackManager;
    public final int RC_SIGN_IN = 94;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private AccessToken mAccessToken;
    private String email_user_FBB, email_user_FB, userIdFromFB, birthdayFB, genderFB, locationFB, firstNameFB;
    private URL profilePicture;
    private String firstName, lastName;
    private String Name_From_Facbook;

    private String passForFB;
    private String passwordGoogle, name_from_google, email_from_google, passwordss_from_google, social_id_from_google, socialtype_from_google, accountPhotoGoogle;
    private ImageButton faceBookBtn, googleBtn;
    private String encodedFB = "", encodedGoogle = "";


    private int socialSignInStatus;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        facebookCallbacks();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Window window = getWindow();

        SI_email = (EditText) findViewById(R.id.SI_email);
        SI_password = (EditText) findViewById(R.id.SI_password);
        SI_emailLabel = (TextView) findViewById(R.id.SI_emailLabel);
        SI_passwordLabel = (TextView) findViewById(R.id.SI_passwordLabel);
        SI_signUpLabel = (TextView) findViewById(R.id.SI_signUpLabel);
        SI_signinBtn = (Button) findViewById(R.id.SI_signinBtn);
        SI_Eazkit = (TextView) findViewById(R.id.SI_Eazkit);

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        //applying typfaces on textViews and editTexts
        SI_password.setTypeface(firaSansLight);
        SI_email.setTypeface(firaSansLight);
        SI_emailLabel.setTypeface(firaSansMedium);
        SI_passwordLabel.setTypeface(firaSansMedium);
        SI_signinBtn.setTypeface(firaSansMedium);
        SI_signUpLabel.setTypeface(firaSansRegular);
        SI_Eazkit.setTypeface(absoluteProReducedBlack);

        //adding animation on tooth image
        SI_SmileBlinkImage = (ImageView) findViewById(R.id.SI_SmileBlinkImage);
        SI_SmileBlinkImage.setBackgroundResource(R.drawable.smileblink_animation);
        AnimationDrawable animation = (AnimationDrawable) SI_SmileBlinkImage.getBackground();
        animation.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.gray));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.gray));
        }

        RelativeLayout signInButton = (RelativeLayout) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AttemptSignIn signIn=new AttemptSignIn();
                String email=SI_email.getText().toString();
                String password=SI_password.getText().toString();
                signIn.execute(email,password); */
                signIn();
            }
        });


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestEmail()
                .build();


        //  AccountPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                // .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
        mGoogleApiClient.connect();

        faceBookBtn = findViewById(R.id.faceBookBtn);
        googleBtn = findViewById(R.id.googleBtn);

        faceBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookLogin();
            }
        });
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleLogin();
            }
        });

    }

    private void signIn() {
        final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setMessage("Signing In!!");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            jsonObject = new JSONObject(response);

                            Log.d("loginResponse", response + "");
                            String check = jsonObject.getString("statusCode");

                            if (check.equals("1")) {
                                JSONObject result = jsonObject.getJSONObject("Result");
                                SharedPreManager.getInstance(SignIn.this).userLogin(
                                        result.getString("id"),
                                        result.getString("token"),
                                        result.getString("email"),
                                        result.getString("username"),
                                        result.getString("profileImage"),
                                        result.getString("prefStatus")
                                );
                                UserProfileSharedPreferences.getInstance(SignIn.this).userProfile(
                                        result.getString("firstName"),
                                        result.getString("lastName"),
                                        result.getString("email"),
                                        result.getString("countryKey"),
                                        result.getString("stateKey"),
                                        result.getString("cityKey"),
                                        result.getString("address"),
                                        result.getString("postCode"),
                                        result.getString("age"),
                                        result.getString("prefStatus")
                                );


                                String age = result.getString("age");
                                String prefsValue = result.getString("prefStatus");

                                SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("PREF_USER", prefsValue);

                                editor.commit();

                                if (age.equalsIgnoreCase("null")) {

                                    editor.putString("ageSet", "0");

                                    editor.commit();


                                } else {

                                    editor.putString("ageSet", "1");
                                    editor.commit();
                                }


                                String agePref = sharedpreferences.getString("ageSet", "null");
                                String prefsValuePref = sharedpreferences.getString("PREF_USER", "0");

                                if (agePref.equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(SignIn.this, CreateProfile1.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    return;
                                } else if (agePref.equalsIgnoreCase("1")) {
                                    if (prefsValuePref.equals("1")) {

                                        Intent intent = new Intent(SignIn.this, AllSessions.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        return;
                                    } else if (prefsValuePref.equals("0")) {
                                        Intent intent = new Intent(SignIn.this, CreateProfile2.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        return;
                                    }

                                }

                            } else {
                                String statusMessage = jsonObject.getString("statusMessage");
                                Toast.makeText(SignIn.this, statusMessage, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
                Toast.makeText(SignIn.this, "No internet connection!", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", SI_email.getText().toString());
                params.put("password", SI_password.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(SignIn.this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


    private void FacebookLogin() {

        if (GlobalClass.isOnline(getApplicationContext()) == true) {
            socialSignInStatus = 1;
            LoginManager.getInstance().logOut();
            LoginManager.getInstance().logInWithReadPermissions(SignIn.this, Arrays.asList("email", "public_profile", "user_friends"));


        } else {
            Toast.makeText(this, "Connect to inernet!", Toast.LENGTH_SHORT).show();
        }


    }

    private void GoogleLogin() {


        if (GlobalClass.isOnline(getApplicationContext()) == true) {

            socialSignInStatus = 2;
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);

        } else {
            Toast.makeText(this, "Connect to inernet!", Toast.LENGTH_SHORT).show();
        }


    }


    private void facebookCallbacks() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("status_fb_login", loginResult.toString());

                        mAccessToken = loginResult.getAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("respooooFB", object.toString());
                                Log.e("respooooFB", response.toString());


                                try {
                                    userIdFromFB = object.getString("id");
                                    try {
                                        profilePicture = new URL("https://graph.facebook.com/" + userIdFromFB + "/picture?width=500&height=500");
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    if (object.has("first_name"))
                                        firstName = object.getString("first_name");
                                    if (object.has("last_name"))
                                        lastName = object.getString("last_name");
                                    if (object.has("email"))
                                        email_user_FB = object.getString("email");
//                                    if (object.has("birthday"))
//                                        birthday = object.getString("birthday");
//                                    if (object.has("gender"))
//                                        gender = object.getString("gender");

                                    Name_From_Facbook = firstName + " " + lastName;
                                    if (email_user_FB == null) {
                                        Toast.makeText(SignIn.this, getApplicationContext().getString(R.string.fb_verify), Toast.LENGTH_SHORT).show();

                                    } else {

                                        Random r = new Random();
                                        int NUmber = r.nextInt(100000 - 0) + 0;
                                        passForFB = "987654" + NUmber + "@randomPass123_android";
                                        Log.d("passsslength", passForFB);


                                        String UrlImageFb = "https://graph.facebook.com/" + userIdFromFB + "/picture?type=large";
                                        Bitmap bitmap = getBitmapFromURL(UrlImageFb);


                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                        byte[] byteArray = byteArrayOutputStream.toByteArray();

                                        encodedFB = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                        Log.d("encodedImage", encodedFB);

                                        signInUser();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        //Here we put the requested fields to be returned from the JSONObject
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email");
                        // parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignIn.this, getResources().getString(R.string.login_cancel), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignIn.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            try {
//
//                int statusCode = result.getStatus().getStatusCode();
//
//                Log.d("statusCode", statusCode + "");
                handleSignInResult(result);


            } catch (Exception e) {

            }

        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("handleSignInResult", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            name_from_google = acct.getDisplayName();
            email_from_google = acct.getEmail();

            social_id_from_google = acct.getId();
            socialtype_from_google = "google";
            accountPhotoGoogle = null;
            try {
                accountPhotoGoogle = acct.getPhotoUrl().toString();
            } catch (Exception E) {

            }


            Log.i("GoogleRespose", acct.getPhotoUrl() + "");
            Random r = new Random();
            int number = r.nextInt(999999 - 0) + 0;

            passwordGoogle = "asdkjGth" + number;


            if (accountPhotoGoogle != null) {
                Bitmap bitsss = getBitmapFromURL(accountPhotoGoogle);
                Bitmap resized = getResizedBitmap(bitsss, 500, 500);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                encodedGoogle = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("encodedImage", encodedFB);

            } else {


                encodedGoogle = "";

            }


            signInUser();

        } else {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.gmail_login_unsuccessfull), Toast.LENGTH_SHORT).show();
        }
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
            e.printStackTrace();
            return null;
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void signInUser() {


        if (GlobalClass.isOnline(getApplicationContext()) == true) {


            if (socialSignInStatus == 1) {
                RequestParams mParams = new RequestParams();
                mParams.put("username", Name_From_Facbook);
                mParams.put("token", userIdFromFB);
                mParams.put("profileImage", encodedFB);
                mParams.put("fullName", Name_From_Facbook);
                mParams.put("email", email_user_FB);
                mParams.put("provider", "facebook");


                WebReq.post(getApplicationContext(), "/External_Login", mParams, new MyTextHttpResponseHandler());

            } else if (socialSignInStatus == 2) {
                RequestParams mParams = new RequestParams();
                mParams.put("username", name_from_google);
                mParams.put("token", social_id_from_google);
                mParams.put("profileImage", encodedGoogle);
                mParams.put("fullName", name_from_google);
                mParams.put("email", email_from_google);
                mParams.put("provider", "gmail");

                WebReq.post(getApplicationContext(), "/External_Login", mParams, new MyTextHttpResponseHandler());
            }


        } else {
            Toast.makeText(this, "no internet Available!", Toast.LENGTH_SHORT).show();
        }


    }


    private class MyTextHttpResponseHandler extends JsonHttpResponseHandler {
        final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);

        MyTextHttpResponseHandler() {
        }

        @Override
        public void onStart() {
            super.onStart();

            progressDialog.setMessage("Signing In!!");
            progressDialog.show();


        }

        @Override
        public void onFinish() {
            super.onFinish();


            progressDialog.dismiss();

        }

        @Override
        public void onFailure(int mStatusCode, Header[] headers, Throwable mThrow, JSONObject e) {
            Log.d("response_social", "OnFailure" + e);
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.d("response_social", responseString + throwable);
            progressDialog.dismiss();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONObject mResponse) {

            progressDialog.dismiss();
            Log.d("response_social", mResponse.toString() + "Respo");
            if (mResponse != null && mResponse.length() != 0) {
                try {
                    String check = mResponse.getString("statusCode");
                    if (check.equals("1")) {
                        JSONObject result = mResponse.getJSONObject("Result");
                        SharedPreManager.getInstance(SignIn.this).userLogin(
                                result.getString("id"),
                                result.getString("token"),
                                result.getString("email"),
                                result.getString("username"),
                                result.getString("profileImage"),
                                result.getString("prefStatus")
                        );
                        UserProfileSharedPreferences.getInstance(SignIn.this).userProfile(
                                result.getString("firstName"),
                                result.getString("lastName"),
                                result.getString("email"),
                                result.getString("countryKey"),
                                result.getString("stateKey"),
                                result.getString("cityKey"),
                                result.getString("address"),
                                result.getString("postCode"),
                                result.getString("age"),
                                result.getString("prefStatus")
                        );


                        String age = result.getString("age");
                        String prefsValue = result.getString("prefStatus");

                        SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("PREF_USER", prefsValue);

                        editor.commit();

                        if (age.equalsIgnoreCase("null")) {

                            editor.putString("ageSet", "0");

                            editor.commit();


                        } else {

                            editor.putString("ageSet", "1");
                            editor.commit();
                        }


                        String agePref = sharedpreferences.getString("ageSet", "null");
                        String prefsValuePref = sharedpreferences.getString("PREF_USER", "0");

                        if (agePref.equalsIgnoreCase("0")) {

                            Intent intent = new Intent(SignIn.this, CreateProfile1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            return;
                        } else if (agePref.equalsIgnoreCase("1")) {
                            if (prefsValuePref.equals("1")) {

                                Intent intent = new Intent(SignIn.this, AllSessions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return;
                            } else if (prefsValuePref.equals("0")) {
                                Intent intent = new Intent(SignIn.this, CreateProfile2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return;
                            }

                        }
                    } else {
                        String statusMessage = jsonObject.getString("statusMessage");
                        Toast.makeText(SignIn.this, statusMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
