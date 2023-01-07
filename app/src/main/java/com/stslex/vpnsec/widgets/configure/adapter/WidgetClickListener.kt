package com.stslex.vpnsec.widgets.configure.adapter

import com.stslex.vpnsec.widgets.configure.model.WidgetItem

fun interface WidgetClickListener {
    fun click(widgetItem: WidgetItem)
}