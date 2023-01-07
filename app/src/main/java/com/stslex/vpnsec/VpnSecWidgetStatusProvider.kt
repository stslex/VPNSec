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
        context.updateAppWidget(appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context == null ||
            intent == null ||
            intent.extras?.getInt(REQUEST_CODE) != REQUEST_CODE_FROM_COLLECTION_WIDGET
        ) {
            return
        }
        context.apply {
            updateAppWidget()
            startActivity(settingsIntent)
        }
    }

    private fun Context.updateAppWidget() {
        val remoteViews = buildUpdateViews()
        val componentName = ComponentName(this, VpnSecWidgetStatusProvider::class.java)
        AppWidgetManager.getInstance(this).updateAppWidget(componentName, remoteViews)
    }

    private fun Context.updateAppWidget(
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = buildUpdateViews()
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }

    private fun Context.buildUpdateViews(): RemoteViews =
        RemoteViews(packageName, R.layout.appwidget_provider_layout_status).apply {
            setTextViewText(R.id.vpnStatusTextView, statusText)
            setOnClickPendingIntent(R.id.vpnLayout, onCheckedChangePendingIntent)
        }

    private val Context.statusText: String
        get() = if (isVpnConnect) {
            R.string.app_widget_status_title_connect
        } else {
            R.string.app_widget_status_title_disconnected
        }.let(::getString)

    private val Context.onCheckedChangePendingIntent: PendingIntent
        get() = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE_FROM_COLLECTION_WIDGET,
            widgetStatusIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

    private val Context.widgetStatusIntent: Intent
        get() = Intent(this, VpnSecWidgetStatusProvider::class.java).apply {
            putExtra(EXTRA_VIEW_ID, R.id.vpnLayout)
            putExtra(REQUEST_CODE, REQUEST_CODE_FROM_COLLECTION_WIDGET)
        }

    companion object {
        const val REQUEST_CODE_FROM_COLLECTION_WIDGET = 2
        const val EXTRA_VIEW_ID = "extra_view_id"
        const val REQUEST_CODE = "request_code"
    }
}