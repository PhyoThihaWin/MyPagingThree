package com.pthw.domain.feature.samplepost.usecase

import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.repository.PostRepository
import com.pthw.domain.utils.*
import javax.inject.Inject

class GetPostDetail @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: PostRepository,
) : CoroutineUseCase<TwoParams<Int, String>, List<Comment>>(dispatcherProvider) {
    override suspend fun provide(params: TwoParams<Int, String>): List<Comment> {
        val postId = params.one
        val testString = params.two
        return repository.getPostDetail(postId)
    }
}
