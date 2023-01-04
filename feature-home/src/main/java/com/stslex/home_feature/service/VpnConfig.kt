package com.stslex.home_feature.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VpnConfig(
    @SerialName("name")
    val name: String = "",
    @SerialName("serverAddress")
    val serverAddress: String = "",
    @SerialName("ipSecIdentifier")
    val ipSecIdentifier: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("password")
    val password: String = "",
    @SerialName("state")
    val state: VpnState = VpnState.DISCONNECTED
)