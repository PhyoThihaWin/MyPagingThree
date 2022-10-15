package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.data.repository.ArticleRepository
import com.pthw.mypagingthree.data.repository.ArticleRepositoryImpl
import com.pthw.mypagingthree.data.repository.SplashPhotoRepository
import com.pthw.mypagingthree.data.repository.SplashPhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    @Binds
    abstract fun bindImageRepository(splashPhotoRepositoryImpl: SplashPhotoRepositoryImpl): SplashPhotoRepository
}