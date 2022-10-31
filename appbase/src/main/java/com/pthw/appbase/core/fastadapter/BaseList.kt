package com.pthw.appbase.core.fastadapter

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.bind(items: List<T>): FastAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return FastAdapter(items.toMutableList(), this)
}

fun <T> RecyclerView.bind(items: List<T>, diffUtil: DiffUtil.ItemCallback<T>): FastListAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return FastListAdapter(items.toMutableList(), diffUtil, this)
}

fun <T> RecyclerView.bind(
    items: List<T>,
    @LayoutRes singleLayout: Int = 0,
    singleBind: (View.(item: T, pos: Int) -> Unit)
): FastAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return FastAdapter(
        items.toMutableList(), this
    ).map(singleLayout, { item: T, idx: Int -> true }, singleBind)
}


fun <T> SmartRecyclerView.bindAnimate(
    items: List<T>,
    @LayoutRes singleLayout: Int = 0,
    diffCallback : DiffUtil.ItemCallback<T>,
    singleBind: (View.(item: T, pos: Int) -> Unit)

): AnimatableAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return AnimatableAdapter(
        items.toMutableList(), diffCallback , this
    ).map(singleLayout, { item: T, idx: Int -> true }, singleBind)
}

fun <T> RecyclerView.bind(
    items: List<T>,
    diffUtil: DiffUtil.ItemCallback<T>,
    @LayoutRes singleLayout: Int = 0,
    singleBind: (View.(item: T, pos: Int) -> Unit)
): FastListAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return FastListAdapter(
        items.toMutableList(), diffUtil, this
    ).map(singleLayout, { item: T, idx: Int -> true }, singleBind)
}

fun <T> RecyclerView.update(newItems: MutableList<T>) {
    (adapter as? FastListAdapter<T>)?.apply {
        this.update(newItems)
    } ?: (adapter as? FastAdapter<T>)?.apply {
        this.update(newItems)
    }
}

fun <T> RecyclerView.updateAnimate(newItems: MutableList<T>) {
    (adapter as? AnimatableAdapter<T>)?.apply {
        this.update(newItems)
    } ?: (adapter as? AnimatableAdapter<T>)?.apply {
        this.update(newItems)
    }
}

fun <T> RecyclerView.update(pos: Int, item: T) {
    (adapter as? FastListAdapter<T>)?.update(pos, item)
        ?: (adapter as? FastAdapter<T>)?.update(pos, item)
}

fun <T> RecyclerView.add(item: T, pos: Int = adapter?.itemCount ?: 0) {
    (adapter as? FastAdapter<T>)?.add(item, pos) ?: (adapter as? FastListAdapter<T>)?.add(item, pos)
}

fun <T> RecyclerView.remove(item: T) {
    (adapter as? FastAdapter<T>)?.remove(item) ?: (adapter as? FastListAdapter<T>)?.remove(item)
}

fun RecyclerView.clear() {
    (adapter as? FastAdapter<*>)?.clear() ?: (adapter as? FastListAdapter<*>)?.clear()
}

fun <T> RecyclerView.indexOf(item: T): Int {
    return ((adapter as? FastListAdapter<T>)?.indexOf(item)
        ?: (adapter as? FastAdapter<T>)?.indexOf(item)) ?: -1
}

fun <T> RecyclerView.getItems(): MutableList<T> {
    return ((adapter as? FastAdapter<T>)?.getItems()
        ?: (adapter as? FastListAdapter<T>)?.getItems()) ?: mutableListOf()
}

fun <T> RecyclerView.get(pos: Int): T {
    return ((adapter as? FastListAdapter<T>)?.get(pos) ?: (adapter as? FastAdapter<T>)?.get(pos))!!
}

val RecyclerView.size: Int
    get() = this.adapter!!.itemCount

fun RecyclerView.refresh() {
    adapter!!.notifyDataSetChanged()
}
