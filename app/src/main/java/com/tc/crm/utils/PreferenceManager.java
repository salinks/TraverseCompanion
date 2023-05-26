package com.tc.crm.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.tc.crm.TCApp;

import java.util.List;


public class PreferenceManager {


    public static final String FIRST_TIME_FLAG = "FirstTimeStartFlag";


    public static String APP_PREFERENCE_NAME = "mclfleet";
    private static final int PRIVATE_MODE = 0;
    public static String USER_ID = "USER_ID";

    public static String FCM_TOKEN = "FCM_TOKEN";
    public static String USER_NAME = "USER_NAME";
    public static String DesName = "DesName";




    public String getUserId() {
        return mPreferences.getString(USER_ID, "");
    }

    public void setUserId(String value) {
        save(USER_ID, value);
    }



    public String getFcmToken() {
        return mPreferences.getString(FCM_TOKEN, "");
    }

    public void setFcmToken(String value) {
        save(FCM_TOKEN, value);
    }




    public String getUserName() {
        return mPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String value) {
        save(USER_NAME, value);
    }




    public String getDesName() {
        return mPreferences.getString(DesName, "");
    }

    public void setDesName(String value) {
        save(DesName, value);
    }







    //=======================================================================================================//


    private static PreferenceManager sessionManager = null;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mTranslationPreferences;
    private SharedPreferences.Editor mTranslationEditor;

    public PreferenceManager(Context context) {
        mPreferences = context.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mEditor.apply();
    }

    public static PreferenceManager getInstance() {
        if (sessionManager == null)
            sessionManager = new PreferenceManager(TCApp.context);
        return sessionManager;
    }


    public SharedPreferences getSharedPreferences() {
        return mPreferences;
    }

    public SharedPreferences getTranslationSharedPreferences() {
        return mTranslationPreferences;
    }

    public void clearTransPrefsData() {
        mTranslationEditor.clear();
        mTranslationEditor.commit();
    }

    public void clearPrefsData() {
        mEditor.clear();
        mEditor.commit();
    }

    public String read(String valueKey, String valueDefault, boolean isTrans) {
        return mTranslationPreferences.getString(valueKey, valueDefault);
    }

    public void save(String valueKey, String value, boolean isTrans) {
        mTranslationEditor.putString(valueKey, value);
        mTranslationEditor.commit();
    }

    public String read(String valueKey, String valueDefault) {
        return mPreferences.getString(valueKey, valueDefault);
    }

    public void save(String valueKey, String value) {
        mEditor.putString(valueKey, value);
        mEditor.commit();
    }

    public int read(String valueKey, int valueDefault) {
        return mPreferences.getInt(valueKey, valueDefault);
    }

    public void save(String valueKey, int value) {
        mEditor.putInt(valueKey, value);
        mEditor.commit();
    }

    public boolean read(String valueKey, boolean valueDefault) {
        return mPreferences.getBoolean(valueKey, valueDefault);
    }

    public void save(String valueKey, boolean value) {
        mEditor.putBoolean(valueKey, value);
        mEditor.commit();
    }

    public long read(String valueKey, long valueDefault) {
        return mPreferences.getLong(valueKey, valueDefault);
    }

    public void save(String valueKey, long value) {
        mEditor.putLong(valueKey, value);
        mEditor.commit();
    }

    public void saveID(String valueKey, long value) {
        mEditor.putLong(valueKey, value);
        mEditor.commit();
    }

    public long readID(String valueKey, long valueDefault) {
        return mPreferences.getLong(valueKey, valueDefault);
    }

    public float read(String valueKey, float valueDefault) {
        return mPreferences.getFloat(valueKey, valueDefault);
    }

    public void save(String valueKey, float value) {
        mEditor.putFloat(valueKey, value);
        mEditor.commit();
    }

    public void clear(Context context, boolean keepSearchData) {


    }


    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();


    }


}
