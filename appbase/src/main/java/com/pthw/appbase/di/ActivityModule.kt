package com.pthw.appbase.di

import com.pthw.appbase.utils.connectivity.ConnectivityObserver
import com.pthw.appbase.utils.connectivity.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityModule {
    @Binds
    abstract fun bindNetworkConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver
}