package com.pthw.network.feature.splashimage.di

import com.pthw.data.feature.splashimage.datasource.SpPhotoNetworkDSource
import com.pthw.network.feature.splashimage.datasource_impl.SpPhotoNetworkDSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SpPhotoDataSourceModule {

    @Binds
    abstract fun bindSpPhotoNetworkDataSource(
        spPhotoNetworkDataSourceImpl: SpPhotoNetworkDSourceImpl
    ): SpPhotoNetworkDSource

}