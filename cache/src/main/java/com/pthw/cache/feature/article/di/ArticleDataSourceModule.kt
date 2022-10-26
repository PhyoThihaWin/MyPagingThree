package com.pthw.cache.feature.article.di

import com.pthw.data.feature.article.datasource.ArticleCacheDSource
import com.pthw.cache.feature.article.datasource_impl.ArticleCacheDSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArticleDataSourceModule {

    @Binds
    abstract fun bindArticleCacheDataSource(
        articleCacheDSourceImpl: ArticleCacheDSourceImpl
    ): ArticleCacheDSource

}