package app.assessmentdubizzle.com.utils

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtils(private val context: Context) {

    fun isConnected(): Boolean {
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected
            }
        }
        return false
    }
}