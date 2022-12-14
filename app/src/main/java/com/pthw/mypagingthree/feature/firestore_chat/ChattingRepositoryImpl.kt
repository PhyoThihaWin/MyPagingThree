package com.pthw.mypagingthree.feature.firestore_chat

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
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
    private var lastVisibleProduct: DocumentSnapshot? = null
    private var isLastProductReached = false

    override fun getChatListLiveData(): ChatListLiveData? {
        if (isLastProductReached) return null

        lastVisibleProduct?.let {
            query = query.startAfter(it)
        }

        return ChatListLiveData(query = query, mapper = mapper, repository = this)
    }

    override fun setLastVisibleProduct(lastVisibleProduct: DocumentSnapshot?) {
        this.lastVisibleProduct = lastVisibleProduct
    }

    override fun setLastProductReached(isLastProductReached: Boolean) {
        this.isLastProductReached = isLastProductReached
    }


}