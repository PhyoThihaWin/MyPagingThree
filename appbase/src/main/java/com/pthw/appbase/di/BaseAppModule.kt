package com.pthw.appbase.di

import com.pthw.appbase.exception.ExceptionToStringMapper
import com.pthw.appbase.exception.ExceptionToStringMapperImpl
import com.pthw.appbase.utils.DefaultDispatcherProvider
import com.pthw.domain.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BaseAppModule {
    @Binds
    @Singleton
    abstract fun exceptionToStringMapper(exceptionToStringMapperImpl: ExceptionToStringMapperImpl): ExceptionToStringMapper

    @Binds
    abstract fun dispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}

