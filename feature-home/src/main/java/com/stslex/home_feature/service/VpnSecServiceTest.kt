package com.stslex.home_feature.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.VpnService
import android.os.Handler
import android.os.Message
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import com.stslex.feature_home.R
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class VpnSecServiceTest : VpnService(), Handler.Callback {

    private var configIntent: PendingIntent? = null
    private val mHandler: Handler? = null

    private val connectingThread = AtomicReference<Thread>()
    private val mConnection = AtomicReference<Connection>()
    private val mNextConnectionId = AtomicInteger(1)
    private val mConfigureIntent: PendingIntent? = null

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, Class.forName(MAIN_ACTIVITY_PATH))
        configIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent != null && ACTION_DISCONNECT == intent.action) {
            disconnect()
            START_NOT_STICKY
        } else {
            connect()
            START_STICKY
        }
    }

    override fun onDestroy() {
        disconnect()
    }

    override fun handleMessage(message: Message): Boolean {
        Toast.makeText(this, message.what, Toast.LENGTH_SHORT).show()
        if (message.what != R.string.disconnected) {
            updateForegroundNotification(message.what)
        }
        return true
    }

    private fun connect() {
        // Become a foreground service. Background services can be VPN services too, but they can
        // be killed by background check before getting a chance to receive onRevoke().
        updateForegroundNotification(R.string.connecting)
        mHandler?.sendEmptyMessage(R.string.connecting)
        // Extract information from the shared preferences.
        val prefs = getSharedPreferences(VpnSecClientPrefs.NAME, MODE_PRIVATE)
        val server = prefs.getString(VpnSecClientPrefs.SERVER_ADDRESS, "")
        val secret = prefs.getString(VpnSecClientPrefs.SHARED_SECRET, "").orEmpty().toByteArray()
        val allow = prefs.getBoolean(VpnSecClientPrefs.ALLOW, true)
        val packages = prefs.getStringSet(VpnSecClientPrefs.PACKAGES, emptySet())
        val port = prefs.getInt(VpnSecClientPrefs.SERVER_PORT, 0)
        val proxyHost = prefs.getString(VpnSecClientPrefs.PROXY_HOSTNAME, "")
        val proxyPort = prefs.getInt(VpnSecClientPrefs.PROXY_PORT, 0)
        startConnection(
            VpnSecConnectionTest(
                this, mNextConnectionId.getAndIncrement(), server, port, secret,
                proxyHost, proxyPort, allow, packages
            )
        )
    }

    private fun startConnection(connection: VpnSecConnectionTest) {
        // Replace any existing connecting thread with the  new one.
        val thread = Thread(connection, "VpnSecThread")
        setConnectingThread(thread)
        // Handler to mark as connected once onEstablish is called.
        connection.setConfigureIntent(mConfigureIntent)
        connection.setOnEstablishListener { tunInterface ->
            mHandler?.sendEmptyMessage(R.string.connected)
            connectingThread.compareAndSet(thread, null)
            setConnection(Connection(thread, tunInterface))
        }
        thread.start()
    }

    private fun setConnectingThread(thread: Thread?) {
        connectingThread.getAndSet(thread)?.interrupt()
    }

    private fun setConnection(connection: Connection?) {
        try {
            mConnection.getAndSet(connection)?.apply {
                first?.interrupt()
                second?.close()
            }
        } catch (e: IOException) {
            Log.e(javaClass.simpleName, "Closing VPN interface", e)
        }
    }

    private fun disconnect() {
        mHandler?.sendEmptyMessage(R.string.disconnected)
        setConnectingThread(null)
        setConnection(null)
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun updateForegroundNotification(message: Int) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentText(getString(message))
            .setContentIntent(configIntent)
            .build()
        startForeground(NOTIFICATION_CHANNEL_FOREGROUND_ID, notification)
    }

    companion object {
        const val ACTION_CONNECT = "ACTION_CONNECT"
        const val ACTION_DISCONNECT = "ACTION_DISCONNECT"

        private const val NOTIFICATION_CHANNEL_ID = "VpnSec"
        private const val NOTIFICATION_CHANNEL_FOREGROUND_ID = 1
        private const val MAIN_ACTIVITY_PATH = "com.stslex.vpnsec.ui.MainActivity"
    }
}

private typealias Connection = Pair<Thread?, ParcelFileDescriptor?>