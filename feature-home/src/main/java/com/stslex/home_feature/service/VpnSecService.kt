package com.stslex.home_feature.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.VpnService
import com.stslex.home_feature.data.DataStore.vpnDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class VpnSecService : VpnService() {

    private val coroutineScope = CoroutineScope(SupervisorJob())
    private var configIntent: PendingIntent? = null

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, Class.forName(MAIN_ACTIVITY_PATH))
        val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        configIntent = PendingIntent.getActivity(this, 0, intent, flag)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent != null && VpnSecServiceTest.ACTION_DISCONNECT == intent.action) {
            disconnect()
            START_NOT_STICKY
        } else {
            connect()
            START_STICKY
        }
    }

    private fun connect() {
        updateForegroundNotification(VpnState.CONNECTING)
        sendState(VpnState.CONNECTING)
    }

    private fun disconnect() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        sendState(VpnState.DISCONNECTED)
    }

    private fun updateForegroundNotification(state: VpnState) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentText(getString(state.stateRes))
            .setContentIntent(configIntent)
            .build()
        startForeground(NOTIFICATION_CHANNEL_FOREGROUND_ID, notification)
    }

    private fun sendState(state: VpnState) {
        coroutineScope.launch(Dispatchers.IO) {
            vpnDataStore.updateData { it.copy(state = state) }
        }
    }

    companion object {
        const val ACTION_CONNECT = "ACTION_CONNECT"
        const val ACTION_DISCONNECT = "ACTION_DISCONNECT"

        private const val NOTIFICATION_CHANNEL_ID = "VpnSec"
        private const val NOTIFICATION_CHANNEL_FOREGROUND_ID = 1
        private const val MAIN_ACTIVITY_PATH = "com.stslex.vpnsec.ui.MainActivity"
    }
}