package com.pthw.mypagingthree.feature.firestorechat.activity

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.pthw.appbase.core.recyclerview.BaseListAdapter
import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.appbase.core.recyclerview.diffCallBackWith
import com.pthw.appbase.extension.inflater
import com.pthw.appbase.extension.loadFromUrl
import com.pthw.appbase.extension.useColor
import com.pthw.appbase.extension.withSafeAdapterPosition
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ListItemChatMessageFileBinding
import com.pthw.mypagingthree.databinding.ListItemChatMessageImageBinding
import com.pthw.mypagingthree.feature.firestorechat.CHAT_FILE_TYPE
import com.pthw.mypagingthree.feature.firestorechat.GOOGLE_DOC
import com.pthw.mypagingthree.feature.firestorechat.model.Attachment
import com.pthw.mypagingthree.webview.WebViewActivity

class ChattingChildAdapter(
    val isLeft: Boolean,
    val onClickImage: (Int) -> Unit
) : BaseListAdapter<Attachment, BaseViewHolder<Attachment>>(
    diffCallback = diffCallBackWith(
        areContentsTheSame = { item1, item2 ->
            item1 == item2
        },
        areItemTheSame = { item1, item2 ->
            item1 == item2
        }
    )
) {

    companion object {
        const val MESSAGE_IMAGE = 1
        const val MESSAGE_FILE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Attachment> {
        return when (viewType) {
            MESSAGE_IMAGE -> {
                val binding =
                    ListItemChatMessageImageBinding.inflate(parent.inflater(), parent, false)
                ImageViewHolder(binding)
            }
            else -> {
                val binding =
                    ListItemChatMessageFileBinding.inflate(parent.inflater(), parent, false)
                FileViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            CHAT_FILE_TYPE -> MESSAGE_FILE
            else -> MESSAGE_IMAGE
        }
    }

    inner class ImageViewHolder(
        val binding: ListItemChatMessageImageBinding
    ) : BaseViewHolder<Attachment>(binding.root) {
        override fun bind(item: Attachment) {
            binding.apply {
                ivImage.loadFromUrl(item.url.orEmpty(), R.drawable.ic_launcher_background)

                root.setOnClickListener {
                    onClickImage.invoke(absoluteAdapterPosition)
                }
            }
        }

    }


    inner class FileViewHolder(val binding: ListItemChatMessageFileBinding) :
        BaseViewHolder<Attachment>(binding.root) {

        init {
            binding.root.setOnClickListener { v ->
                withSafeAdapterPosition {
                    v.context.startActivity(
                        WebViewActivity.newIntent(v.context, GOOGLE_DOC + getItem(it).url)
                    )
                }
            }
        }

        override fun bind(item: Attachment) {
            binding.apply {
                if (item.name.isNullOrEmpty()) {
                    val fileName = item.url.orEmpty().split("/")
                    if (fileName.isNotEmpty()) tvFileName.text = fileName[fileName.lastIndex]
                } else tvFileName.text = item.name

                if (isLeft) {
                    layoutPdf.backgroundTintList =
                        ColorStateList.valueOf(root.context.useColor(R.color.colorWhite))
                    layoutImage.backgroundTintList =
                        ColorStateList.valueOf(root.context.useColor(R.color.backgroundColor))
                    tvFileName.setTextColor(root.context.useColor(R.color.standard_text_color))
                } else {
                    layoutPdf.backgroundTintList =
                        ColorStateList.valueOf(root.context.useColor(R.color.colorPrimary))
                    layoutImage.backgroundTintList =
                        ColorStateList.valueOf(root.context.useColor(R.color.colorJoinBtn))
                    tvFileName.setTextColor(root.context.useColor(R.color.colorWhite))
                }
            }


        }

    }

}



