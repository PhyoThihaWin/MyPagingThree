package com.pthw.mypagingthree.feature.firestore_chat

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import timber.log.Timber
import javax.inject.Inject

class ChattingRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
    private val mapper: ChatMessageMapper
) : ChattingRepository {
    private val productsRef = firestore.collection(PARENT_COLLECTION).document("appointment_1")
    private var query = productsRef.collection(SUB_COLLECTION)
        .orderBy("messageData.dateTime", Query.Direction.DESCENDING).limit(CHAT_PAGE_SIZE.toLong())
    private var lastVisibleItem: DocumentSnapshot? = null
    private var isLastItemReached = false
    private var lr: ListenerRegistration? = null

    override fun getChatListLiveData(): ChatListLiveData? {
        if (isLastItemReached) return null

        lastVisibleItem?.let {
            query = query.startAfter(it)
        }

        return ChatListLiveData(query = query, mapper = mapper, repository = this)
    }

    override fun getListenerRegistration() = lr

    override fun setLastVisibleProduct(lastVisibleItem: DocumentSnapshot?) {
        this.lastVisibleItem = lastVisibleItem
    }

    override fun setLastProductReached(isLastItemReached: Boolean) {
        this.isLastItemReached = isLastItemReached
    }

    override fun setListenerRegistration(registration: ListenerRegistration?) {
        this.lr = registration
    }


}