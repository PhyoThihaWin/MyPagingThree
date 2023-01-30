package com.pthw.mypagingthree.feature.firestore_chat

import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class ChatMessageMapper @Inject constructor() :
    UnidirectionalMap<ChatData, ChatMessage> {
    override fun map(item: ChatData): ChatMessage {
        return if (item.messageData?.id != "") ChatMessage.ChatRight(item)
        else ChatMessage.ChatLeft(item)
    }
}
