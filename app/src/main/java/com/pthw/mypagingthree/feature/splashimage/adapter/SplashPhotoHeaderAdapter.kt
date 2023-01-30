package com.pthw.mypagingthree.feature.splashimage.adapter

import android.view.ViewGroup
import com.pthw.appbase.core.recyclerview.BaseListAdapter
import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.appbase.core.recyclerview.diffCallBackWith
import com.pthw.listdialog.utils.inflater
import com.pthw.mypagingthree.databinding.ListItemSplashphotoHeaderBinding

class SplashPhotoHeaderAdapter() : BaseListAdapter<String, BaseViewHolder<String>>(
    diffCallback = diffCallBackWith(
        areContentsTheSame = { item1, item2 ->
            item1 == item2
        },
        areItemTheSame = { item1, item2 ->
            item1 == item2
        }
    )
) {

    init {
        submitList(arrayListOf("1"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        val binding = ListItemSplashphotoHeaderBinding.inflate(parent.inflater(), parent, false)
        return SplashPhotoHeaderViewHolder(binding)
    }

    class SplashPhotoHeaderViewHolder(binding: ListItemSplashphotoHeaderBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) {
        }
    }
}
