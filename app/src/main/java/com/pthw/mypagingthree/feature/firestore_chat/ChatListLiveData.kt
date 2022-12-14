package com.pthw.mypagingthree.feature.firestore_chat

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import timber.log.Timber

class ChatListLiveData(
    private val query: Query,
    private val mapper: ChatMessageMapper,
    private val repository: ChattingRepository
) : LiveData<ChattingViewState>(), EventListener<QuerySnapshot> {

    private var listenerRegistration: ListenerRegistration? = null
    private var lastVisibleProduct: DocumentSnapshot? = null

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null) return
        if (value == null) return

        for (documentChange in value.documentChanges) {
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    val addedData = documentChange.document.toObject(ChatData::class.java)
                    val viewState = if (documentChange.newIndex == 0 && lastVisibleProduct != null)
                        ChattingViewState.AddNewDataState(mapper.map(addedData))
                    else ChattingViewState.AddOldDataState(mapper.map(addedData))

                    Timber.w("Reached added old item, ${addedData}")
                    setValue(viewState)
                }
                DocumentChange.Type.MODIFIED -> {
                    val modifiedData = documentChange.document.toObject(ChatData::class.java)
                    val viewState = ChattingViewState.ModifyDataState(mapper.map(modifiedData))
                    setValue(viewState)
                }
                DocumentChange.Type.REMOVED -> {
                    val removedData = documentChange.document.toObject(ChatData::class.java)
                    val viewState = ChattingViewState.RemoveDataState(mapper.map(removedData))
                    setValue(viewState)
                }
            }
        }

        val querySnapshotSize: Int = value.size()
        if (querySnapshotSize < CHAT_PAGE_SIZE) {
            repository.setLastProductReached(true)
        } else {
            lastVisibleProduct = value.documents[querySnapshotSize - 1]
            repository.setLastVisibleProduct(lastVisibleProduct)
        }
    }

    override fun onActive() {
        super.onActive()
        listenerRegistration = query.addSnapshotListener(this)

    }

    override fun onInactive() {
        super.onInactive()
        listenerRegistration?.remove()

    }
}