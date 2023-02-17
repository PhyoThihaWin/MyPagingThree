package dev.onenex.heal.features.firestorechat.model

import com.pthw.mypagingthree.feature.firestorechat.model.ChatMessage

sealed class ChattingViewState {
//    data class AddNewDataState(val data: ChatMessage) : ChattingViewState()
//    data class AddOldDataState(val data: ChatMessage) : ChattingViewState()
//    data class ModifyDataState(val modifyData: ChatMessage) : ChattingViewState()
//    data class RemoveDataState(val removedData: ChatMessage) : ChattingViewState()
    data class SetMessageData(val data: List<ChatMessage>) : ChattingViewState()
    data class ErrorState(val msg: String) : ChattingViewState()
    data class EmptyChatState(val isEmpty: Boolean) : ChattingViewState()
    object ReachedEndState : ChattingViewState()

}
