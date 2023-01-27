package com.pthw.mypagingthree.feature.firestore_chat

import androidx.lifecycle.ViewModel
import com.pthw.mypagingthree.feature.firestore_chat.ChattingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val repository: ChattingRepository
) : ViewModel() {


    fun getChatListLiveData() = repository.getChatListLiveData()
    fun getListenerRegistration() = repository.getListenerRegistration()
}