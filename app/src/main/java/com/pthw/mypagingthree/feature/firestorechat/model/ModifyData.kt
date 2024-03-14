package com.pthw.mypagingthree.feature.firestorechat.model

data class ModifyData(
    val oldIndex: Int,
    val newIndex: Int,
    val chatMessage: ChatMessage
)