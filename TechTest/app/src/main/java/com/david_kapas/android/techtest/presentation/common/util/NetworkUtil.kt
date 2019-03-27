package com.david_kapas.android.techtest.presentation.common.util

import android.content.Context
import android.net.ConnectivityManager
import com.david_kapas.android.techtest.presentation.common.app.AndroidApplication

/**
 * Util class for network related methods.
 * Created by David Kapas on 2018.03.17.
 */

object NetworkUtil {

    fun checkNetworkConnection(): Boolean {
        val cm = AndroidApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}
