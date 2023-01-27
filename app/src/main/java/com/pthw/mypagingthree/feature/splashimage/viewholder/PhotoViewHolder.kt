package com.pthw.mypagingthree.feature.splashimage.viewholder

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ListItemSplashPhotoBinding

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
