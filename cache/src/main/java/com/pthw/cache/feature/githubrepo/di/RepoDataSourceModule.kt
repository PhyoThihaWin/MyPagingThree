package com.pthw.cache.feature.githubrepo.di

import com.pthw.cache.feature.githubrepo.datasource_impl.RepoCacheDSourceImpl
import com.pthw.data.feature.githubrepo.datasource.RepoCacheDSource
import com.pthw.data.feature.splashimage.datasource.SpPhotoNetworkDSource
import com.pthw.network.feature.splashimage.datasource_impl.SpPhotoNetworkDSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoDataSourceModule {

    @Binds
    abstract fun bindRepoCacheDataSource(
        repoCacheDSourceImpl: RepoCacheDSourceImpl
    ): RepoCacheDSource

}