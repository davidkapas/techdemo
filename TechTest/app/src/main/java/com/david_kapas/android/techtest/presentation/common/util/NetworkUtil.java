package com.david_kapas.android.techtest.presentation.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.david_kapas.android.techtest.presentation.common.app.AndroidApplication;

/**
 * Util class for network related methods.
 * Created by David Kapas on 2018.03.17.
 */

public class NetworkUtil {

    private NetworkUtil() {
    }

    public static boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) AndroidApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
