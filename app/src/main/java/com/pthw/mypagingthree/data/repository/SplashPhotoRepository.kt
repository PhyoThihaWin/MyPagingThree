package com.pthw.mypagingthree.data.repository

import androidx.paging.PagingData
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import kotlinx.coroutines.flow.Flow

interface SplashPhotoRepository {
    fun getSearchResults(query: String): Flow<PagingData<SplashPhoto>>
}