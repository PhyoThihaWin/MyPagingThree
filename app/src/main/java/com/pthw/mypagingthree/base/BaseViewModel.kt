package com.pthw.mypagingthree.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel<E>() : ViewModel() {

    //emit as an event
    private val _shareFlow = MutableSharedFlow<E>()
    val eventLD: LiveData<E> = _shareFlow.asLiveData()

//    private val _shareFlowError = MutableSharedFlow<String>()
//    val eventErrorLD: LiveData<String> = _shareFlowError.asLiveData()
//
//    protected val exceptionHandler = CoroutineExceptionHandler { c, throwable ->
//        val errorMessage = when (throwable) {
//            is UnknownHostException -> {
//                "connection problem"
//            }
//            is SocketTimeoutException -> {
//                "connection timeout"
//            }
//            is ConnectException -> {
//                "no network available"
//            }
//            else -> {
//                throwable.localizedMessage ?: "Something went wrong."
//            }
//        }
//        viewModelScope.launch {
//            _shareFlowError.emit(errorMessage)
//        }
//    }


    /**
     * emit one time events to the view
     * */
    protected fun emit(event: E) {

        if (shouldEmit(event)) {
            viewModelScope.launch {
                _shareFlow.emit(event)
            }
        }
    }

    /**
     * override this to skip a particular event type
     */
    protected open fun shouldEmit(event: E): Boolean {
        return true
    }

}