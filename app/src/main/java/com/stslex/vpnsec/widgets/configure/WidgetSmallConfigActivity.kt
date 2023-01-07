package com.stslex.vpnsec.widgets.configure

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.stslex.vpnsec.R
import com.stslex.vpnsec.base.BaseExt.pendingSettingsIntent
import com.stslex.vpnsec.databinding.WidgetSmallConfigActivityBinding
import com.stslex.vpnsec.widgets.configure.adapter.SmallWidgetRecyclerAdapter
import com.stslex.vpnsec.widgets.configure.model.WidgetItem

class WidgetSmallConfigActivity : Activity() {

    private var _binding: WidgetSmallConfigActivityBinding? = null
    private val binding: WidgetSmallConfigActivityBinding
        get() = requireNotNull(_binding)

    private val adapter: SmallWidgetRecyclerAdapter by lazy {
        SmallWidgetRecyclerAdapter(::onWidgetClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setResult(RESULT_CANCELED)
        super.onCreate(savedInstanceState)
        _binding = WidgetSmallConfigActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.widgetRecycler.adapter = adapter
        adapter.setItems(smallWidgets)
    }

    private fun onWidgetClick(widgetItem: WidgetItem) {
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(packageName, widgetItem.layoutRes).apply {
            setOnClickPendingIntent(R.id.vpnLayout, pendingSettingsIntent)
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        showWidget(appWidgetId)
    }

    private fun showWidget(appWidgetId: Int) {
        val widgetIntent = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, widgetIntent)
        finish()
    }
}