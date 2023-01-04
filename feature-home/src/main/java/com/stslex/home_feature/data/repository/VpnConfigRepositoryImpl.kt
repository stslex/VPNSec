package com.stslex.home_feature.data.repository

import androidx.datastore.core.DataStore
import com.stslex.home_feature.service.VpnConfig
import kotlinx.coroutines.flow.Flow

class VpnConfigRepositoryImpl(
    private val dataStore: DataStore<VpnConfig>
) : VpnConfigRepository {

    override suspend fun saveConfig(config: VpnConfig) {
        dataStore.updateData { config }
    }

    override val vpnConfig: Flow<VpnConfig>
        get() = dataStore.data
}