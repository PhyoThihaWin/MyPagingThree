package com.pthw.data.feature.splashimage.datasource

import androidx.paging.PagingData
import com.pthw.data.feature.splashimage.entity.SplashPhotoEntity
import kotlinx.coroutines.flow.Flow

interface SpPhotoNetworkDSource {
    fun getSearchResults(query: String): Flow<PagingData<SplashPhotoEntity>>

}