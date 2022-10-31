package com.pthw.appbase.core.fastadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class AnimatableAdapter<T>(
    private var items: MutableList<T>,
    private var diffUtilCb : DiffUtil.ItemCallback<T> ,
    private var list: RecyclerView? = null,
    private var vpList: ViewPager2? = null
) : ListAdapter<T, FastViewHolder<T>>(diffUtilCb) {

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
        bindMap.first { it.predicated(items[position], position) }.type
    } catch (e: Exception) {
        0
    }

    /**
     * The function used for mapping types to layouts
     * @param layout - The ID of the XML layout of the given type
     * @param predicate - Function used to sort the ic_qr_code_white. For example, a Type field inside your ic_qr_code_white class with different values for different types.
     * @param bind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
     * so you can just use the XML names of the views inside your layout to address them.
     */
    fun map(
        @LayoutRes layout: Int, predicated: (item: T, idx: Int) -> Boolean,
        bind: View.(item: T, pos: Int) -> Unit
    ): AnimatableAdapter<T> {
        bindMap.add(BindMap(layout, typeCounter++, bind, predicated))
        list?.adapter = this
        vpList?.adapter = this
        return this
    }

    /**
     * The function used for mapping types to layouts
     * @param layoutFactory - factory that creates the view for this adapter
     * @param predicate - Function used to sort the ic_qr_code_white. For example, a Type field inside your ic_qr_code_white class with different values for different types.
     * @param bind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
     * so you can just use the XML names of the views inside your layout to address them.
     */
    fun map(
        layoutFactory: LayoutFactory,
        predicated: (item: T, idx: Int) -> Boolean,
        bind: View.(item: T, pos: Int) -> Unit
    ): AnimatableAdapter<T> {
        bindMap.add(BindMap(layoutFactory, typeCounter++, bind, predicated))
        list?.adapter = this
        vpList?.adapter = this
        return this
    }

    /**
     * Sets up a layout manager for the recycler view.
     */
    fun layoutManager(manager: RecyclerView.LayoutManager): AnimatableAdapter<T> {
        vpList?.let { throw UnsupportedOperationException("layoumanager not needed for ViewPager2") }
        list!!.layoutManager = manager
        return this
    }

    fun itemChange(callback: (Int) -> Unit): AnimatableAdapter<T> {
        this.callback = callback
        return this
    }




    fun update(newList: MutableList<T>) {
        submitList(newList)
        items = currentList
        notifyDataSetChanged()
        callback(itemCount)
    }

    fun add(item: T, idx: Int = itemCount) {
        items.add(idx, item)
        notifyItemInserted(idx)
        callback(itemCount)
    }

    fun update(pos: Int, item: T) {
        items.removeAt(pos)
        items.add(pos, item)
        notifyItemChanged(pos)
        callback(itemCount)
    }

    fun remove(item: T) {
        remove(items.indexOf(item))
        callback(itemCount)
    }

    fun remove(idx: Int) {
        items.removeAt(idx)
        notifyItemRemoved(idx)
        callback(itemCount)
    }

    fun indexOf(item: T) = items.indexOf(item)
    fun getItems() = items
    fun get(idx: Int) = items[idx]
    fun clear() {
        val index = items.size
        items.clear()
        notifyItemRangeRemoved(0, index)
        callback(itemCount)
    }
}