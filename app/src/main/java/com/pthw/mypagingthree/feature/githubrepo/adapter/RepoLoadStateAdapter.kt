package com.pthw.mypagingthree.feature.githubrepo.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.pthw.appbase.extension.inflater
import com.pthw.mypagingthree.databinding.ReposLoadStateFooterViewItemBinding
import com.pthw.mypagingthree.feature.githubrepo.viewholder.RepoLoadStateViewHolder

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
            ),
            retry
        )
    }
}
