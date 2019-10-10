package com.snk.wolly.wolly;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPrefs {
    public static void setInt(Context context, String key, int input) {
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, input);
        editor.apply();
    }

    public static void setString(Context context, String key, String input) {
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, input);
        editor.apply();
    }

    public static void setBoolean(Context context, String key, boolean input) {
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, input);
        editor.apply();
    }


    public static int getInt(Context context, String s) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(s, -1);
    }
    public static String getString(Context context, String s) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(s, "-1");
    }

    public static boolean getBoolean(Context context, String s) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(s, false);
    }

}
