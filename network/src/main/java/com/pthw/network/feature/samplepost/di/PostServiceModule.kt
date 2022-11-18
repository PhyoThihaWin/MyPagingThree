package com.pthw.network.feature.samplepost.di

import com.pthw.network.feature.samplepost.service.PostService
import com.pthw.network.feature.splashimage.service.SplashPhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PostServiceModule {

    @Provides
    fun providePostService(retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

}