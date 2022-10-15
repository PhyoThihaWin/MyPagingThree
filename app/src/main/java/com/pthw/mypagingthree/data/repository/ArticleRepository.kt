package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.paging.source.ArticlePagingSource


interface ArticleRepository {
    fun articlePagingSource(): ArticlePagingSource
}