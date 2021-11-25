package com.example.jbalpha.eazkitv8.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreManager {

    private static SharedPreManager mInstance;
    private static Context context;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String USER_ID_KEY="user_id";
    private static final String USER_TOKEN_KEY="token";
    private static final String USER_EMAIL_KEY="email";
    private static final String USER_USERNAME_KEY="username";
    private static final String USER_PROFILEIMAGE_KEY="profileImage";
    private static final String FIRST_LAUNCH="first_launch";
    private static final String PERFERENCE_VALUE="PREF_USER";


    private SharedPreManager(Context context) {
        this.context=context;
    }

    public static synchronized SharedPreManager getInstance(Context context){
        if (mInstance==null){
            mInstance=new SharedPreManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String id,String token,String email,String username,String profileImage,String PreferenceValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_ID_KEY,id);
        editor.putString(USER_TOKEN_KEY,token);
        editor.putString(USER_EMAIL_KEY,email);
        editor.putString(USER_USERNAME_KEY,username);
        editor.putString(USER_PROFILEIMAGE_KEY,profileImage);
        editor.putBoolean(FIRST_LAUNCH,true);
        editor.putString(PERFERENCE_VALUE,PreferenceValue);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(USER_ID_KEY,null)!=null){
            return true;
        }
        return false;
    }

    public boolean isLoggedOut(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getCurrentUserId(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID_KEY,null);
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL_KEY,null);
    }

    public String getUsername(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_USERNAME_KEY,null);
    }

    public String getProfileImage(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PROFILEIMAGE_KEY,null);
    }

    public boolean setProfileImage(String profileImage){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_PROFILEIMAGE_KEY,profileImage);
        editor.apply();
        return true;
    }

    public boolean setUserName(String username){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_USERNAME_KEY,username);
        editor.apply();
        return true;
    }


}
