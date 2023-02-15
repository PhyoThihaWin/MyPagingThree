package com.pthw.appbase.core.viewstate

sealed class ViewState<out T> {
    open operator fun invoke(): T? = null

    class Idle<out T> : ViewState<T>()
    data class Loading<out T>(val value: T) : ViewState<T>()
    data class Success<out T>(val value: T) : ViewState<T>()
    data class Error<out T>(val errorMessage: String) : ViewState<T>()
}

fun <T> ViewState<T>.renderState(
    idle: (() -> Unit) = {},
    loading: (() -> Unit) = {},
    error: ((msg: String) -> Unit) = {},
    success: ((data: T) -> Unit),
) {
    when (this) {
        is ViewState.Loading -> {
            loading.invoke()
        }
        is ViewState.Success -> {
            success.invoke(this.value)
        }
        is ViewState.Error -> {
            error.invoke(this.errorMessage)
        }
        else -> idle.invoke()
    }
}