package com.pthw.listdialog.ui

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.selection.SelectionTracker
import com.pthw.listdialog.R
import com.pthw.listdialog.base.BaseRecyclerAdapter
import com.pthw.listdialog.base.BaseViewHolder
import com.pthw.listdialog.databinding.ListItemDialogBinding
import com.pthw.listdialog.utils.inflater
import com.pthw.listdialog.utils.textAppearance
import com.pthw.listdialog.utils.useColor


internal class SearchListDialogAdapter<Item : Any?>(
    private val textStyle: Int = 0,
    private val onSelected: (Int, Item) -> Unit,
    private val listCount: (Int) -> Unit
) :
    BaseRecyclerAdapter<Item, SearchListDialogAdapter<Item>.ItemViewHolder>(),
    Filterable {

    private var selectionTracker: SelectionTracker<Long>? = null
    private var list = mutableListOf<Item>()

    init {
        hasStableIds()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = parent.inflater()
        val view = ListItemDialogBinding.inflate(inflater, parent, false).root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        mData?.let { data ->
            holder.bind(data[position])
            selectionTracker?.let {
                holder.details.position = position.toLong()
                if (it.isSelected(position.toLong())) {
                    holder.select()
                    onSelected.invoke(position, data[position])
                } else {
                    holder.unSelect()
                }
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : BaseViewHolder<Item>(itemView) {
        val details = ListDetails()
        private val binding = ListItemDialogBinding.bind(itemView)
        override fun bind(item: Item) {
            if (textStyle != 0) binding.tvText.textAppearance(textStyle)
            binding.tvText.text = item.castString()
        }

        fun select() {
            binding.tvText.setBackgroundColor(binding.root.context.useColor(R.color.colorPrimaryForRipple))
        }

        fun unSelect() {
            binding.tvText.setBackgroundColor(binding.root.context.useColor(R.color.colorBackground))
        }
    }

    fun setData(list: MutableList<Item>) {
        this.list = list
        setNewData(list)
    }

    fun setSelectionTracker(tracker: SelectionTracker<Long>?) {
        this.selectionTracker = tracker
    }


    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Item>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(list)
            } else {
                for (item in list) {
                    if (item.castString().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            setNewData(filterResults?.values as MutableList<Item>)
            listCount.invoke(itemCount)
        }

    }

}

/**
 * Check Generic or Primitive
 */
fun Any?.castString(): String {
    return when (this) {
        is String -> this
        is Int, Double, Float -> this.toString()
        is GenericItem -> this.toMyString()
        else -> this.toString()
    }
}