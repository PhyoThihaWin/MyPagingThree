package com.pthw.appbase.core.viewstate

sealed class ViewState<out T> {
    open operator fun invoke(): T? = null
    data class Nothing<out T>(val value: T) : ViewState<T>()
    data class Success<out T>(val value: T) : ViewState<T>()
    data class Error<out T>(/*val exception: Throwable,*/ val errorMessage: String) : ViewState<T>()
}
