package com.stslex.home_feature.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.stslex.feature_home.R
import com.stslex.home_feature.service.VpnConfig
import com.stslex.home_feature.service.VpnSecService

@Composable
fun FeatureHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeatureHomeViewModel,
    context: Context = LocalContext.current
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        context.onLauncherResult(result.resultCode)
    }
    val launchIntent = VpnService.prepare(context)
    val vpnConfig by remember {
        viewModel.vpnConfig
    }.collectAsState(initial = VpnConfig())

    var configName by remember { mutableStateOf(vpnConfig.name) }
    var configServerAddress by remember { mutableStateOf(vpnConfig.serverAddress) }
    var configIpSecIdentifier by remember { mutableStateOf(vpnConfig.ipSecIdentifier) }
    var configUsername by remember { mutableStateOf(vpnConfig.username) }
    var configPassword by remember { mutableStateOf(vpnConfig.password) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item("name") {
            VpnTextField(
                text = configName,
                label = "name"
            ) { configName = it }
        }
        item("ServerAddress") {
            VpnTextField(
                text = configServerAddress,
                label = "ServerAddress"
            ) { configServerAddress = it }
        }
        item("IpSecIdentifier") {
            VpnTextField(
                text = configIpSecIdentifier,
                label = "IpSecIdentifier"
            ) { configIpSecIdentifier = it }
        }
        item("username") {
            VpnTextField(
                text = configUsername,
                label = "username"
            ) { configUsername = it }

        }
        item("password") {
            VpnPasswordTextField(
                text = configPassword,
            ) { configPassword = it }
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    ),
                onClick = {
                    viewModel.saveVpnConfig(
                        vpnConfig.copy(
                            name = configName,
                            username = configUsername,
                            password = configPassword,
                            serverAddress = configServerAddress,
                            ipSecIdentifier = configIpSecIdentifier
                        )
                    )
                    if (launchIntent != null) {
                        launcher.launch(launchIntent)
                    } else {
                        context.onLauncherResult(Activity.RESULT_OK)
                    }
                }
            ) {
                Text(text = "launch VPN")
            }
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    ),
                onClick = {
                    val intent = context.vpnServiceIntent.setAction(VpnSecService.ACTION_DISCONNECT)
                    context.startService(intent)
                }
            ) {
                Text(
                    text = "disconnect VPN"
                )
            }
        }
    }
}

@Composable
private fun VpnPasswordTextField(
    text: String,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val resource = if (isPasswordVisible) {
        R.drawable.baseline_visibility_24
    } else {
        R.drawable.baseline_visibility_off_24
    }
    val visualTransformation = if (isPasswordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    VpnTextField(
        text = text,
        label = "password",
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = {
                    isPasswordVisible = isPasswordVisible.not()
                }
            ) {
                Icon(
                    painter = painterResource(id = resource),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        onChange = onChange,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VpnTextField(
    text: String,
    label: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    onChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        label = { Text(text = label) },
        singleLine = true,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

private val Context.vpnServiceIntent: Intent
    get() = Intent(this, VpnSecService::class.java)

private fun Context.onLauncherResult(resultCode: Int) {
    if (resultCode == Activity.RESULT_OK) {
        val intent = vpnServiceIntent.setAction(VpnSecService.ACTION_CONNECT)
        startService(intent)
    }
}
