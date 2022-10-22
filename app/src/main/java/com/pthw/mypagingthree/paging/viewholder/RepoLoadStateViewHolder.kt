package com.pthw.mypagingthree.paging.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.pthw.mypagingthree.base.recyclerview.BaseViewHolder
import com.pthw.mypagingthree.databinding.ReposLoadStateFooterViewItemBinding

class RepoLoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : BaseViewHolder<LoadState>(binding.root) {
    override fun bind(item: LoadState) {
        if (item is LoadState.Error) {
            binding.errorMsg.text = item.error.localizedMessage
        }
        binding.progressBar.isVisible = item is LoadState.Loading
        binding.retryButton.isVisible = item is LoadState.Error
        binding.errorMsg.isVisible = item is LoadState.Error
        binding.retryButton.setOnClickListener { retry.invoke() }
    }
}