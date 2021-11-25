package com.example.jbalpha.eazkitv8;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    RelativeLayout createAccountBtn;
    EditText et_username, et_email, et_password, et_confirmPassword;
    String username, email, password, confirmPassword;
    FrameLayout signUp_uploadPicture;
    ImageView cp_smileBlinkTooth;
    CircleImageView Signup_profile_picture;
    String encoded_img = "";
    Button signUp_SignUpLabel;
    //for conversion of image
    final int RequestPermissionCode = 1;
    JSONParser jsonParser = new JSONParser();
    Bitmap img;
    TextView SI_haveAccount;
    TextView tv_signupwith, createAccountLabel, signUp_EAZKIT, su_username, su_email, su_password, su_confirmPassword;
    Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private boolean imageUploadCheck = false;
    public final static int Overlay_REQUEST_CODE = 251;
    private CallbackManager callbackManager;
    private AccessToken mAccessToken;
    public final int RC_SIGN_IN = 94;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private String email_user_FBB, email_user_FB, userIdFromFB, birthdayFB, genderFB, locationFB, firstNameFB;
    private URL profilePicture;
    private String firstName, lastName;
    private String Name_From_Facbook;
    private String passForFB;
    private String passwordGoogle, name_from_google, email_from_google, passwordss_from_google, social_id_from_google, socialtype_from_google, accountPhotoGoogle;
    private File filePath;
    private ImageButton faceBookBtn, googleBtn;
    private int socialSignInStatus;
    private JSONObject jsonObject;

    private String encodedGoogle = "", encodedFB = "";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        facebookCallbacks();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.gray));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.gray));
        }
        int permissionCheck = ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.CAMERA);
        RequestRuntimePermission();

        //initializing typefaces here
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        signUp_SignUpLabel = (Button) findViewById(R.id.signUp_SignUpLabel);
        signUp_SignUpLabel.setTypeface(firaSansMedium);

        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmPassword = (EditText) findViewById(R.id.et_confirmPassword);
        Signup_profile_picture = (CircleImageView) findViewById(R.id.Signup_profile_picture);
        signUp_uploadPicture = (FrameLayout) findViewById(R.id.imageContainer);
        createAccountLabel = (TextView) findViewById(R.id.createAccountLabel);
        signUp_EAZKIT = (TextView) findViewById(R.id.signUp_EAZKIT);
        su_confirmPassword = (TextView) findViewById(R.id.su_confirmPassword);
        su_password = (TextView) findViewById(R.id.su_password);
        su_email = (TextView) findViewById(R.id.su_email);
        su_username = (TextView) findViewById(R.id.su_username);
        SI_haveAccount = (TextView) findViewById(R.id.SI_haveAccount);
        SI_haveAccount.setTypeface(firaSansRegular);
        SI_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });

        cp_smileBlinkTooth = (ImageView) findViewById(R.id.cp_smileBlinkTooth);
        cp_smileBlinkTooth.setBackgroundResource(R.drawable.smileblink_animation);
        AnimationDrawable animation = (AnimationDrawable) cp_smileBlinkTooth.getBackground();
        animation.start();

        //assigning typeFace to textViews and editTexts
        su_username.setTypeface(firaSansMedium);
        su_confirmPassword.setTypeface(firaSansMedium);
        su_password.setTypeface(firaSansMedium);
        su_username.setTypeface(firaSansMedium);
        su_email.setTypeface(firaSansMedium);
        et_email.setTypeface(firaSansLight);
        et_password.setTypeface(firaSansLight);
        et_confirmPassword.setTypeface(firaSansLight);
        et_username.setTypeface(firaSansLight);
        createAccountLabel.setTypeface(firaSansMedium);
        signUp_EAZKIT.setTypeface(absoluteProReducedBlack);

        tv_signupwith = (TextView) findViewById(R.id.tv_signupwith);
        tv_signupwith.setTypeface(firaSansRegular);
        signUp_uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        });

        createAccountBtn = (RelativeLayout) findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                confirmPassword = et_confirmPassword.getText().toString();

//
                if (username.equals("")) {
                    Toast.makeText(SignUp.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (emailValidator(email) == false) {
                    Toast.makeText(SignUp.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(SignUp.this, "Password Length must be greater than 6 character", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.equals("")) {
                    Toast.makeText(SignUp.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                } else if (imageUploadCheck == false) {
                    Toast.makeText(SignUp.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    AttemptSignup attemptSignup = new AttemptSignup();
                    attemptSignup.execute();
                }


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

        getKeyHash();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void signUp() {
        StringRequest request = new StringRequest(Request.Method.POST, Utils.BASE_URL + Utils.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(SignUp.this, object.getString("statusMessage"), Toast.LENGTH_LONG).show();
                            if (!object.getString("statusMessage").equals("email Already Exists")) {

                                Intent intent = new Intent(SignUp.this, CreateProfile1.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(SignUp.this, "No internet connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("profileImage", encoded_img);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(0, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue que = Volley.newRequestQueue(SignUp.this);
        que.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            switch (requestCode) {
                case Overlay_REQUEST_CODE:
                    if (Build.VERSION.SDK_INT >= 23) {
//                    if (Settings.canDrawOverlays(getApplicationContext())) {
//                        openFloatingWindow();
//                    }
                    } else {
//                    openFloatingWindow();
                    }
                    break;

                case 10:
                    Uri selected_image = data.getData();
                    Signup_profile_picture.setImageURI(selected_image);
                    //Converting image to base 64
                    Bitmap image = ((BitmapDrawable) Signup_profile_picture.getDrawable()).getBitmap();

                    Bitmap bitMapuse = getResizedBitmap(image, 200);

                    encoded_img = imageToString(bitMapuse);

                    imageUploadCheck = true;
                    et_username.requestFocus();

                    break;
            }


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


        } catch (Exception e) {
            e.getMessage();
        }
        getKeyHash();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, Manifest.permission.CAMERA))
            Toast.makeText(this, "CAMERA permssion allows the app to access Camera", Toast.LENGTH_LONG).show();
        else
            ActivityCompat.requestPermissions(SignUp.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
    }

    public class AttemptSignup extends AsyncTask<String, String, JSONObject> {

        ProgressDialog progressDialog = new ProgressDialog(SignUp.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Signing Up!");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String mail = email;
            String pass = password;
            String img = encoded_img;
            String userName = username;
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", mail));
            params.add(new BasicNameValuePair("password", pass));
            if (img != "") {
                params.add(new BasicNameValuePair("profileImage", img));
            }
            params.add(new BasicNameValuePair("username", userName));

            JSONObject jsonObject = jsonParser.makeHttpRequest(Utils.BASE_URL + Utils.SIGNUP_URL, "POST", params);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            if (jsonObject != null) {
                try {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                    String check = jsonObject.getString("statusMessage");
                    JSONObject jsonObject1 = jsonObject.getJSONObject("Result");
                    Log.d("UserSignUpResponse", jsonObject1 + "");
                    Log.d("response_signUP", jsonObject1 + "");
                    if (!check.equals("email Already Exists")) {
//                        SharedPreManager.getInstance(getApplicationContext()).userLogin(
//                                jsonObject1.getString("id"),
//                                jsonObject1.getString("token"),
//                                jsonObject1.getString("email"),
//                                jsonObject1.getString("username"),
//                                jsonObject1.getString("profileImage"),
//                                "");

//
//                        SharedPreferences sharedpreferences = getSharedPreferences("mysharedpref12", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                        String userID = jsonObject1.getString("id");
//                        editor.putString("user_id", userID);
//                        editor.commit();

                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("statusMessage"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Not connected to database", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void FacebookLogin() {

        if (GlobalClass.isOnline(getApplicationContext()) == true) {
            socialSignInStatus = 1;
            LoginManager.getInstance().logOut();
            LoginManager.getInstance().logInWithReadPermissions(SignUp.this, Arrays.asList("email", "public_profile", "user_friends"));


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
    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.jobesk.eazkit", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("MY_KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

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


                                    Name_From_Facbook = firstName + " " + lastName;
                                    if (email_user_FB == null) {
                                        Toast.makeText(SignUp.this, getApplicationContext().getString(R.string.fb_verify), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(SignUp.this, getResources().getString(R.string.login_cancel), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignUp.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

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


                WebReq.post(getApplicationContext(), "/External_Login", mParams, new SignUp.MyTextHttpResponseHandler());

            } else if (socialSignInStatus == 2) {
                RequestParams mParams = new RequestParams();
                mParams.put("username", name_from_google);
                mParams.put("token", social_id_from_google);
                mParams.put("profileImage", encodedGoogle);
                mParams.put("fullName", name_from_google);
                mParams.put("email", email_from_google);
                mParams.put("provider", "gmail");

                WebReq.post(getApplicationContext(), "/External_Login", mParams, new SignUp.MyTextHttpResponseHandler());
            }


        } else {
            Toast.makeText(this, "no internet Available!", Toast.LENGTH_SHORT).show();
        }


    }


    private class MyTextHttpResponseHandler extends JsonHttpResponseHandler {
        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);

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
                        SharedPreManager.getInstance(SignUp.this).userLogin(
                                result.getString("id"),
                                result.getString("token"),
                                result.getString("email"),
                                result.getString("username"),
                                result.getString("profileImage"),
                                result.getString("prefStatus")
                        );
                        UserProfileSharedPreferences.getInstance(SignUp.this).userProfile(
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

                            Intent intent = new Intent(SignUp.this, CreateProfile1.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            return;
                        } else if (agePref.equalsIgnoreCase("1")) {
                            if (prefsValuePref.equals("1")) {

                                Intent intent = new Intent(SignUp.this, AllSessions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return;
                            } else if (prefsValuePref.equals("0")) {
                                Intent intent = new Intent(SignUp.this, CreateProfile2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return;
                            }

                        }
                    } else {
                        String statusMessage = jsonObject.getString("statusMessage");
                        Toast.makeText(SignUp.this, statusMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
