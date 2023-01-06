package com.stslex.vpnsec

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.stslex.vpnsec.BaseExt.isVpnConnect
import com.stslex.vpnsec.BaseExt.settingsIntent


class VpnSecWidgetStatusProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = context.buildUpdate()
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context == null || intent == null) return
        if (intent.extras?.getInt(REQUEST_CODE) == REQUEST_CODE_FROM_COLLECTION_WIDGET) {
            val remoteViews = context.buildUpdate()
            val componentName = ComponentName(context, VpnSecWidgetStatusProvider::class.java)
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews)
            context.startActivity(settingsIntent)
        }
    }

    private fun Context.buildUpdate(): RemoteViews =
        RemoteViews(packageName, R.layout.appwidget_provider_layout_status).apply {
            val statusText = if (isVpnConnect) "connected" else "disconnected"
            setTextViewText(R.id.vpnStatusTextView, statusText)
            setOnClickPendingIntent(R.id.vpnLayout, onCheckedChangePendingIntent)
        }

    private val Context.onCheckedChangePendingIntent: PendingIntent
        get() = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE_FROM_COLLECTION_WIDGET,
            Intent(this, VpnSecWidgetStatusProvider::class.java).apply {
                putExtra(EXTRA_VIEW_ID, R.id.vpnLayout)
                putExtra(REQUEST_CODE, REQUEST_CODE_FROM_COLLECTION_WIDGET)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

    companion object {
        const val REQUEST_CODE_FROM_COLLECTION_WIDGET = 2
        const val EXTRA_VIEW_ID = "extra_view_id"
        const val REQUEST_CODE = "request_code"
    }
}