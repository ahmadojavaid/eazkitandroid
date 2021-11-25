package com.example.jbalpha.eazkitv8.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class UserProfileSharedPreferences {

    private static UserProfileSharedPreferences userProfileSharedPreferences;
    private static Context context;
    private static final String SHARED_PREFERENCE_NAME="currentUserProfile";
    private static final String FIRST_NAME_KEY="firstName";
    private static final String LAST_NAME_KEY="lastName";
    private static final String EMAIL_KEY="email";
    private static final String COUNTRY_KEY="country";
    private static final String STATE_KEY= "state";
    private static final String CITY_KEY= "city";
    private static final String ADDRESS_KEY= "address";
    private static final String POSTAL_KEY= "postal";
    private static final String AGE_KEY= "key";
    private static final String PERFERENCE_VALUE="PREF_USER";
    private UserProfileSharedPreferences(Context context){
        this.context=context;
    }

    public static synchronized UserProfileSharedPreferences getInstance(Context context){
        if(userProfileSharedPreferences==null){
            userProfileSharedPreferences=new UserProfileSharedPreferences(context);
        }
        return userProfileSharedPreferences;
    }

    public boolean userProfile(String firstName,String lastName,String email,String country,String state,String city,
    String address,String postal,String age,String prefStatus){
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(FIRST_NAME_KEY,firstName);
        editor.putString(LAST_NAME_KEY,lastName);
        editor.putString(EMAIL_KEY,email);
        editor.putString(COUNTRY_KEY,country);
        editor.putString(STATE_KEY,state);
        editor.putString(CITY_KEY,city);
        editor.putString(ADDRESS_KEY,address);
        editor.putString(POSTAL_KEY,postal);
        editor.putString(AGE_KEY,age);

        editor.putString(PERFERENCE_VALUE,prefStatus);

        editor.apply();
        return true;
    }

    public String getCurrentUserFirstName(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIRST_NAME_KEY,null);
    }

    public String getCurrentUserLastName(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_NAME_KEY,null);
    }

    public String getCurrentUserEmail(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_KEY,null);
    }

    public String getCurrentUserCountry(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(COUNTRY_KEY,null);
    }

    public String getCurrentUserState(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(STATE_KEY,null);
    }

    public String getCurrentUserCity(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(CITY_KEY,null);
    }

    public String getCurrentUserAddress(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(ADDRESS_KEY,null);
    }

    public String getCurrentUserPostalCode(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(POSTAL_KEY,null);
    }

    public String getCurrentUserAge(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(AGE_KEY,null);
    }

    public boolean isLoggedOut(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUserAge(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(AGE_KEY,Context.MODE_PRIVATE);
        return sharedPreferences.getString(AGE_KEY,null);
    }
    public String getUserPref(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PERFERENCE_VALUE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PERFERENCE_VALUE,null);
    }
}
