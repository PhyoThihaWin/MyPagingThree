package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.paging.source.ArticlePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PagingSourceModule {

    @Provides
    fun provideArticlePagingSource(): ArticlePagingSource = ArticlePagingSource()

}
