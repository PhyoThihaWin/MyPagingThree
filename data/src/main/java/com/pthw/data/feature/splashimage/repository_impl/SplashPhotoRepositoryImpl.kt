package com.pthw.data.feature.splashimage.repository_impl

import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.feature.splashimage.datasource.SpPhotoNetworkDSource
import com.pthw.data.feature.splashimage.mapper.SpPhotoEntityMapper
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import com.pthw.domain.feature.splashimage.repository.SplashPhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SplashPhotoRepositoryImpl @Inject constructor(
    private val spPhotoNetworkDSource: SpPhotoNetworkDSource,
    private val mapper: SpPhotoEntityMapper
) : SplashPhotoRepository {

    override fun getSearchResults(query: String): Flow<PagingData<SplashPhoto>> {
        return spPhotoNetworkDSource.getSearchResults(query).map { pagingData ->
            pagingData.map { mapper.map(it) }
        }
    }

}