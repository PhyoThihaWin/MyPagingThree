package com.pthw.appbase.core.fastadapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * This class controls not only whether the list has reached to its end
 * or whether the list has reached to its top most
 */
open class SmartScrollListener(
    private val mSmartScrollListener: OnSmartScrollListener,

    private val mFlag: Int = FLAG_VERTICALLY
) : RecyclerView.OnScrollListener() {

    companion object {
        val FLAG_VERTICALLY = 0
        val FLAG_HORIZONTAL = 1
    }

    protected var isListEndReached = false

    protected var limit = 10
    private var thresholdItemCount = 7

    fun setThresholdItemCount(thresholdItemCount: Int) {
        this.thresholdItemCount = thresholdItemCount + 1
    }

    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(rv, dx, dy)

        val visibleItemCount = rv.layoutManager!!.childCount
        val totalItemCount = rv.layoutManager!!.itemCount
        val pastVisibleItems =
            (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        /** User had scrolled down to the new items , so make the @newItemAlert invisible */
        /* if (newItemAlert != null && pastVisibleItems + visibleItemCount > limit && newItemAlert!!.visibility == View.VISIBLE) {
             newItemAlert!!.visibility = View.GONE
         }*/
        if (visibleItemCount + pastVisibleItems < totalItemCount) {
            isListEndReached = false
        }

        /*if (swipeRefreshLayout != null)
            swipeRefreshLayout.isEnabled =
                (rv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0*/
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
        super.onScrollStateChanged(recyclerView, scrollState)
        /*  if (scrollState == RecyclerView.SCROLL_STATE_IDLE
                && ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1
                && !isListEndReached) {*/

        if (mFlag == FLAG_VERTICALLY && !recyclerView.canScrollVertically(1) &&
            !isListEndReached && scrollState == RecyclerView.SCROLL_STATE_IDLE) {

            isListEndReached = true
            limit = recyclerView.layoutManager!!.itemCount
            mSmartScrollListener.onListEndReach()
        }

        if (mFlag == FLAG_HORIZONTAL && !recyclerView.canScrollHorizontally(1) &&
            !isListEndReached && scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            isListEndReached = true
            limit = recyclerView.layoutManager!!.itemCount
            mSmartScrollListener.onListEndReach()
        }
    }

    interface OnSmartScrollListener {
        fun onListEndReach()
    }
}
