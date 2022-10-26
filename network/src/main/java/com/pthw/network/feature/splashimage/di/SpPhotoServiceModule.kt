package com.pthw.network.feature.splashimage.di

import com.pthw.network.feature.splashimage.service.SplashPhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SpPhotoServiceModule {

    @Provides
    fun providePhotoService(retrofit: Retrofit): SplashPhotoService {
        return retrofit.create(SplashPhotoService::class.java)
    }

}