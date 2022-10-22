package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.data.api.GithubService
import com.pthw.mypagingthree.data.api.SplashPhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    fun providePhotoService(retrofit: Retrofit): SplashPhotoService {
        return retrofit.create(SplashPhotoService::class.java)
    }

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return  retrofit.create(GithubService::class.java)
    }
}