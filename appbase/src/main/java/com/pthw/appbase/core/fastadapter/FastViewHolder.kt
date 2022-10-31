package com.pthw.appbase.core.fastadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FastViewHolder<T>(
    override val containerView: View,
    val holderType: Int
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(entry: T, func: View.(item: T, pos: Int) -> Unit) {
        containerView.apply {
            func(entry, adapterPosition)
        }
    }
}