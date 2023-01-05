package com.stslex.vpnsec

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object BaseExt {
    private const val PATH_SETTINGS_VPN = "android.net.vpn.SETTINGS"

    val settingsIntent: Intent
        get() = Intent(PATH_SETTINGS_VPN).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    val Context.isVpnConnect: Boolean
        get() = with(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            val network = activeNetwork
            val caps = getNetworkCapabilities(network)
            caps?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false
        }
}