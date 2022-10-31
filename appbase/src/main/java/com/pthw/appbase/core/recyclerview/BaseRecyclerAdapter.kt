package com.pthw.appbase.core.recyclerview

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<itemType, VH : BaseViewHolder<itemType>>() :
    RecyclerView.Adapter<VH>() {

//    protected var mLayoutInflater: LayoutInflater

    protected var mData: MutableList<itemType>? = null

    val items: List<itemType>
        get() = if (mData == null) ArrayList() else mData!!

    init {
//        mLayoutInflater = LayoutInflater.from(context)
        mData = ArrayList()
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    fun setNewData(newData: MutableList<itemType>) {
        mData = newData
        notifyDataSetChanged()
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

    fun addNewData(data: itemType) {
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun addDataAtPositionZero(data: itemType) {
        mData!!.add(0, data)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }
}