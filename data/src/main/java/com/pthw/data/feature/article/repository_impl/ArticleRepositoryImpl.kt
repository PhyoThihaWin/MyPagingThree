package com.pthw.data.feature.article.repository_impl

import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.feature.article.datasource.ArticleCacheDSource
import com.pthw.data.feature.article.mapper.ArticleEntityMapper
import com.pthw.domain.feature.article.model.Article
import com.pthw.domain.feature.article.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleCacheDSource: ArticleCacheDSource,
    private val mapper: ArticleEntityMapper
) : ArticleRepository {
    override fun getArticles(): Flow<PagingData<Article>> {
        return articleCacheDSource.getArticles().map { pagingData ->
            pagingData.map { mapper.map(it) }
        }
    }

}