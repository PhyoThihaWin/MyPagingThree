package com.pthw.mypagingthree.feature.firestorechat.repository

import com.pthw.mypagingthree.feature.firestorechat.lifecycleaware.ChatListLiveData
import com.pthw.mypagingthree.feature.firestorechat.model.MessageData

interface FirestoreRepository {
    suspend fun getChatListLiveData(userId: Long): ChatListLiveData?

    fun setLastProductReached(isLastItemReached: Boolean) // flag for paging end

//    fun destroySnapshot() // call this on viewModel onCleared()

    fun setAppointmentId(appointmentId: String)

    fun checkDocumentExist(id: String, isExist: (Boolean) -> Unit)

    fun makeDocumentSeen(userId: Long, data: MessageData)
}