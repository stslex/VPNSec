package com.stslex.home_feature.ui

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stslex.home_feature.service.VpnSecClientPrefs
import com.stslex.home_feature.service.VpnSecService


private val Context.vpnServiceIntent: Intent
    get() = Intent(this, VpnSecService::class.java)

@Composable
fun FeatureHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeatureHomeViewModel,
    context: Context = LocalContext.current
) {
    val prefs = context.getSharedPreferences(VpnSecClientPrefs.NAME, MODE_PRIVATE)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        context.onLauncherResult(result.resultCode)
    }
    val launchIntent = VpnService.prepare(context)
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        ) {
            Button(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    // TODO add fields for input
                    prefs.edit()
                        .putString(VpnSecClientPrefs.SERVER_ADDRESS, "serverAddress")
                        .putInt(VpnSecClientPrefs.SERVER_PORT, 0)
                        .putString(VpnSecClientPrefs.SHARED_SECRET, "sharedSecret")
                        .putString(VpnSecClientPrefs.PROXY_HOSTNAME, "proxyHost")
                        .putInt(VpnSecClientPrefs.PROXY_PORT, 0)
                        .putBoolean(VpnSecClientPrefs.ALLOW, true)
                        .putStringSet(VpnSecClientPrefs.PACKAGES, emptySet())
                        .apply()
                    if (launchIntent != null) {
                        launcher.launch(launchIntent)
                    } else {
                        context.onLauncherResult(Activity.RESULT_OK)
                    }
                }
            ) {
                Text(text = "launch VPN")
            }
            Button(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    val intent = context.vpnServiceIntent.setAction(VpnSecService.ACTION_DISCONNECT)
                    context.startService(intent)
                }
            ) {
                Text(text = "disconnect VPN")
            }
        }
    }
}

private fun Context.onLauncherResult(resultCode: Int) {
    if (resultCode == Activity.RESULT_OK) {
        val intent = vpnServiceIntent.setAction(VpnSecService.ACTION_CONNECT)
        startService(intent)
    }
}
