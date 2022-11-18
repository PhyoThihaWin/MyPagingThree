package com.pthw.appbase.core.viewstate

sealed class ListViewState<out T> {
    open operator fun invoke(): T? = null

    class Idle<out T> : ListViewState<T>()
    class Loading<out T> : ListViewState<T>()
    data class Success<out T>(val value: T) : ListViewState<T>()
    data class Error<out T>(val errorMessage: String) : ListViewState<T>()
    class NoMoreContent<out T> : ListViewState<T>()
}

fun <T> ListViewState<T>.renderState(
    idle: (() -> Unit)? = null,
    loading: (() -> Unit)? = null,
    success: ((T) -> Unit)? = null,
    error: ((String) -> Unit)? = null,
    noMore: (() -> Unit)? = null
) {
    when (this) {
        is ListViewState.Loading -> {
            loading?.invoke()
        }
        is ListViewState.Success -> {
            success?.invoke(this.value)
        }
        is ListViewState.Error -> {
            error?.invoke(this.errorMessage)
        }
        is ListViewState.NoMoreContent -> {
            noMore?.invoke()
        }
        else -> idle?.invoke()
    }
}