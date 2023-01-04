package com.stslex.home_feature.data

import android.content.Context
import androidx.datastore.dataStore
import com.stslex.home_feature.data.model.VpnConfigSerializer
import com.stslex.home_feature.service.VpnConfig
import com.stslex.home_feature.utils.HandlerUtils.corruptionHandler

object DataStore {

    private const val DATA_STORE_NAME = "vpn_datastore"

    val Context.vpnDataStore by dataStore(
        fileName = DATA_STORE_NAME,
        serializer = VpnConfigSerializer,
        corruptionHandler = VpnConfig().corruptionHandler
    )
}