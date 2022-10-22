package com.pthw.mypagingthree.paging.viewholder

import com.pthw.mypagingthree.base.recyclerview.BaseViewHolder
import com.pthw.mypagingthree.databinding.SeparatorViewItemBinding

class RepoSeparatorViewHolder(
    private val binding: SeparatorViewItemBinding
) : BaseViewHolder<String>(binding.root) {
    override fun bind(item: String) {
        binding.separatorDescription.text = item
    }
}