package com.stslex.vpnsec.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import com.stslex.vpnsec.R
import com.stslex.vpnsec.base.BaseExt.pendingSettingsIntent


class VpnSecWidgetProvider : AppWidgetProvider() {

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        if (context == null) return
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.appwidget_provider_layout_circle
        ).apply {
            setOnClickPendingIntent(R.id.vpnLayout, context.pendingSettingsIntent)
        }
        val componentName = ComponentName(context, VpnSecWidgetProvider::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews)
    }
}