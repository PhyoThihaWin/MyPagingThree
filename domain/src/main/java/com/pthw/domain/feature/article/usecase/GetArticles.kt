package com.pthw.domain.feature.article.usecase

import androidx.paging.PagingData
import com.pthw.domain.feature.article.model.Article
import com.pthw.domain.feature.article.repository.ArticleRepository
import com.pthw.domain.utils.CoroutineUseCase
import com.pthw.domain.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticles @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: ArticleRepository
) : CoroutineUseCase<Unit, Flow<PagingData<Article>>>(dispatcherProvider) {
    override suspend fun provide(params: Unit): Flow<PagingData<Article>> {
        return repository.getArticles()
    }
}