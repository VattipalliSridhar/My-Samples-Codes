package com.apps.myapplication.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class Utils {

    public static final String BASE_URL = "https://egovindia.in/steelbank/api/";
    public static String FireBase_URL = "https://fcm.googleapis.com/";

    public Utils(Context context) {


    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
