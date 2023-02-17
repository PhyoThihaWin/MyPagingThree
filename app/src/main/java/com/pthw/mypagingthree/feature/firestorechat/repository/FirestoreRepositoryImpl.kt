package com.pthw.mypagingthree.feature.firestorechat.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pthw.mypagingthree.feature.firestorechat.*
import com.pthw.mypagingthree.feature.firestorechat.lifecycleaware.ChatListLiveData
import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessageMapper
import com.pthw.mypagingthree.feature.firestorechat.model.MessageData
import timber.log.Timber
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: ChatMessageMapper
) : FirestoreRepository {

    private lateinit var chatDocumentRef: DocumentReference
    private lateinit var query: Query

    override fun setAppointmentId(appointmentId: String) {
        chatDocumentRef = firestore
            .collection(PARENT_COLLECTION)
            .document(appointmentId)
        query = chatDocumentRef
            .collection(SUB_COLLECTION)
            .orderBy(TIME_KEY, Query.Direction.DESCENDING)
            .limit(CHAT_PAGE_SIZE.toLong())
    }

    private var isLastItemReached = false
    private var chatListLiveData: ChatListLiveData? = null
    private var documentList: MutableList<DocumentSnapshot> = arrayListOf()

    override suspend fun getChatListLiveData(userId: Long): ChatListLiveData? {
        if (isLastItemReached) return null

        if (documentList.isNotEmpty()) {
            query = query.startAfter(documentList.last())
        }

        mapper.setUserId(userId)
        chatListLiveData = ChatListLiveData(
            query = query,
            documentList = documentList,
            userId = userId,
            mapper = mapper,
            repository = this
        )
        return chatListLiveData
    }

    override fun setLastProductReached(isLastItemReached: Boolean) {
        this.isLastItemReached = isLastItemReached
    }

    override fun checkDocumentExist(id: String, isExist: (Boolean) -> Unit) {
        chatDocumentRef.collection(SUB_COLLECTION).document(id)
            .get()
            .addOnSuccessListener {
                isExist.invoke(it.exists())
            }
    }

    override fun makeDocumentSeen(userId: Long, data: MessageData) {
        val readBy = data.read_by.orEmpty().toMutableList()
        readBy.add(userId.toString())
        chatDocumentRef.collection(SUB_COLLECTION).document(data.id.orEmpty())
            .update(READ_BY_KEY, readBy)
        Timber.i("Make seen at : ${data.id}, ${data.message}")
    }

}