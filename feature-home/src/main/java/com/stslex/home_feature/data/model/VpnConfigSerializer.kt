package com.stslex.home_feature.data.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.stslex.home_feature.service.VpnConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object VpnConfigSerializer : Serializer<VpnConfig> {

    override val defaultValue: VpnConfig = VpnConfig()

    override suspend fun readFrom(input: InputStream): VpnConfig = try {
        Json.decodeFromString(
            deserializer = VpnConfig.serializer(),
            string = input.readBytes().decodeToString()
        )
    } catch (serialization: SerializationException) {
        throw CorruptionException("Unable to read VpnConfig", serialization)
    }

    override suspend fun writeTo(t: VpnConfig, output: OutputStream) {
        withContext(Dispatchers.IO) {
            val encodedString = Json.encodeToString(VpnConfig.serializer(), t)
            output.write(encodedString.encodeToByteArray())
        }
    }
}