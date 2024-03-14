package com.pthw.mypagingthree.feature.firestorechat.model

import com.pthw.appbase.exception.UnidirectionalMap
import javax.inject.Inject

class ChatMessageMapper @Inject constructor() : UnidirectionalMap<MessageData, ChatMessage> {

    private var userId: Long = 0L

    override fun map(item: MessageData): ChatMessage {
        //check if userid is own id ,show message at right side.
        return if (item.sender_id == userId.toString())
            ChatMessage.ChatRight(item)
        else ChatMessage.ChatLeft(item)
    }

    fun setUserId(userId: Long) {
        this.userId = userId
    }
}