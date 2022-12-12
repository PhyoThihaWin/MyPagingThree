package com.pthw.domain.feature.samplepost.usecase

import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.repository.PostRepository
import com.pthw.domain.utils.CoroutineUseCaseParams
import com.pthw.domain.utils.DispatcherProvider
import com.pthw.domain.utils.number
import javax.inject.Inject

class GetPostDetail @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: PostRepository,
) : CoroutineUseCaseParams<Any, List<Comment>>(dispatcherProvider) {
    override suspend fun provide(vararg params: Any): List<Comment> {
        val postId = params[0].number().toInt()
        return repository.getPostDetail(postId)
    }
}
