package com.pthw.mypagingthree.paging.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.pthw.mypagingthree.databinding.ReposLoadStateFooterViewItemBinding
import com.pthw.mypagingthree.paging.viewholder.RepoLoadStateViewHolder
import com.pthw.mypagingthree.utils.ext.inflater

class RepoLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RepoLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RepoLoadStateViewHolder {
        return RepoLoadStateViewHolder(
            ReposLoadStateFooterViewItemBinding.inflate(
                parent.inflater(),
                parent,
                false
            ), retry
        )
    }
}