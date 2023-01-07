package com.stslex.vpnsec.recycler

import androidx.recyclerview.widget.DiffUtil

class WidgetItemDiffUtilCallback(
    private val oldList: List<WidgetItem>,
    private val newList: List<WidgetItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
}