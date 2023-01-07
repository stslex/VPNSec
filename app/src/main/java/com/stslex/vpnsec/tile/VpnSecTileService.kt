package com.stslex.vpnsec.tile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.stslex.vpnsec.base.BaseExt.isVpnConnect
import com.stslex.vpnsec.base.BaseExt.settingsIntent

class VpnSecTileService : TileService() {

    override fun onClick() {
        super.onClick()
        updateState()
        startActivityAndCollapse(settingsIntent)
    }

    override fun onStartListening() {
        super.onStartListening()
        updateState()
    }

    private fun updateState() {
        qsTile.state = if (isVpnConnect) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}