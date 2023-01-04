package com.stslex.vpnsec

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class VpnSecTileService : TileService() {

    override fun onClick() {
        super.onClick()
        updateState()
        val intent = Intent("android.net.vpn.SETTINGS").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
    }

    override fun onStartListening() {
        super.onStartListening()
        updateState()
    }

    private fun updateState() {
        qsTile.state = if (isVpnConnect) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    private val isVpnConnect: Boolean
        get() = with(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            val network = activeNetwork
            val caps = getNetworkCapabilities(network)
            caps?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false
        }
}