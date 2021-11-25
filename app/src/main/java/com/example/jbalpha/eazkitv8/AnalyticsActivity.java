package com.example.jbalpha.eazkitv8;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbalpha.eazkitv8.Adapters.AnalyticsAdapter;
import com.example.jbalpha.eazkitv8.Models.AllSessionsModel;
import com.example.jbalpha.eazkitv8.Models.SharedPreManager;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AnalyticsActivity extends AppCompatActivity {
    private String userId;
    private SharedPreferences myPrefs;
    private ImageView backimg;
    private TextView title;
    private ImageView imageView;
    private String ToothFirstImage;
    private ArrayList<AllSessionsModel> ArrayListData = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnalyticsAdapter mAdapter;
    CardView card1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        myPrefs = getSharedPreferences("mysharedpref12", MODE_PRIVATE);
        userId = myPrefs.getString("user_id", "");

        card1 = findViewById(R.id.card1);
        card1.setVisibility(View.INVISIBLE);


        backimg = findViewById(R.id.backimg);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        title = findViewById(R.id.title);
        imageView = findViewById(R.id.imageView);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AnalyticsAdapter(ArrayListData, AnalyticsActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        if (GlobalClass.isOnline(getApplicationContext()) == true) {
            JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
            jsonAsyncTask.execute();
            getAllSessions();
        } else {
            Toast.makeText(this, "Please connect to internet!", Toast.LENGTH_SHORT).show();
        }


    }


    private class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {


                String url = Utils.BASE_URL + "/preference?userId=" + userId;
                HttpGet httppost = new HttpGet(url);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);


                if (response != null) {


                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsonObject = new JSONObject(data);
                    Log.d("responseAnalytic", jsonObject + "");
                    ToothFirstImage = jsonObject.getJSONObject("Result").getString("toothfieImage");
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putString("FirstImageTooth", ToothFirstImage);
                    editor.apply();

                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {

                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {

            try {
                card1.setVisibility(View.VISIBLE);

                Picasso.with(getApplicationContext()).load(Utils.BASE_URL_IMAGE + ToothFirstImage).fit().centerCrop().into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private void getAllSessions() {
        final ProgressDialog progressDialog = new ProgressDialog(AnalyticsActivity.this);
        progressDialog.setMessage("Loading..!");
        progressDialog.show();


        String url = Utils.BASE_URL + Utils.ALL_SESSIONS_URL + userId;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("allSessionsRes", response);
                            JSONArray array = jsonObject.getJSONArray("Result");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                AllSessionsModel model = new AllSessionsModel(
                                        object.getString("id"),
                                        object.getString("userId"),
                                        object.getString("sessionImage"),
                                        object.getString("sessionRating"),
                                        object.getString("timeCarriedOut"),
                                        object.getString("actualTimeCarriedOut")
                                );
                                ArrayListData.add(model);
                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AnalyticsActivity.this, "Unable to retrieve sessions data!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


}
