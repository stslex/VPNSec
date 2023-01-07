package com.stslex.vpnsec

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_VPN

object BaseExt {

    private const val PATH_SETTINGS_VPN = "android.net.vpn.SETTINGS"

    val settingsIntent: Intent
        get() = Intent(PATH_SETTINGS_VPN).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    val Context.isVpnConnect: Boolean
        get() = connectiveManager
            .getNetworkCapabilities(connectiveManager.activeNetwork)
            ?.hasTransport(TRANSPORT_VPN)
            ?: false

    private val Context.connectiveManager: ConnectivityManager
        get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}