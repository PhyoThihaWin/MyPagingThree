package com.pthw.mypagingthree.feature.firestore_chat

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pthw.appbase.core.recyclerview.*
import com.pthw.appbase.extension.inflater
import com.pthw.domain.extension.dateTimeFormatter
import com.pthw.mypagingthree.databinding.ListItemChatMessageLeftBinding
import com.pthw.mypagingthree.databinding.ListItemChatMessageRightBinding

class ChattingAdapter : BaseRecyclerPagingAdapter<ChatMessage, BaseViewHolder<ChatMessage>>() {

    companion object {
        const val MESSAGE_LEFT = 1
        const val MESSAGE_RIGHT = 2
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessage> {
        return when (viewType) {
            MESSAGE_LEFT -> {
                val binding =
                    ListItemChatMessageLeftBinding.inflate(parent.inflater(), parent, false)
                ChatLeftViewHolder(binding) as BaseViewHolder<ChatMessage>
            }
            else -> {
                val binding =
                    ListItemChatMessageRightBinding.inflate(parent.inflater(), parent, false)
                ChatRightViewHolder(binding) as BaseViewHolder<ChatMessage>
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ChatMessage>, position: Int) {
        mData?.let {
            holder.bind(it[position])
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
        }
    }
}


class ChatLeftViewHolder(val binding: ListItemChatMessageLeftBinding) :
    BaseViewHolder<ChatMessage.ChatLeft>(binding.root) {
    override fun bind(item: ChatMessage.ChatLeft) {
        binding.apply {
            tvMessage.text = item.data.messageData?.message
            tvDateTime.text = item.data.messageData?.dateTime?.dateTimeFormatter("hh:mm a")
        }
    }
}

class ChatRightViewHolder(val binding: ListItemChatMessageRightBinding) :
    BaseViewHolder<ChatMessage.ChatRight>(binding.root) {
    override fun bind(item: ChatMessage.ChatRight) {
        binding.apply {
            tvMessage.text = item.data.messageData?.message
            tvDateTime.text = item.data.messageData?.dateTime?.dateTimeFormatter("hh:mm a")
        }
    }

}

sealed class ChatMessage {
    class ChatLeft(val data: ChatData) : ChatMessage()
    class ChatRight(val data: ChatData) : ChatMessage()
}

data class ChatData(
    val messageType: String? = null,
    val messageData: MessageData? = null
)

data class MessageData(
    val id: String? = null,
    val message: String? = null,
    val dateTime: Long? = null
)

fun RecyclerView.smoothScrollToPos(position: Int) {
    Handler(Looper.getMainLooper()).postDelayed({
        this.smoothScrollToPosition(position)
    }, 300)
}