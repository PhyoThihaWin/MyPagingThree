package com.pthw.cache.di

import com.pthw.cache.config.AuthStoreProviderImpl
import com.pthw.cache.config.BaseUrlProviderImpl
import com.pthw.cache.config.LanguageCacheDataSourceImpl
import com.pthw.data.config.AuthStoreProvider
import com.pthw.data.config.BaseUrlProvider
import com.pthw.data.config.LanguageCacheDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {
    @Binds
    abstract fun bindAuthStoreProvider(authStoreProviderImpl: AuthStoreProviderImpl): AuthStoreProvider

    @Binds
    abstract fun bindBaseUrlProvider(baseUrlProviderImpl: BaseUrlProviderImpl): BaseUrlProvider

    @Binds
    abstract fun bindLanguageCacheDataSource(languageCacheDataSourceImpl: LanguageCacheDataSourceImpl): LanguageCacheDataSource

}