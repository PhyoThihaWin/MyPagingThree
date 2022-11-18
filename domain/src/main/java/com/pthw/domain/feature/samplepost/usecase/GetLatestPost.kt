package com.pthw.domain.feature.samplepost.usecase

import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.domain.feature.samplepost.repository.PostRepository
import com.pthw.domain.utils.*
import javax.inject.Inject

class GetLatestPost @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: PostRepository,
) : CoroutineUseCase<Unit, List<Post>>(dispatcherProvider) {
    override suspend fun provide(params: Unit): List<Post> {
        return repository.getLatestPost()
    }
}