package com.stslex.home_feature.service

import com.stslex.feature_home.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VpnState(val stateRes: Int) {

    @SerialName("disconnected")
    DISCONNECTED(R.string.disconnected),

    @SerialName("connecting")
    CONNECTING(R.string.connecting),
}