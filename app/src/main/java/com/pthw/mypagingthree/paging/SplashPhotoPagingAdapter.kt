package com.pthw.mypagingthree.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.databinding.ListItemSplashPhotoBinding

class SplashPhotoPagingAdapter() : PagingDataAdapter<SplashPhoto, PhotoViewHolder>(PHOTO_DIFF) {
    companion object {
        private val PHOTO_DIFF = object : DiffUtil.ItemCallback<SplashPhoto>() {
            override fun areItemsTheSame(oldItem: SplashPhoto, newItem: SplashPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SplashPhoto, newItem: SplashPhoto): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ListItemSplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }
}

class PhotoViewHolder(
    private val binding: ListItemSplashPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: SplashPhoto) {
        binding.apply {
            Glide.with(itemView)
                .load(photo.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(imageView)

            textViewUserName.text = photo.user.username
        }
    }
}