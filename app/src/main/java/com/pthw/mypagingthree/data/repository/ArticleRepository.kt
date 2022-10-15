package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.paging.ArticlePagingSource

interface ArticleRepository {
    fun articlePagingSource(): ArticlePagingSource
}