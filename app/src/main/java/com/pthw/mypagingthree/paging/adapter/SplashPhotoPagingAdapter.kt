package com.pthw.mypagingthree.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.base.recyclerview.BaseDiffUtil
import com.pthw.mypagingthree.base.recyclerview.BaseViewHolder
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.databinding.ListItemSplashPhotoBinding

class SplashPhotoPagingAdapter(private val onClick: (SplashPhoto) -> Unit) :
    PagingDataAdapter<SplashPhoto, PhotoViewHolder>(
        BaseDiffUtil(
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

class PhotoViewHolder(
    private val binding: ListItemSplashPhotoBinding,
    private val onClick: (SplashPhoto) -> Unit
) :
    BaseViewHolder<SplashPhoto>(binding.root) {
    override fun bind(item: SplashPhoto) {
        binding.apply {
            Glide.with(itemView)
                .load(item.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(imageView)

            textViewUserName.text = item.user.username

            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

}

