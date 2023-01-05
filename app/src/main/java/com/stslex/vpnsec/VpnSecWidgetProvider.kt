package com.stslex.vpnsec

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.stslex.vpnsec.BaseExt.settingsIntent


class VpnSecWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.appwidget_provider_layout
        )

        context.updateBackground(remoteViews)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            remoteViews.setOnClickResponse(
                R.id.vpnLayout,
                RemoteViews.RemoteResponse.fromPendingIntent(context.onCheckedChangePendingIntent)
            )
        } else {
            remoteViews.setOnClickPendingIntent(R.id.vpnLayout, context.pendingIntent)
        }

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (Build.VERSION.SDK_INT >= 31 &&
            intent?.extras?.getInt(REQUEST_CODE) == REQUEST_CODE_FROM_COLLECTION_WIDGET
        ) {
            context?.updateBackground(
                RemoteViews(
                    context.packageName,
                    R.layout.appwidget_provider_layout
                )
            )
            context?.startActivity(settingsIntent)
        }
    }

    private fun Context.updateBackground(remoteViews: RemoteViews) {
//        todo add background change
//        val color = if (isVpnConnect) {
//            android.graphics.Color.BLACK
//        } else {
//            android.graphics.Color.WHITE
//        }
//        remoteViews.setInt(
//            R.id.vpnImageView, "setBackgroundColor",
//            color
//        )
    }

    private val Context.onCheckedChangePendingIntent: PendingIntent
        get() = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE_FROM_COLLECTION_WIDGET,
            Intent(this, VpnSecWidgetProvider::class.java).apply {
                putExtra(EXTRA_VIEW_ID, R.id.vpnLayout)
                putExtra(REQUEST_CODE, REQUEST_CODE_FROM_COLLECTION_WIDGET)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

    private val Context.pendingIntent: PendingIntent
        get() = PendingIntent.getActivity(
            this,
            0,
            settingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    companion object {
        const val REQUEST_CODE_FROM_COLLECTION_WIDGET = 2
        const val EXTRA_VIEW_ID = "extra_view_id"
        const val REQUEST_CODE = "request_code"
    }
}