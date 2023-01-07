package com.stslex.vpnsec.widgets.configure

import com.stslex.vpnsec.R
import com.stslex.vpnsec.widgets.configure.model.WidgetItem

val smallWidgets: List<WidgetItem> = listOf(
    WidgetItem(
        layoutRes = R.layout.appwidget_provider_layout_circle,
        titleRes = R.string.app_widget_title_circle
    ),
    WidgetItem(
        layoutRes = R.layout.appwidget_provider_layout_rounded,
        titleRes = R.string.app_widget_title_rounded
    ),
    WidgetItem(
        layoutRes = R.layout.appwidget_provider_layout_transparent,
        titleRes = R.string.app_widget_title_transparent
    ),
)