package com.pthw.appbase.core.recyclerview

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T : Any>(
    private val areItemTheSame: ((T, T) -> Boolean),
    private val areContentsTheSame: ((T, T) -> Boolean)
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemTheSame.invoke(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSame.invoke(oldItem, newItem)
    }
}