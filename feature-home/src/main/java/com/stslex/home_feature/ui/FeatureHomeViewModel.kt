package com.stslex.home_feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.home_feature.data.repository.VpnConfigRepository
import com.stslex.home_feature.service.VpnConfig
import com.stslex.home_feature.utils.HandlerUtils.coroutineHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FeatureHomeViewModel(
    private val repository: VpnConfigRepository
) : ViewModel() {

    val vpnConfig: Flow<VpnConfig>
        get() = repository.vpnConfig

    fun saveVpnConfig(config: VpnConfig) {
        viewModelScope.launch(Dispatchers.IO + coroutineHandler) {
            repository.saveConfig(config)
        }
    }
}