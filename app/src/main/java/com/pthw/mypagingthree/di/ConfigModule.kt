package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.config.AuthStoreProvider
import com.pthw.mypagingthree.config.AuthStoreProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {

    @Binds
    abstract fun bindAuthStoreProvider(authStoreProviderImpl: AuthStoreProviderImpl): AuthStoreProvider
}