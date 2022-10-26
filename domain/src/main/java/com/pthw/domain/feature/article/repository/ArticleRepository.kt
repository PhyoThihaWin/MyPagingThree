package com.pthw.domain.feature.article.repository

import androidx.paging.PagingData
import com.pthw.domain.feature.article.model.Article
import kotlinx.coroutines.flow.Flow


interface ArticleRepository {
    fun getArticles(): Flow<PagingData<Article>>
}