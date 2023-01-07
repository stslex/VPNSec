package com.stslex.vpnsec.base

import android.app.PendingIntent
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

    val Context.pendingSettingsIntent: PendingIntent
        get() = PendingIntent.getActivity(
            this,
            0,
            settingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    private val Context.connectiveManager: ConnectivityManager
        get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}