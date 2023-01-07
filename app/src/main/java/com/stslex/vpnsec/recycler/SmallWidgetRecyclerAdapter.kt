package com.stslex.vpnsec.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stslex.vpnsec.databinding.LayoutWidgetSmallItemBinding

class SmallWidgetRecyclerAdapter : RecyclerView.Adapter<SmallWidgetViewHolder>() {

    private val listOfItems: MutableList<WidgetItem> = mutableListOf()

    override fun onBindViewHolder(holder: SmallWidgetViewHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallWidgetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutWidgetSmallItemBinding.inflate(layoutInflater, parent, false)
        return SmallWidgetViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfItems.size

    fun setItems(widgets: List<WidgetItem>) {
        val diffUtilCallback = WidgetItemDiffUtilCallback(listOfItems, widgets)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listOfItems.clear()
        listOfItems.addAll(widgets)
        diffResult.dispatchUpdatesTo(this)
    }
}