package com.pthw.appbase.core.viewstate

sealed class MyViewState<out T> {
    open operator fun invoke(): T? = null

    class Idle<out T> : MyViewState<T>()
    data class Loading<out T>(val value: T) : MyViewState<T>()
    data class Success<out T>(val value: T) : MyViewState<T>()
    data class Error<out T>(val errorMessage: String) : MyViewState<T>()
}

fun <T> MyViewState<T>.renderState(
    idle: () -> Unit = {},
    loading: () -> Unit = {},
    success: (T) -> Unit,
    error: (String) -> Unit = {},
) {
    when (this) {
        is MyViewState.Loading -> {
            loading.invoke()
        }
        is MyViewState.Success -> {
            success.invoke(this.value)
        }
        is MyViewState.Error -> {
            error.invoke(this.errorMessage)
        }
        else -> idle.invoke()
    }
}