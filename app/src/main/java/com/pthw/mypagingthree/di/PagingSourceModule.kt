package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.paging.ArticlePagingSource
import com.pthw.mypagingthree.paging.SplashPhotoPagingSource
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
