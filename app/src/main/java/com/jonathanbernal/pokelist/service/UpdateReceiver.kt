package com.jonathanbernal.pokelist.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log

class UpdateReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.extras!=null){
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = connectivityManager.activeNetworkInfo
            if (ni != null && ni.isConnectedOrConnecting){
                Log.e("NET", "conectado")
            }else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false)){
                Log.e("NET", "no conectado")
            }
        }
    }
}