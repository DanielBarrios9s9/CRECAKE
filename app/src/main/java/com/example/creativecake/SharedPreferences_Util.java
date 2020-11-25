package com.example.creativecake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferences_Util {

    public SharedPreferences_Util(){
    }

    public static boolean savePhone_SP(String phone, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(SharedPreferences_Constantes.KEY_PHONE, phone);
        prefsEditor.apply();
        return true;
    }

    public static String getPhone_SP(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SharedPreferences_Constantes.KEY_PHONE, null);
    }

    public static boolean savePassword_SP(String password, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(SharedPreferences_Constantes.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword_SP(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(SharedPreferences_Constantes.KEY_PASSWORD, null);
    }

}
