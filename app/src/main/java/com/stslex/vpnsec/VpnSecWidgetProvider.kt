package com.stslex.vpnsec

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.stslex.vpnsec.BaseExt.settingsIntent


class VpnSecWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(context.packageName, R.layout.appwidget_provider_layout_small)
        remoteViews.setOnClickPendingIntent(R.id.vpnLayout, context.pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }

    private val Context.pendingIntent: PendingIntent
        get() = PendingIntent.getActivity(
            this,
            0,
            settingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}