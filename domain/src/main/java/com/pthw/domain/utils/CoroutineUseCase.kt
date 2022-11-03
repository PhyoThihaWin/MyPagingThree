package com.pthw.domain.utils

import kotlinx.coroutines.withContext

/**
 * Created by Vincent on 2019-10-21
 */

abstract class CoroutineUseCase<I, O> constructor(protected val dispatcherProvider: DispatcherProvider) {
    suspend fun execute(params: I): O {
        return withContext(dispatcherProvider.io()) {
            provide(params)
        }
    }

    protected abstract suspend fun provide(params: I): O
}

abstract class CoroutineUseCaseParams<I, O> constructor(protected val dispatcherProvider: DispatcherProvider) {
    suspend fun execute(vararg params: I): O {
        return withContext(dispatcherProvider.io()) {
            provide(*params)
        }
    }

    protected abstract suspend fun provide(vararg params: I): O
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.itsType(): T {
    return this as T
}