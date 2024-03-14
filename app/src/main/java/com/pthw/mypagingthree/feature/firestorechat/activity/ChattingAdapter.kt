package com.pthw.mypagingthree.feature.firestorechat.activity

import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayoutManager
import com.pthw.appbase.core.recyclerview.BaseRecyclerPagingAdapter
import com.pthw.appbase.core.recyclerview.BaseViewHolder
import com.pthw.appbase.extension.*
import com.pthw.domain.extension.orZero
import com.pthw.mypagingthree.R
import com.pthw.mypagingthree.databinding.ListItemChatMessageDateBinding
import com.pthw.mypagingthree.databinding.ListItemChatMessageLeftBinding
import com.pthw.mypagingthree.databinding.ListItemChatMessageRightBinding
import com.pthw.mypagingthree.feature.firestorechat.activity.photoview.ImagePagerActivity
import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessage
import java.util.*

class ChattingAdapter : BaseRecyclerPagingAdapter<ChatMessage, BaseViewHolder<ChatMessage>>() {

    companion object {
        const val MESSAGE_LEFT = 1
        const val MESSAGE_RIGHT = 2
        const val MESSAGE_DATE = 3
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessage> {
        return when (viewType) {
            MESSAGE_LEFT -> {
                val binding =
                    ListItemChatMessageLeftBinding.inflate(parent.inflater(), parent, false)
                ChatLeftViewHolder(binding) as BaseViewHolder<ChatMessage>
            }
            MESSAGE_RIGHT -> {
                val binding =
                    ListItemChatMessageRightBinding.inflate(parent.inflater(), parent, false)
                ChatRightViewHolder(binding) as BaseViewHolder<ChatMessage>
            }
            else -> {
                val binding =
                    ListItemChatMessageDateBinding.inflate(parent.inflater(), parent, false)
                ChatDateViewHolder(binding) as BaseViewHolder<ChatMessage>
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mData!![position]) {
            is ChatMessage.ChatLeft -> {
                MESSAGE_LEFT
            }
            is ChatMessage.ChatRight -> {
                MESSAGE_RIGHT
            }
            is ChatMessage.ChatDate -> {
                MESSAGE_DATE
            }
        }
    }


    class ChatDateViewHolder(val binding: ListItemChatMessageDateBinding) :
        BaseViewHolder<ChatMessage.ChatDate>(binding.root) {
        override fun bind(item: ChatMessage.ChatDate) {
            binding.apply {
                tvMessageDate.text = item.date
            }
        }

    }


    class ChatLeftViewHolder(val binding: ListItemChatMessageLeftBinding) :
        BaseViewHolder<ChatMessage.ChatLeft>(binding.root) {
        override fun bind(item: ChatMessage.ChatLeft) {
            binding.apply {
                tvMessage.text = item.data.message
                tvDateTime.text = item.data.messageTime()

                item.data.attachments?.let { attachments ->
                    if (attachments.isEmpty()) {
                        tvMessage.show()
                        rvChatImage.gone()
                    } else {
                        tvMessage.gone()
                        rvChatImage.show()

                        val childAdapter = ChattingChildAdapter(isLeft = true) { pos ->
                            val imageList = attachments.map { it.url.orEmpty() }.toArrayList()
                            root.context.startActivity(
                                ImagePagerActivity.newIntent(root.context, imageList, pos)
                            )
                        }
                        rvChatImage.layoutManager = FlexboxLayoutManager(root.context)
                        rvChatImage.adapter = childAdapter
                        childAdapter.submitList(attachments)
                    }
                }
            }
        }
    }

    inner class ChatRightViewHolder(val binding: ListItemChatMessageRightBinding) :
        BaseViewHolder<ChatMessage.ChatRight>(binding.root) {
        override fun bind(item: ChatMessage.ChatRight) {
            binding.apply {
                tvMessage.text = item.data.message
                tvDateTime.text = item.data.messageTime()

                item.data.attachments?.let { attachments ->
                    if (attachments.isEmpty()) {
                        tvMessage.show()
                        rvChatImage.gone()
                    } else {
                        tvMessage.gone()
                        rvChatImage.show()

                        val childAdapter = ChattingChildAdapter(isLeft = false) { pos ->
                            val imageList = attachments.map { it.url.orEmpty() }.toArrayList()
                            root.context.startActivity(
                                ImagePagerActivity.newIntent(root.context, imageList, pos)
                            )
                        }
                        rvChatImage.layoutManager = FlexboxLayoutManager(root.context)
                        rvChatImage.adapter = childAdapter
                        childAdapter.submitList(attachments)
                    }
                }


                if (item.data.read_by?.size.orZero() > 1) {
                    if (absoluteAdapterPosition == 0) {
                        ivSentSeen.setImageResource(R.drawable.ic_chat_seen)
                        ivSentSeen.show()
                    } else ivSentSeen.hide()
                } else {
                    ivSentSeen.setImageResource(R.drawable.ic_chat_sent)
                    ivSentSeen.show()
                }

            }
        }

    }
}
