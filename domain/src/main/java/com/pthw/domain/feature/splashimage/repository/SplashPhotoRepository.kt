package com.pthw.domain.feature.splashimage.repository

import androidx.paging.PagingData
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import kotlinx.coroutines.flow.Flow

interface SplashPhotoRepository {
    fun getSearchResults(query: String): Flow<PagingData<SplashPhoto>>
}