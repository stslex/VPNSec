package com.stslex.vpnsec

import android.app.Activity
import android.os.Bundle
import com.stslex.vpnsec.databinding.WidgetSmallConfigActivityBinding
import com.stslex.vpnsec.recycler.SmallWidgetRecyclerAdapter
import com.stslex.vpnsec.recycler.WidgetItem

class WidgetSmallConfigActivity : Activity() {

    private var _binding: WidgetSmallConfigActivityBinding? = null
    private val binding: WidgetSmallConfigActivityBinding
        get() = requireNotNull(_binding)

    private val adapter: SmallWidgetRecyclerAdapter by lazy {
        SmallWidgetRecyclerAdapter()
    }

    private val widgets = listOf(
        WidgetItem(
            widgetRes = R.layout.appwidget_provider_layout_small,
            widgetTitle = "circle widget"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setResult(RESULT_CANCELED)
        super.onCreate(savedInstanceState)
        _binding = WidgetSmallConfigActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter.setItems(widgets)
    }
}