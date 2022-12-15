package com.pthw.mypagingthree.feature.firestore_chat

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration

interface ChattingRepository {
    fun getChatListLiveData(): ChatListLiveData?
    fun getListenerRegistration(): ListenerRegistration?

    fun setLastVisibleProduct(lastVisibleItem: DocumentSnapshot?)
    fun setLastProductReached(isLastItemReached: Boolean)
    fun setListenerRegistration(registration: ListenerRegistration?)
}