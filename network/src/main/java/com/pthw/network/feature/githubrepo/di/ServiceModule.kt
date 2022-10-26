package com.pthw.network.feature.githubrepo.di

import com.pthw.network.feature.githubrepo.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

//    @Provides
//    fun providePhotoService(retrofit: Retrofit): com.pthw.network.feature.article.service.SplashPhotoService {
//        return retrofit.create(com.pthw.network.feature.article.service.SplashPhotoService::class.java)
//    }

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}