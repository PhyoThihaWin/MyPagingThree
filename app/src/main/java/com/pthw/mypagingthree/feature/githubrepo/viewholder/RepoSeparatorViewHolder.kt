package com.pthw.mypagingthree.feature.githubrepo.viewholder

import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.mypagingthree.databinding.SeparatorViewItemBinding

class RepoSeparatorViewHolder(
    private val binding: SeparatorViewItemBinding
) : BaseViewHolder<String>(binding.root) {
    override fun bind(item: String) {
        binding.separatorDescription.text = item
    }
}