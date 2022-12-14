package com.pthw.mypagingthree.feature.firestore_chat

sealed class ChattingViewState {
    data class AddNewDataState(val data: ChatMessage) : ChattingViewState()
    data class AddOldDataState(val data: ChatMessage) : ChattingViewState()
    data class ModifyDataState(val data: ChatMessage) : ChattingViewState()
    data class RemoveDataState(val data: ChatMessage) : ChattingViewState()
    data class ErrorState(val msg: String)
}
