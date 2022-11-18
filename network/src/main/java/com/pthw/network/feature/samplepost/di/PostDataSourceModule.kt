package com.pthw.network.feature.samplepost.di

import com.pthw.data.feature.samplepost.datasource.PostNetworkDSource
import com.pthw.data.feature.splashimage.datasource.SpPhotoNetworkDSource
import com.pthw.network.feature.samplepost.datasource_impl.PostNetworkDSourceImpl
import com.pthw.network.feature.splashimage.datasource_impl.SpPhotoNetworkDSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PostDataSourceModule {

    @Binds
    abstract fun bindPostNetworkDataSource(
        postNetworkDSourceImpl: PostNetworkDSourceImpl
    ): PostNetworkDSource

}