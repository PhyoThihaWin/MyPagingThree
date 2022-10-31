package com.pthw.appbase.core.fastadapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class SmartRecyclerView : RecyclerView {

    private val dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(dataObserver)

        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        checkIfEmpty()
    }

    /**
     * check if adapter connected to SRV is empty. If so, show emptyView.
     */
    private fun checkIfEmpty() {
        val isEmpty = adapter!!.itemCount == 0
        /* if (mEmptyView != null) {
             if (isEmpty) mEmptyView!!.show() else mEmptyView!!.hide()
         }*/
    }
}
