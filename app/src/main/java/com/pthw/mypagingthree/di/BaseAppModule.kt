package com.pthw.mypagingthree.di

import com.pthw.mypagingthree.utils.DefaultDispatcherProvider
import com.pthw.mypagingthree.utils.DispatcherProvider
import com.pthw.mypagingthree.utils.exception.ExceptionToStringMapper
import com.pthw.mypagingthree.utils.exception.ExceptionToStringMapperImpl
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

