package com.pthw.mypagingthree.feature.firestorechat.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.pthw.domain.AppConstants.LOCAL_PHARMACY_DATE_FORMAT
import com.pthw.domain.AppConstants.LOCAL_TIME_FORMAT
import com.pthw.mypagingthree.feature.firestorechat.epochChatFormatter
import com.pthw.mypagingthree.feature.firestorechat.epochFormatter

sealed class ChatMessage {
    class ChatLeft(val data: MessageData) : ChatMessage()
    class ChatRight(val data: MessageData) : ChatMessage()
    class ChatDate(val date: String) : ChatMessage()
}

data class MessageData(
    @DocumentId val id: String? = null,
    @ServerTimestamp val time: Timestamp? = null,
    val attachments: List<Attachment>? = null,
    val read_by: List<String>? = null,
    val message: String? = null,
    val sender_id: String? = null,
    val sender_name: String? = null,
) {
    /** Use this if you want date, eg: 20 Aug 2022 */
    fun messageDate() = time?.seconds?.epochFormatter(LOCAL_PHARMACY_DATE_FORMAT).orEmpty()

    /** Use this if you want date, eg: Today, Yesterday, 20 Aug 2022 */
    fun messageLastDate() = time?.seconds?.epochChatFormatter(LOCAL_PHARMACY_DATE_FORMAT).orEmpty()

    /** Use this if you want time, eg: 12:56 PM */
    fun messageTime() = time?.seconds?.epochFormatter(LOCAL_TIME_FORMAT).orEmpty()
}

data class Attachment(
    val type: String? = null,
    val url: String? = null,
    val name: String? = null
)