package com.pthw.network.feature.splashimage.datasource_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.feature.splashimage.datasource.SpPhotoNetworkDSource
import com.pthw.data.feature.splashimage.entity.SplashPhotoEntity
import com.pthw.network.feature.splashimage.mapper.SpPhotoNetworkToDataMapper
import com.pthw.network.feature.splashimage.pagingsource.SplashPhotoPagingSource
import com.pthw.network.feature.splashimage.service.SplashPhotoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

class SpPhotoNetworkDSourceImpl @Inject constructor(
    private val service: SplashPhotoService,
    private val mapper: SpPhotoNetworkToDataMapper
) : SpPhotoNetworkDSource {
    override fun getSearchResults(query: String): Flow<PagingData<SplashPhotoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SplashPhotoPagingSource(query, service) }
        ).flow.map { pagingData ->
            pagingData.map { mapper.map(it) }
        }
    }
}