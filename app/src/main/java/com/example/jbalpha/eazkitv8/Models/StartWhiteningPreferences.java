package com.example.jbalpha.eazkitv8.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class StartWhiteningPreferences {

    private static StartWhiteningPreferences mInstance;
    private static Context context;
    private static final String SHARED_PREF_NAME="startWhiteningSharedPrefs";
    private static final String putGelBox="PUT_GEL";
    private static final String mouthLightInsertion="MOUTH_LIGHT";
    private static final String placeTray="PLACE_TRAY";

    private StartWhiteningPreferences(Context context){
        this.context=context;
    }

    public static  synchronized StartWhiteningPreferences getInstance(Context context){
        if(mInstance==null)
            mInstance=new StartWhiteningPreferences(context);
        return mInstance;
    }

    public boolean savePrefs(boolean putGel){
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(putGelBox,putGel);
        editor.apply();
        return true;
    }

    public boolean savePrefs(boolean putGel,boolean mouthLight){
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(putGelBox,putGel);
        editor.putBoolean(mouthLightInsertion,mouthLight);
        editor.apply();
        return true;
    }

    public boolean savePrefs(boolean putGel,boolean mouthLight,boolean placeT){
        SharedPreferences preferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(putGelBox,putGel);
        editor.putBoolean(mouthLightInsertion,mouthLight);
        editor.putBoolean(placeTray,placeT);
        editor.apply();
        return true;
    }

    public  boolean getPutGel(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(putGelBox,false);
    }

    public boolean getmouthLight(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(mouthLightInsertion,false);
    }

    public boolean getPlaceTray(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(placeTray,false);
    }

    public boolean setPutGel(boolean putGel){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(putGelBox,putGel);
        editor.apply();
        return true;
    }

    public boolean setMouthLight(boolean mouthLight){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(mouthLightInsertion,mouthLight);
        editor.apply();
        return true;
    }

    public boolean setPlaceTray(boolean setTray){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(placeTray,setTray);
        editor.apply();
        return true;
    }
}
