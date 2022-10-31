package com.pthw.appbase.core.fastadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.lang.Exception

class FastListAdapter<T>(
    private var items: MutableList<T>,
    diffUtil: DiffUtil.ItemCallback<T>,
    private var list: RecyclerView? = null,
    private var vpList: ViewPager2? = null
) : ListAdapter<T, FastViewHolder<T>>(diffUtil) {

    private var callback: (Int) -> Unit = {}

    init {
        if (vpList != null && list != null)
            throw IllegalArgumentException("You can only use either the Recycler(list) or the Pager(vpList)")
        if (vpList == null && list == null)
            throw IllegalArgumentException("You have to use either the Recycler(list) or the Pager(vpList)")
    }

    private var bindMap = mutableListOf<BindMap<T>>()
    private var typeCounter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastViewHolder<T> {
        return bindMap.first { it.type == viewType }.let {
            it.layoutFactory?.let {
                return FastViewHolder(it.createView(parent, viewType), viewType)
            } ?: run {
                return FastViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        it.layout,
                        parent, false
                    ), viewType
                )
            }
        }
    }

    override fun onBindViewHolder(holder: FastViewHolder<T>, position: Int) {
        val item = items.get(position)
        holder.bind(item, bindMap.first { it.type == holder.holderType }.bind)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = try {
        bindMap.first {
            it.predicated(items[position], position)
        }.type
    } catch (e: Exception) {
        Log.e(TAG, e.localizedMessage ?: "")
        0
    }

    fun map(
        @LayoutRes layout: Int, predicated: (item: T, idx: Int) -> Boolean,
        bind: View.(item: T, pos: Int) -> Unit
    ): FastListAdapter<T> {
        bindMap.add(BindMap(layout, typeCounter++, bind, predicated))
        list?.adapter = this
        vpList?.adapter = this
        return this
    }

    fun map(
        layoutFactory: LayoutFactory,
        predicated: (item: T, idx: Int) -> Boolean,
        bind: View.(item: T, pos: Int) -> Unit
    ): FastListAdapter<T> {
        bindMap.add(BindMap(layoutFactory, typeCounter++, bind, predicated))
        list?.adapter = this
        vpList?.adapter = this
        return this
    }

    fun layoutManager(manager: RecyclerView.LayoutManager): FastListAdapter<T> {
        vpList?.let { throw UnsupportedOperationException("layoumanager not needed for ViewPager2") }
        list!!.layoutManager = manager
        return this
    }

    fun itemChange(callback: (Int) -> Unit): FastListAdapter<T> {
        this.callback = callback
        return this
    }

    fun update(newList: MutableList<T>) {
        items = newList.toMutableList()
        submitList(items)
        callback(itemCount)
    }

    fun update(idx: Int, item: T) {
        items.removeAt(idx)
        items.add(idx, item)
        notifyItemChanged(idx)
        callback(itemCount)
    }

    fun add(item: T, idx: Int = itemCount) {
        items.add(idx, item)
        submitList(items)
        callback(itemCount)
    }

    fun remove(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
        callback(itemCount)
    }

    fun remove(d: T) {
        remove(items.indexOf(d))
        callback(itemCount)
    }

    fun get(idx: Int) = items[idx]

    fun indexOf(item: T) = items.indexOf(item)

    fun getItems() = items

    fun clear() {
        items.clear()
        submitList(items)
        callback(itemCount)
    }

    companion object {
        val TAG = FastListAdapter::class.java.name
    }
}