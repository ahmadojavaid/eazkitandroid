package com.example.jbalpha.eazkitv8;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
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

import com.example.jbalpha.eazkitv8.Models.JSONParser;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.UserProfileSharedPreferences;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfile1 extends AppCompatActivity {

    private Spinner country_spinner;
    private EditText city_spinner;
    private SpinnerAdapter cityAdapter, stateAdapter;
    ArrayAdapter<String> countryAdapter;
    private CircleImageView profileImg;
    private ImageButton cp1_changeProfilePic;
    private EditText cp_firstName, cp_email, cp_address, cp_postal_code, cp_age, cp_lastname;
    private String encoded_Img = "";
    private JSONParser jsonParser = new JSONParser();
    private String firstname, lastname, email, address, postal_code, age="", country, city;
    private TextView cp2_username;
    private ImageView cp_smilingTeethAnimation;
    private TextView stepsId, personal_Info, tv_firstName, tv_lastName, tv_email, label_delivery, label_eazkit, tv_country,
             tv_city, tv_address, tv_postalCode, tv_age, tv_optionalLabel, cp_continueLabel, cp1_EAZKIT;
    private Typeface firaSansMedium, firaSansBold, firaSansRegular, firaSansSemiBold, absoluteProReducedBlack, firaSansLight;
    ProgressDialog progressDialog;
//    private String StateValue = "";

    private String countryValue = "", phoneNumber;
    private ArrayList<String> listOfCountries = new ArrayList<>();
    private EditText et_phone;
    private TextView tv_phone;
    private boolean isDelete = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_one);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        stepsId = (TextView) findViewById(R.id.stepsId);
        personal_Info = (TextView) findViewById(R.id.personal_Info);
        tv_firstName = (TextView) findViewById(R.id.tv_firstName);
        tv_lastName = (TextView) findViewById(R.id.tv_lastName);
        tv_email = (TextView) findViewById(R.id.tv_email);
        label_delivery = (TextView) findViewById(R.id.label_delivery);
        label_eazkit = (TextView) findViewById(R.id.label_eazkit);
        tv_country = (TextView) findViewById(R.id.tv_country);

        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_postalCode = (TextView) findViewById(R.id.tv_postalCode);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_optionalLabel = (TextView) findViewById(R.id.tv_optionalLabel);
        cp_continueLabel = (TextView) findViewById(R.id.cp_continueLabel);
        cp1_EAZKIT = (TextView) findViewById(R.id.cp1_EAZKIT);
//        stateEt = (EditText) findViewById(R.id.stateEt);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_phone = (TextView) findViewById(R.id.tv_phone);

        stepsId.setTypeface(firaSansMedium);
        personal_Info.setTypeface(firaSansBold);

        tv_firstName.setTypeface(firaSansMedium);
        tv_lastName.setTypeface(firaSansMedium);
        tv_email.setTypeface(firaSansMedium);
        tv_country.setTypeface(firaSansMedium);

        tv_city.setTypeface(firaSansMedium);
        tv_address.setTypeface(firaSansMedium);
        tv_postalCode.setTypeface(firaSansMedium);
        tv_age.setTypeface(firaSansMedium);
        tv_phone.setTypeface(firaSansMedium);

        tv_optionalLabel.setTypeface(firaSansRegular);
        cp_continueLabel.setTypeface(firaSansRegular);
        label_delivery.setTypeface(firaSansBold);
        label_eazkit.setTypeface(firaSansBold);
        cp1_EAZKIT.setTypeface(absoluteProReducedBlack);

        profileImg = (CircleImageView) findViewById(R.id.profileImg);
        cp1_changeProfilePic = (ImageButton) findViewById(R.id.cp1_changeProfilePic);

        cp1_changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 20);
            }
        });


        city_spinner = (EditText) findViewById(R.id.city_spinner);
//        state_spinner = (Spinner) findViewById(R.id.state_spinner);
        // stateAdapter=ArrayAdapter.createFromResource(this,R.array.states_arrays,R.layout.custom_spinner_layout);


//        cityAdapter = ArrayAdapter.createFromResource(this, listOfCountries, R.layout.custom_spinner_layout);
//        countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.custom_spinner_layout);


        // city_spinner.setAdapter((SpinnerAdapter)cityAdapter);
//        state_spinner.setAdapter((SpinnerAdapter) stateAdapter);
        //country_spinner=getResources().getStringArray(R.array.country_arrays);
        cp_firstName = (EditText) findViewById(R.id.cp_firstName);
        cp_lastname = (EditText) findViewById(R.id.cp_lastName);
        cp_email = (EditText) findViewById(R.id.cp_email);
        cp_address = (EditText) findViewById(R.id.cp_address);
        cp_postal_code = (EditText) findViewById(R.id.cp_postal_code);
        cp_age = (EditText) findViewById(R.id.cp_age);
        cp2_username = (TextView) findViewById(R.id.cp2_username);

        cp_email.setEnabled(false);
        cp_email.setClickable(false);
        cp_firstName.setTypeface(firaSansLight);
        cp_lastname.setTypeface(firaSansLight);
        cp_email.setTypeface(firaSansLight);
        cp_address.setTypeface(firaSansLight);
        cp_postal_code.setTypeface(firaSansLight);
        cp_age.setTypeface(firaSansLight);
        cp2_username.setTypeface(firaSansLight);

        et_phone.setTypeface(firaSansLight);
        city_spinner.setTypeface(firaSansLight);

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


        //adding animation to tooth Image
        cp_smilingTeethAnimation = (ImageView) findViewById(R.id.cp_smilingTeethAnimation);
        cp_smilingTeethAnimation.setBackgroundResource(R.drawable.animation_fulleyes);
        AnimationDrawable animation = (AnimationDrawable) cp_smilingTeethAnimation.getBackground();
        animation.start();

        cp_email.setText(SharedPreManager.getInstance(CreateProfile1.this).getUserEmail());

        String userNamePref = SharedPreManager.getInstance(CreateProfile1.this).getUsername();
        cp2_username.setText(userNamePref + "");

        //if(SharedPreManager.getInstance(CreateProfile1.this).getProfileImage()!=null){
        String base = Utils.BASE_URL;
        //  Toast.makeText(CreateProfile1.this,base,Toast.LENGTH_LONG).show();
        //getting image url from shared preferences
        String pi = SharedPreManager.getInstance(CreateProfile1.this).getProfileImage();
        // Toast.makeText(CreateProfile1.this,pi,Toast.LENGTH_LONG).show();
        Picasso.with(CreateProfile1.this).load(Utils.BASE_URL_IMAGE + pi).into(profileImg);

        //   Toast.makeText(CreateProfile1.this,url,Toast.LENGTH_LONG).show();
        //  }
        //  else{
        //  profileImg.setImageResource(R.drawable.image);
        //  }


        RelativeLayout continue_button = (RelativeLayout) findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (GlobalClass.isOnline(getApplicationContext()) == true) {

                    if (countryValue.equalsIgnoreCase("")) {

                        JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
                        jsonAsyncTask.execute();
                        Toast.makeText(CreateProfile1.this, "Please Select Your Country", Toast.LENGTH_SHORT).show();
                    } else {
                        firstname = cp_firstName.getText().toString();
                        lastname = cp_lastname.getText().toString();
                        email = cp_email.getText().toString();
                        address = cp_address.getText().toString();
                        postal_code = cp_postal_code.getText().toString();
                        age = cp_age.getText().toString();
                        country = countryValue;
                        city = city_spinner.getText().toString();
//                        StateValue = stateEt.getText().toString();

                        phoneNumber = et_phone.getText().toString().trim();
                        AttemptCreateProfile createProfile = new AttemptCreateProfile();
                        if (!firstname.equals("") && !lastname.equals("") && !email.equals("") && !country.equals("") && !city.equals("")

                                && !phoneNumber.equalsIgnoreCase("")

                                ) {
                            try {
                                Bitmap image = ((BitmapDrawable) profileImg.getDrawable()).getBitmap();
                                encoded_Img = imageToString(image);
                            } catch (Exception e) {
                                e.getMessage();
                            }


                            createProfile.execute(firstname, email, countryValue,  city, address, postal_code, age, encoded_Img, lastname, phoneNumber);
                        } else {
                            Toast.makeText(CreateProfile1.this, "All fields are mandatory!", Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                    Toast.makeText(CreateProfile1.this, "Please Connect to network!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        if (GlobalClass.isOnline(getApplicationContext()) == true) {

            JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
            jsonAsyncTask.execute();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selected_image = data.getData();
        profileImg.setImageURI(selected_image);
        //Converting image to base 64
        Bitmap image = ((BitmapDrawable) profileImg.getDrawable()).getBitmap();
        encoded_Img = imageToString(image);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public class AttemptCreateProfile extends AsyncTask<String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(CreateProfile1.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Creating Profile");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String nam, mail, count,  cit, addr, postC, age, pi, lnam, phn;
            nam = strings[0];
            mail = strings[1];
            count = strings[2];
//            stat = strings[3];
            cit = strings[3];
            addr = strings[4];
            postC = strings[5];
            age = strings[6];
            pi = strings[7];
            lnam = strings[8];
            phn = strings[9];
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("firstName", nam));
            params.add(new BasicNameValuePair("email", mail));
            params.add(new BasicNameValuePair("country", count));
//            params.add(new BasicNameValuePair("state", stat));
            params.add(new BasicNameValuePair("city", cit));
            params.add(new BasicNameValuePair("address", addr));
            params.add(new BasicNameValuePair("postCode", postC));
            params.add(new BasicNameValuePair("age", age+""));
            params.add(new BasicNameValuePair("profileImage", pi));
            params.add(new BasicNameValuePair("lastName", lnam));
            params.add(new BasicNameValuePair("countryKey", countryValue));
//            params.add(new BasicNameValuePair("stateKey", StateValue));
            params.add(new BasicNameValuePair("phoneNumber", phn));


            String uId = SharedPreManager.getInstance(CreateProfile1.this).getCurrentUserId();
            JSONObject jsonObject = jsonParser.makeHttpRequest(Utils.BASE_URL + Utils.CREATE_PROFILE1_URL + uId, "POST", params);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();

            if (jsonObject != null) {
                try {
//                    Toast.makeText(getApplicationContext(), jsonObject.getString("statusCode"), Toast.LENGTH_LONG).show();
                    String check = jsonObject.getString("statusCode");

                    String city;


                    city = city_spinner.getText().toString();
                    if (check.equals("1")) {
                        JSONObject profile = jsonObject.getJSONObject("Result");
                        Log.d("Create1Res", profile + "");


                        String profilePhoto = profile.getString("profileImage");
                        UserProfileSharedPreferences.getInstance(getApplicationContext()).userProfile(
                                firstname, lastname, email, countryValue, "", city,
                                address, postal_code, age, "0"
                        );

                        SharedPreferences sharedpreferences = getSharedPreferences("currentUserProfile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("ageSet", "1");
                        editor.putString("PREF_USER", "0");
                        editor.commit();


                        SharedPreManager.getInstance(CreateProfile1.this).setProfileImage(profilePhoto);
                        Intent intent = new Intent(CreateProfile1.this, CreateProfile2.class);
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


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CreateProfile1.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet("http://eazkit.jobesk.com/countries.json");
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
        }
    }

    private void counrySpinner() {
        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        countryAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_layout, listOfCountries);

        country_spinner.setAdapter(countryAdapter);

        int selectionvalue = 0;
        for (int i = 0; i < listOfCountries.size(); i++) {

            if (listOfCountries.get(i).equalsIgnoreCase("united kingdom")) {

                selectionvalue = i;
            }


        }


        country_spinner.setSelection(selectionvalue);
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
