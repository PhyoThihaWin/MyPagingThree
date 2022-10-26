package com.pthw.cache.feature.article.datasource_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.cache.feature.article.mapper.ArticleCacheToDataMapper
import com.pthw.data.feature.article.datasource.ArticleCacheDSource
import com.pthw.data.feature.article.entity.ArticleEntity
import com.pthw.cache.feature.article.pagingsource.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 50

class ArticleCacheDSourceImpl @Inject constructor(
    private val mapper: ArticleCacheToDataMapper
) : ArticleCacheDSource {
    override fun getArticles(): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = false,
                initialLoadSize = ITEMS_PER_PAGE * 2
            ),
            pagingSourceFactory = { ArticlePagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { mapper.map(it) }
        }
    }
}