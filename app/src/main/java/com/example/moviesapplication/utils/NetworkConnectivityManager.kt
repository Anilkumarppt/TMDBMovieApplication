package com.example.moviesapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkConnectivityManager {
    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun isNetworkConnected(context: Context):Boolean{
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            capabilities.also {
                it.let { network->
                    if(network!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                        return true
                    if(network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                        return true

                }
            }
            return false
        }

    }
}