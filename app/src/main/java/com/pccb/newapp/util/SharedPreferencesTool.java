package com.pccb.newapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesTool {
    private static final String SP_NAME = "config";
    private static SharedPreferences spPreferences;

    public static void saveBoolean(Context context, String key, boolean value) {
        if (spPreferences == null) {
            spPreferences = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
        }
        spPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (spPreferences == null) {
            spPreferences = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
        }
        return spPreferences.getBoolean(key, defValue);
    }

    public static void saveString(Context context, String key, String value) {
        if (spPreferences == null) {
            spPreferences = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
        }
        spPreferences.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        if (spPreferences == null) {
            spPreferences = context.getSharedPreferences(SP_NAME,
                    Context.MODE_PRIVATE);
        }

        return spPreferences.getString(key, defValue);
    }
}
