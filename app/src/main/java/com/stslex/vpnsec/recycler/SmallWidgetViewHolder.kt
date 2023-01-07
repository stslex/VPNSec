package com.stslex.vpnsec.recycler

import androidx.recyclerview.widget.RecyclerView
import com.stslex.vpnsec.databinding.LayoutWidgetSmallItemBinding

class SmallWidgetViewHolder(
    private val binding: LayoutWidgetSmallItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WidgetItem) {
        binding.widgetLayout.inflatedId = item.widgetRes
        binding.widgetTitleTextView.text = item.widgetTitle
    }
}