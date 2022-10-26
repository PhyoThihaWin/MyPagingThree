package com.pthw.appbase.core.viewstate

sealed class ListViewState<out T> {
    open operator fun invoke(): T? = null

    class Idle<out T> : ListViewState<T>()
    class Loading<out T> : ListViewState<T>()
    data class Success<out T>(val value: T) : ListViewState<T>()
    data class Error<out T>(val errorMessage: String) : ListViewState<T>()
    class NoMoreContent<out T> : ListViewState<T>()
}
