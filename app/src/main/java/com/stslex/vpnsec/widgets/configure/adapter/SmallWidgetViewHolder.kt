package com.stslex.vpnsec.widgets.configure.adapter

import androidx.recyclerview.widget.RecyclerView
import com.stslex.vpnsec.databinding.LayoutWidgetSmallItemBinding
import com.stslex.vpnsec.widgets.configure.model.WidgetItem

class SmallWidgetViewHolder(
    private val binding: LayoutWidgetSmallItemBinding,
    private val widgetClickListener: WidgetClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WidgetItem) {
        binding.widgetLayout.layoutResource = item.layoutRes
        binding.widgetLayout.inflate()
        binding.widgetTitleTextView.text = binding.root.resources.getString(item.titleRes)
        binding.root.setOnClickListener {
            widgetClickListener.click(item)
        }
    }
}