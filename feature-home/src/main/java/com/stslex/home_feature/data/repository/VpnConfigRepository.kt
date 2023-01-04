package com.stslex.home_feature.data.repository

import com.stslex.home_feature.service.VpnConfig
import kotlinx.coroutines.flow.Flow

interface VpnConfigRepository {

    suspend fun saveConfig(config: VpnConfig)

    val vpnConfig: Flow<VpnConfig>
}