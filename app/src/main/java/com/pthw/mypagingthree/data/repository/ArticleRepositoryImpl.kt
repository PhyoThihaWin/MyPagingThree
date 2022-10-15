package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.paging.ArticlePagingSource
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articlePagingSource: ArticlePagingSource
) : ArticleRepository {
    override fun articlePagingSource(): ArticlePagingSource {
        return articlePagingSource
    }
}