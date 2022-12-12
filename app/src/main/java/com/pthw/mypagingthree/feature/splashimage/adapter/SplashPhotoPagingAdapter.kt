package com.pthw.mypagingthree.feature.splashimage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.pthw.appbase.core.recyclerview.diffCallBackWith
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import com.pthw.mypagingthree.databinding.ListItemSplashPhotoBinding
import com.pthw.mypagingthree.feature.splashimage.viewholder.PhotoViewHolder

class SplashPhotoPagingAdapter(private val onClick: (SplashPhoto) -> Unit) :
    PagingDataAdapter<SplashPhoto, PhotoViewHolder>(
        diffCallBackWith(
            areItemTheSame = { old, new -> old.id == new.id },
            areContentsTheSame = { old, new -> old == new }
        )
    ) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ListItemSplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding, onClick)
    }
}
