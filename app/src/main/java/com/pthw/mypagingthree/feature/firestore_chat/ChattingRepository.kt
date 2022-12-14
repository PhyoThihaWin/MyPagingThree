package com.pthw.mypagingthree.feature.firestore_chat

import com.google.firebase.firestore.DocumentSnapshot

interface ChattingRepository {
    fun getChatListLiveData(): ChatListLiveData?
    fun setLastVisibleProduct(lastVisibleProduct: DocumentSnapshot?)
    fun setLastProductReached(isLastProductReached: Boolean)
}