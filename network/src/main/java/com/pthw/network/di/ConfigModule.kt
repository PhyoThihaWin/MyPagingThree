package com.pthw.network.di

import com.pthw.data.config.UrlStringProvider
import com.pthw.network.config.UrlStringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {
    @Binds
    abstract fun bindUrlStringProvider(urlStringProviderImpl: UrlStringProviderImpl): UrlStringProvider

}