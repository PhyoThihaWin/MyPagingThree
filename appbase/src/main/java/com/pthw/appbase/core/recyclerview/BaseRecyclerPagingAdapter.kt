package com.pthw.appbase.core.recyclerview

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class BaseRecyclerPagingAdapter<itemType, VH : BaseViewHolder<itemType>>() :
    RecyclerView.Adapter<VH>() {

    var isLastReached = false
    val footerLoadingAdapter: FooterLoadingAdapter by lazy {
        FooterLoadingAdapter()
    }

    protected var mData: MutableList<itemType>? = null

    val items: List<itemType>
        get() = if (mData == null) ArrayList() else mData!!

    init {
        mData = ArrayList()
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        mData?.let {
            holder.bind(it[position])
        }
    }

    fun setNewData(newData: MutableList<itemType>) {
        mData = newData
        notifyDataSetChanged()
        hideFooterLoading()
    }

    fun appendNewData(newData: List<itemType>) {
        mData!!.addAll(newData)
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): itemType? {
        return if (position < mData!!.size) mData!![position] else null

    }

    fun removeData(data: itemType) {
        mData!!.remove(data)
        notifyDataSetChanged()
    }

    fun removeDataAt(index: Int) {
        if (index == -1) return
        mData!!.removeAt(index)
        notifyDataSetChanged()
    }

    fun addNewData(data: itemType) {
        mData!!.add(data)
        notifyDataSetChanged()
        hideFooterLoading()
    }

    fun addDataAtPositionZero(data: itemType) {
        mData!!.add(0, data)
        notifyDataSetChanged()
    }

    fun addDataAt(index: Int, data: itemType) {
        if (index == -1) return
        mData!!.add(index, data)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }

    fun showFooterLoading() {
        if (!isLastReached && itemCount > 0) {
            footerLoadingAdapter.setNewData(mutableListOf(1))
            Timber.d("Show footer loading!")
        }
    }

    fun hideFooterLoading() {
        if (footerLoadingAdapter.itemCount != 0) {
            footerLoadingAdapter.setNewData(mutableListOf())
            Timber.d("Hide footer loading!")
        }
    }

    fun lastItemReached() {
        isLastReached = true
        hideFooterLoading()
    }

    fun getLastVisibleItem(): itemType? {
        return mData?.last()
    }

}