package com.pthw.mypagingthree.feature.firestorechat.lifecycleaware

import androidx.lifecycle.viewModelScope
import com.pthw.appbase.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.pthw.mypagingthree.feature.firestorechat.ChatNotifySetting
import com.pthw.mypagingthree.feature.firestorechat.repository.FirestoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val chatNotifySetting: ChatNotifySetting,
) : BaseViewModel() {

    /** User Data */
    var userId: Long = 123456
    var readBy: List<String> = emptyList()

//    init {
//        viewModelScope.launch {
//            authRepository.getUserPreference().collectLatest {
//                userId = it.userId
//                user = it
//                readBy = arrayListOf(it.userId.toString())
//            }
//        }
//    }

//    fun updateChatNotifySetting(on: Boolean) {
//        chatNotifySetting.enabledNotification = on
//    }
//
//    fun notifyMessage(chatId: String, message: String) {
//        viewModelScope.launch {
//            runCatching {
//                postMessage.execute(TwoParams(chatId, message))
//            }.getOrElse {
//                Timber.e(it)
//            }
//        }
//    }

    fun setAppointmentId(appointmentId: String) {
        repository.setAppointmentId(appointmentId)
    }

    fun getChatListLiveData(onChange: (ChatListLiveData?) -> Unit) {
        viewModelScope.launch {
            onChange.invoke(repository.getChatListLiveData(userId))
        }
    }


//    override fun onCleared() {
//        super.onCleared()
//        repository.destroySnapshot()
//        Timber.e("Reached ViewModel cleared!")
//    }


    /** ChatFileUpload Repository */
//    private val _cfList: MutableStateFlow<ListViewState<List<ChatFile>>> =
//        MutableStateFlow(ListViewState.Idle())
//    val chatFilesFlow = _cfList.asStateFlow()
//
//    fun uploadChatFile(files: List<String>, chatId: String) {
//        _cfList.value = ListViewState.Loading()
//        viewModelScope.launch {
//            runCatching {
//                val data = chatFileUpload.execute(TwoParams(files, chatId))
//                _cfList.value = ListViewState.Success(data)
//            }.getOrElse {
//                Timber.e(it)
//                _cfList.value = ListViewState.Error(exception.map(it))
//            }
//        }
//    }

    /** User Presence State (Active/InActive) */
//    private fun userPresenceState() {
//        val userLastOnlineRef =
//            Firebase.database.getReference(PARENT_NODE).child(userId.toString())
//        val connectedRef = Firebase.database.getReference(CONNECTION_NODE)
//        connectedRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val connected = snapshot.getValue<Boolean>() ?: false
//                if (!connected) return
//                userLastOnlineRef.onDisconnect().setValue(ChatPresenceState.Offline.value)
//                    .addOnSuccessListener { Timber.i("UserState offline!!") }
//                userLastOnlineRef.setValue(ChatPresenceState.Online.value)
//                    .addOnSuccessListener { Timber.i("UserState online..") }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) = Timber.e(error.toException())
//        })
//    }


}
