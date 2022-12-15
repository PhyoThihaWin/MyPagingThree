package com.pthw.appbase.core.recyclerview

import android.view.ViewGroup
import com.pthw.appbase.databinding.ItemViewFooterLoadingBinding
import com.pthw.appbase.extension.inflater

class FooterLoadingAdapter : BaseRecyclerAdapter<Int, FooterLoadingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterLoadingViewHolder {
        val binding = ItemViewFooterLoadingBinding.inflate(parent.inflater(), parent, false)
        return FooterLoadingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FooterLoadingViewHolder, position: Int) {
        mData?.let { holder.bind(it[position]) }
    }
}

class FooterLoadingViewHolder(val binding: ItemViewFooterLoadingBinding) :
    BaseViewHolder<Int>(binding.root) {
    override fun bind(item: Int) {}
}