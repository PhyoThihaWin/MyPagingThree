package com.pthw.mypagingthree.feature.githubrepo.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pthw.appbase.core.recyclerview.diffCallBackWith
import com.pthw.appbase.extension.inflater
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.RepoViewItemBinding
import com.pthw.mypagingthree.databinding.SeparatorViewItemBinding
import com.pthw.mypagingthree.feature.githubrepo.viewholder.RepoSeparatorViewHolder
import com.pthw.mypagingthree.feature.githubrepo.viewholder.RepoViewHolder
import com.pthw.mypagingthree.feature.githubrepo.ui.UiModel

class ReposAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(
    diffCallBackWith(areItemTheSame = { old, new ->
        (old is UiModel.RepoItem && new is UiModel.RepoItem && old.repo.fullName == new.repo.fullName) ||
                (old is UiModel.SeparatorItem && new is UiModel.SeparatorItem && old.description == new.description)
    },
        areContentsTheSame = { old, new -> old == new })
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.repo_view_item)
            RepoViewHolder(RepoViewItemBinding.inflate(parent.inflater(), parent, false))
        else RepoSeparatorViewHolder(SeparatorViewItemBinding.inflate(parent.inflater(), parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.RepoItem -> R.layout.repo_view_item
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.RepoItem -> (holder as RepoViewHolder).bind(uiModel.repo)
                is UiModel.SeparatorItem -> (holder as RepoSeparatorViewHolder).bind(uiModel.description)
                else -> {}
            }
        }
    }
}