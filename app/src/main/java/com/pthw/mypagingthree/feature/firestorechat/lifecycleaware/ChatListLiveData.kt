package com.pthw.mypagingthree.feature.firestorechat.lifecycleaware

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.pthw.domain.extension.orTrue
import com.pthw.domain.extension.orZero
import com.pthw.mypagingthree.feature.firestorechat.*
import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessage
import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessageMapper
import dev.onenex.heal.features.firestorechat.model.ChattingViewState
import com.pthw.mypagingthree.feature.firestorechat.model.MessageData
import com.pthw.mypagingthree.feature.firestorechat.repository.FirestoreRepository
import timber.log.Timber

/**
 * Created by P.T.H.W
 *
 * ChatListLiveData object will create everytime when you make pagination change on ChatScreen.
 * Therefore when you have 2 pages on screen, there will be 2 livedata objects
 * and they will listen and un-listen to snapshot by themselves.
 */
class ChatListLiveData(
    private val query: Query,
    private val documentList: MutableList<DocumentSnapshot>,
    private val userId: Long,
    private val mapper: ChatMessageMapper,
    private val repository: FirestoreRepository
) : LiveData<ChattingViewState>(), EventListener<QuerySnapshot> {

    /** register snapshot listener */
    var listenerRegistration: ListenerRegistration? = null
    var chatMessageList: MutableList<ChatMessage> = arrayListOf()

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null) {
            setValue(ChattingViewState.ErrorState(error.localizedMessage.orEmpty()))
            return
        }
        if (value == null) {
            Timber.w("Document changed count empty!")
            return
        }

        value.documentChanges.forEach { documentChange ->
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    /** Check old or new message */
                    val document = documentChange.document
                    if (documentList.findDocument(document.id) != null) return@forEach
                    if (chatMessageList.isNotEmpty() &&
                        (document.getTime() == null || document.getTime()
                            .orZero() > documentList.first().getTime().orZero())
                    ) {
                        documentList.add(0, documentChange.document)
                    } else documentList.add(documentChange.document)
                    Timber.w("Reached document added!! ${document.getTimestamp("time")?.seconds}")
                }
                DocumentChange.Type.MODIFIED -> {
                    val modifiedIndex = documentList.indexOfDocument(documentChange.document.id)
                    documentList.removeAtSafely(modifiedIndex)
                    documentList.add(modifiedIndex, documentChange.document)
                    Timber.w("Reached document modified!!")

                }
                DocumentChange.Type.REMOVED -> {
                    val document = documentChange.document
                    repository.checkDocumentExist(document.id) { isExist ->
                        if (!isExist) {
                            val removedIndex = documentList.indexOfDocument(document.id)
                            documentList.removeAtSafely(removedIndex)

                            /** Setup chat data every delete event make */
                            setUpChatData()
                            Timber.w("Reached document removed!!")
                        }
                    }
                }
            }

        }

        /** Setup chat data every event change */
        setUpChatData()

        val querySnapshotSize: Int = value.size()
        if (querySnapshotSize > 0) {
//            lastVisibleItem = value.documents[querySnapshotSize - 1]
            setValue(ChattingViewState.EmptyChatState(false))
        } else {
            setValue(ChattingViewState.EmptyChatState(true))
        }
        if (querySnapshotSize < CHAT_PAGE_SIZE) {
            repository.setLastProductReached(true)
            setValue(ChattingViewState.ReachedEndState)
        } else {
//            repository.setLastVisibleDocument(lastVisibleItem)
        }
    }

    /**
     * Register fire-store snapshot listener
     * on LiveData onActive
     */
    override fun onActive() {
        super.onActive()
        if (listenerRegistration == null)
            listenerRegistration = query.addSnapshotListener(this)
        Timber.i("ChatLiveData active state.")
    }

    /**
     * UnRegister fire-store snapsho listener on LiveData onInActive.
     * OnInActive will trigger when onPause(), onDestroy(),
     */
    override fun onInactive() {
        super.onInactive()
        listenerRegistration?.remove()
        listenerRegistration = null
        Timber.i("ChatLiveData inactive state.")
    }

    /**
     *  Handle error for fire-store model converting,
     *  Also make ChatList to GroupBy with Date
     */
    private fun setUpChatData() {
        runCatching {
            val tempList = documentList.map {
                it.toObject(
                    MessageData::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }.groupBy { it?.messageDate() }.entries

            chatMessageList.clear()
            tempList.forEach { entry ->
                entry.value.forEach {
                    it?.let {
                        chatMessageList.add(mapper.map(it))
                        /** Seen by userId added */
                        if (!it.read_by?.contains(userId.toString()).orTrue())
                            repository.makeDocumentSeen(userId, it)
                    }
                }
                chatMessageList.add(ChatMessage.ChatDate(entry.key.orEmpty()))
            }
            /** apply data to livedata state */
            value = ChattingViewState.SetMessageData(chatMessageList)
        }.getOrElse {
            /** apply error to livedata state */
            value = ChattingViewState.ErrorState(it.localizedMessage.orEmpty())
        }
    }

}