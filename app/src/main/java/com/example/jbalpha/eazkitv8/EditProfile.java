package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private EditText ep_firstName, ep_lastName, ep_email, ep_address,
            ep_postal_code, ep_age;
    private EditText ep_userName;


    // String first,last,mail,address,postal,age;
    private ImageView editProfile_toothImage;
    private int cityy, stattt, countryy;

    private CircleImageView ep_profileImg;
    private ImageButton ep_TakePicture;
    private String encoded_Img = "";
    private JSONParser jsonParser = new JSONParser();
    private JSONParser profileParser = new JSONParser();
    private JSONObject profileObject;
    private String state = "";

    //strings to store user profile
    private String firstName, lastName, Username, Email, profileImage, countryKey, stateKey, cityKey, userAddress, postalCode, phoneNumber, userAge;

    private TextView editPro_PIHeading, editPro_FNLabel, editPro_LNLabel, editPro_ELabel, editPro_DLabel, editPro_EAZKITLabel,
            editPro_CountryLabel, editPro_StateLabel, editPro_CityLabel, editPro_AddressLabel, editPro_PostalLabel, editPro_AgeLabel,
            editPro_OptionalLabel, editPro_SavFinLabel, editPro_EAZKIT;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    private EditText stateValueEt;
    //ProgressDialog progressDialog=new ProgressDialog(EditProfile.this);
    private String statevalue = "";
    private EditText ep_city_et;
    private ArrayList<String> listOfCountries = new ArrayList<>();
    private ArrayAdapter<String> countryAdapter;
    private Spinner country_spinner;

    private String countryValue = "";
    private ImageView ImageBack;
    private TextView phone_tv;
    private EditText et_phone;
    private boolean isDelete = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.yellow));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }

        // Toast.makeText(EditProfile.this,SharedPreManager.getInstance(EditProfile.this).getProfileImage(),
        // Toast.LENGTH_LONG).show();

        //initializing typefaces
        firaSansMedium = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Medium.ttf");
        firaSansBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Bold.ttf");
        firaSansRegular = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Regular.ttf");
        firaSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_SemiBold.ttf");
        absoluteProReducedBlack = Typeface.createFromAsset(getAssets(), "fonts/absolut_pro_black_reduced.ttf");
        firaSansLight = Typeface.createFromAsset(getAssets(), "fonts/FiraSans_Light.ttf");

        //initializing textViews(labels) and applying typfaces
        editPro_PIHeading = (TextView) findViewById(R.id.editPro_PIHeading);
        editPro_PIHeading.setTypeface(firaSansBold);
        editPro_FNLabel = (TextView) findViewById(R.id.editPro_FNLabel);
        editPro_FNLabel.setTypeface(firaSansMedium);
        editPro_LNLabel = (TextView) findViewById(R.id.editPro_LNLabel);
        editPro_LNLabel.setTypeface(firaSansMedium);
        editPro_ELabel = (TextView) findViewById(R.id.editPro_ELabel);
        editPro_ELabel.setTypeface(firaSansMedium);
        editPro_DLabel = (TextView) findViewById(R.id.editPro_DLabel);
        editPro_DLabel.setTypeface(firaSansBold);
        editPro_EAZKITLabel = (TextView) findViewById(R.id.editPro_EAZKITLabel);
        editPro_EAZKITLabel.setTypeface(firaSansBold);
        editPro_CountryLabel = (TextView) findViewById(R.id.editPro_CountryLabel);
        editPro_CountryLabel.setTypeface(firaSansMedium);
        editPro_StateLabel = (TextView) findViewById(R.id.editPro_StateLabel);
        editPro_StateLabel.setTypeface(firaSansMedium);
        editPro_CityLabel = (TextView) findViewById(R.id.editPro_CityLabel);
        editPro_CityLabel.setTypeface(firaSansMedium);
        editPro_AddressLabel = (TextView) findViewById(R.id.editPro_AddressLabel);
        editPro_AddressLabel.setTypeface(firaSansMedium);
        editPro_PostalLabel = (TextView) findViewById(R.id.editPro_PostalLabel);
        editPro_PostalLabel.setTypeface(firaSansMedium);
        editPro_AgeLabel = (TextView) findViewById(R.id.editPro_AgeLabel);
        editPro_AgeLabel.setTypeface(firaSansMedium);
        editPro_OptionalLabel = (TextView) findViewById(R.id.editPro_OptionalLabel);
        editPro_OptionalLabel.setTypeface(firaSansRegular);
        editPro_SavFinLabel = (TextView) findViewById(R.id.editPro_SavFinLabel);
        editPro_SavFinLabel.setTypeface(firaSansMedium);
        editPro_EAZKIT = (TextView) findViewById(R.id.editPro_EAZKIT);
        editPro_EAZKIT.setTypeface(absoluteProReducedBlack);


        phone_tv = findViewById(R.id.et_phone);
        et_phone = findViewById(R.id.et_phone);


        phone_tv.setTypeface(absoluteProReducedBlack);
        et_phone.setTypeface(firaSansLight);


        ep_city_et = findViewById(R.id.ep_city_et);
        ep_city_et.setTypeface(firaSansLight);
      /*  first=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserFirstName();
        last=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserLastName();
        mail=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserEmail();
        address=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserAddress();
        postal=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserPostalCode();
        age=UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserAge();
        countryy=Integer.parseInt(UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserCountry());
        stattt=Integer.parseInt(UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserState());
        cityy=Integer.parseInt(UserProfileSharedPreferences.getInstance(EditProfile.this).getCurrentUserCity());*/

        ep_firstName = (EditText) findViewById(R.id.ep_firstName);
        ep_firstName.setTypeface(firaSansLight);

        ep_lastName = (EditText) findViewById(R.id.ep_lastName);
        ep_lastName.setTypeface(firaSansLight);

        ep_email = (EditText) findViewById(R.id.ep_email);
        ep_email.setTypeface(firaSansLight);

        ep_address = (EditText) findViewById(R.id.ep_address);
        ep_address.setTypeface(firaSansLight);

        ep_postal_code = (EditText) findViewById(R.id.ep_postal_code);
        ep_postal_code.setTypeface(firaSansLight);

        ep_age = (EditText) findViewById(R.id.ep_age);
        ep_age.setTypeface(firaSansLight);

        ep_userName = (EditText) findViewById(R.id.editPro_username);
        ep_userName.setTypeface(firaSansBold);


        stateValueEt = findViewById(R.id.stateValueEt);
        stateValueEt.setTypeface(firaSansLight);


        ImageBack = findViewById(R.id.ImageBack);
        ImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editProfile_toothImage = (ImageView) findViewById(R.id.editProfile_toothImage);
        editProfile_toothImage.setBackgroundResource(R.drawable.teeth_blink_animation);
        AnimationDrawable animation = (AnimationDrawable) editProfile_toothImage.getBackground();
        animation.start();


//        ep_state_spinner=(Spinner)findViewById(R.id.ep_state_spinner);
//        ep_city_spinner = (EditText) findViewById(R.id.ep_city_spinner);
//
//        stateAdapter = ArrayAdapter.createFromResource(this, R.array.states_arrays, R.layout.custom_spinner_layout);
//        cityAdapter = ArrayAdapter.createFromResource(this, R.array.city_arrays, R.layout.custom_spinner_layout);
//        countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.custom_spinner_layout);


        // ep_city_spinner.setAdapter((SpinnerAdapter)cityAdapter);
        //ep_state_spinner.setAdapter((SpinnerAdapter)stateAdapter);

        //getUserProfile();

        ep_profileImg = (CircleImageView) findViewById(R.id.ep_profileImg);
        Picasso.with(EditProfile.this).load(Utils.BASE_URL_IMAGE + SharedPreManager.
                getInstance(EditProfile.this).getProfileImage()).into(ep_profileImg);

//        AttemptGetProfile attemptGetProfile = new AttemptGetProfile();
//        attemptGetProfile.execute();

      /*  ep_firstName.setText(first);
        ep_lastName.setText(last);
        ep_email.setText(mail);
        ep_address.setText(address);
        ep_postal_code.setText(postal);
        ep_age.setText(age);
        ep_country_spinner.setSelection(countryy);
        ep_state_spinner.setSelection(stattt);
        ep_city_spinner.setSelection(cityy);
        ep_userName.setText(SharedPreManager.getInstance(EditProfile.this).getUsername());



        if(SharedPreManager.getInstance(EditProfile.this).getProfileImage()!=null){
            Picasso.with(EditProfile.this).load(Utils.BASE_URL+SharedPreManager.
                    getInstance(EditProfile.this).getProfileImage()).into(ep_profileImg);
        }
        else{
        ep_profileImg.setImageResource(R.drawable.image);
        }*/


        ep_TakePicture = (ImageButton) findViewById(R.id.ep_TakePicture);
        ep_TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
            }
        });

        RelativeLayout saveandFinishBTN = (RelativeLayout) findViewById(R.id.ep_saveBtn);
        saveandFinishBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalClass.isOnline(getApplicationContext()) == true) {
                    if (ep_firstName.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ep_lastName.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ep_email.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (GlobalClass.emailValidator(ep_email.getText().toString()) == false) {

                        Toast.makeText(EditProfile.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (ep_address.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter Address", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ep_postal_code.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter postal Code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ep_age.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter Age", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (countryValue.equalsIgnoreCase("")) {
                        Toast.makeText(EditProfile.this, "Enter Country", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    statevalue = stateValueEt.getText().toString();
                    if (statevalue.equalsIgnoreCase("")) {
                        Toast.makeText(EditProfile.this, "Enter State", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ep_city_et.getText().toString().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter City", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (et_phone.getText().equals("")) {
                        Toast.makeText(EditProfile.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    phoneNumber = et_phone.getText().toString().trim();

                    Bitmap image = ((BitmapDrawable) ep_profileImg.getDrawable()).getBitmap();
                    encoded_Img = imageToString(image);
                    AttemptUpdateProfile attemptRetrieveProfile = new AttemptUpdateProfile();

                    attemptRetrieveProfile.execute(
                            ep_firstName.getText().toString(),
                            ep_lastName.getText().toString(),
                            ep_email.getText().toString(),
                            ep_address.getText().toString(),
                            ep_postal_code.getText().toString(),
                            ep_age.getText().toString(),
                            countryValue,
                            statevalue,
                            ep_city_et.getText().toString().trim(),
                            encoded_Img,
                            ep_userName.getText().toString(),
                            state,
                            phoneNumber

                    );
                } else {
                    Toast.makeText(EditProfile.this, "Please Connec to Internet!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        JSONAsyncTask task = new JSONAsyncTask();
        task.execute();


        et_phone.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    isDelete = true;
                }
                return false;
            }
        });


        et_phone.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("lengthCharacter", editable.length() + "");

                if (isDelete == true) {
                    isDelete = false;
                    return;
                } else {
                    if (editable.length() == 3 || editable.length() == 8) {

                        String etvalue = et_phone.getText().toString().trim();

                        et_phone.setText(etvalue + " ");
                        et_phone.setSelection(editable.length() + 1);
                    }
                }


            }
        });


    }

    //saving profile after editing
    public class AttemptUpdateProfile extends AsyncTask<String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Updating Profile");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String first, last, mail, address, postal, age, country, state, city, image, uname, stat, phn;
            first = strings[0];
            last = strings[1];
            mail = strings[2];
            address = strings[3];
            postal = strings[4];
            age = strings[5];
            country = strings[6];
            state = strings[7];
            city = strings[8];
            image = strings[9];
            uname = strings[10];
            stat = strings[11];
            phn = strings[12];


            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("firstName", first));
            params.add(new BasicNameValuePair("lastName", last));
            params.add(new BasicNameValuePair("email", mail));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("postCode", postal));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("country", countryValue));
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("profileImage", image));
            params.add(new BasicNameValuePair("username", uname));
            params.add(new BasicNameValuePair("username", uname));
            params.add(new BasicNameValuePair("countryKey", countryValue));
            params.add(new BasicNameValuePair("stateKey", stat));
            params.add(new BasicNameValuePair("phoneNumber", phn));
            // params.add(new BasicNameValuePair("cityKey",String.valueOf(ep_city_spinner.getSelectedItemPosition())));

            String userId = SharedPreManager.getInstance(EditProfile.this).getCurrentUserId();


            JSONObject jsonObject = jsonParser.makeHttpRequest(Utils.BASE_URL + Utils.UPDATE_PROFILE_URL + userId, "POST", params);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            // Toast.makeText(EditProfile.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            if (jsonObject != null) {
                try {
                    String check = jsonObject.getString("statusCode");
                    if (check.equals("1")) {
                        JSONObject profileResponse = jsonObject.getJSONObject("Result");
                        String profileImg = profileResponse.getString("profileImage");
                        String userName = ep_userName.getText().toString();
                        //Toast.makeText(EditProfile.this,profileImg,Toast.LENGTH_LONG).show();
                        SharedPreManager.getInstance(EditProfile.this).setProfileImage(profileImg);
                        SharedPreManager.getInstance(EditProfile.this).setUserName(userName);
                        Toast.makeText(EditProfile.this, "Profile has been Updated!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditProfile.this, jsonObject.getString("statusCode"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selected_image = data.getData();
        ep_profileImg.setImageURI(selected_image);
        //Converting image to base 64
        Bitmap image = ((BitmapDrawable) ep_profileImg.getDrawable()).getBitmap();
        encoded_Img = imageToString(image);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void getUserProfile() {
        String uID = SharedPreManager.getInstance(EditProfile.this).getCurrentUserId();

        String url = Utils.BASE_URL + Utils.GET_CURRENT_PROFILE_URL + uID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("resPonseGetProfile", jsonObject + "");
                            String check = jsonObject.getString("statusCode");
                            if (check.equals("200")) {
                                JSONObject result = jsonObject.getJSONObject("Result");
                                firstName = result.getString("firstName");
                                lastName = result.getString("lastName");
//
                                Email = result.getString("email");
                                profileImage = result.getString("profileImage");
                                countryKey = result.getString("countryKey");
                                cityKey = result.getString("city");
                                stateKey = result.getString("state");
                                userAddress = result.getString("address");
                                postalCode = result.getString("postCode");
                                userAge = result.getString("age");
                                phoneNumber = result.getString("phoneNumber");


                                et_phone.setText(phoneNumber);
                                ep_firstName.setText(firstName);
                                ep_lastName.setText(lastName);

                                ep_email.setText(Email);
                                Picasso.with(EditProfile.this).load(Utils.BASE_URL_IMAGE + profileImage).into(ep_profileImg);

                                ep_city_et.setText(cityKey);
                                ep_address.setText(userAddress);
                                ep_postal_code.setText(postalCode);
                                ep_age.setText(userAge);
                                stateValueEt.setText(stateKey);


                                for (int i = 0; i < listOfCountries.size(); i++) {

                                    if (countryKey.equalsIgnoreCase(listOfCountries.get(i))) {


                                        country_spinner.setSelection(i);
                                    }

                                }


                                String uName = SharedPreManager.getInstance(EditProfile.this).getUsername();
                                ep_userName.setText(uName);

                            }


                        } catch (JSONException e) {
                            e.getMessage();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(EditProfile.this, "No internet Connection!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        queue.add(stringRequest);
    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EditProfile.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet("http://www.manageilm.com/countries.json");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                Log.d("responseCountries", response + "");
                if (response != null) {


                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONArray jsono = new JSONArray(data);
                    for (int i = 0; i < jsono.length(); i++) {

                        JSONObject jsonObject = jsono.getJSONObject(i);
                        String countryName = jsonObject.getString("name");
                        Log.d("JsonCountries", countryName + "");
                        listOfCountries.add(countryName);

                    }


                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            counrySpinner();
            getUserProfile();
        }
    }

    private void counrySpinner() {
        country_spinner = (Spinner) findViewById(R.id.ep_country_spinner);
        countryAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout, listOfCountries);

        country_spinner.setAdapter(countryAdapter);
        country_spinner.setSelection(0);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = country_spinner.getSelectedItem().toString();

                countryValue = selectedItem;
                Log.d("countryValue", countryValue);


//                if (!selectedItem.equals("United States")) {
//                    state_spinner.setClickable(false);
//                    state_spinner.setEnabled(false);
//                    state = "";
//                } else {
//                    stateAdapter = ArrayAdapter.createFromResource(CreateProfile1.this,
//                            R.array.states_arrays, R.layout.custom_spinner_layout);
//                    state_spinner.setClickable(true);
//                    state_spinner.setEnabled(true);
//                    state_spinner.setAdapter(stateAdapter);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
