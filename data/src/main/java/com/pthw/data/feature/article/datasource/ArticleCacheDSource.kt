package com.pthw.data.feature.article.datasource

import androidx.paging.PagingData
import com.pthw.data.feature.article.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleCacheDSource {
    fun getArticles(): Flow<PagingData<ArticleEntity>>
}