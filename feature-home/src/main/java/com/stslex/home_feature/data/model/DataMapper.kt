package com.stslex.home_feature.data

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.stslex.home_feature.service.VpnConfig

fun VpnConfig.mapToData(): Pair<Preferences.Key<String>, VpnConfig> {
    return stringPreferencesKey("vpn_config") to this
}