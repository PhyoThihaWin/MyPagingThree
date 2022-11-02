package com.pthw.listdialog

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class ListDetails : ItemDetailsLookup.ItemDetails<Long>() {
    var position: Long = 0
    override fun getPosition(): Int {
        return position.toInt()
    }

    override fun getSelectionKey(): Long {
        return position
    }

    override fun inSelectionHotspot(e: MotionEvent): Boolean {
        return true
    }
}

class KeyProvider : ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int): Long {
        return position.toLong()
    }

    override fun getPosition(key: Long): Int {
        return key.toInt()
    }
}

class DetailsClickLookup(private val rv: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        if (e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_DOWN) {
            val view = rv.findChildViewUnder(e.x, e.y)
            view?.apply {
                val vh = rv.getChildViewHolder(this)
                return when (vh) {
                    is SearchListDialogAdapter<*>.ItemViewHolder -> {
                        vh.details
                    }
                    else -> {
                        null
                    }
                }
            }
        }
        return null
    }
}