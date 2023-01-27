package com.pthw.appbase.extension

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pthw.appbase.core.fastadapter.SmartScrollListener
import com.pthw.appbase.core.recyclerview.BaseRecyclerAdapter
import com.pthw.appbase.core.recyclerview.BaseRecyclerPagingAdapter
import com.pthw.appbase.core.recyclerview.BaseViewHolder

/**
 * Created by Vincent on 2/13/20
 */
fun ViewHolder.withSafeAdapterPosition(
    onUnsafePosition: ((Unit) -> (Unit)) = { },
    function: ((@ParameterName("position") Int) -> (Unit))
) {
    val position = adapterPosition
    if (position != RecyclerView.NO_POSITION) {
        function.invoke(position)
    }
}

const val UP_SCROLL_DIRECTION = -1
const val DOWN_SCROLL_DIRECTION = 1

fun RecyclerView.applySmartScrollListener(
    listener: SmartScrollListener.OnSmartScrollListener,
    direction: Int = DOWN_SCROLL_DIRECTION
) {
    this.addOnScrollListener(SmartScrollListener(listener, direction))
}

fun <A : BaseViewHolder<B>, B> BaseRecyclerPagingAdapter<B, A>.asPaginationAdapter(): ConcatAdapter {
    return ConcatAdapter(this, this.footerLoadingAdapter)
}