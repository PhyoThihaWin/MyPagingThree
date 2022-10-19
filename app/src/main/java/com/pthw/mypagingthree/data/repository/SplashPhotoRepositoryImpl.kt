package com.pthw.mypagingthree.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pthw.mypagingthree.data.api.SplashPhotoService
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.paging.source.SplashPhotoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 10

class SplashPhotoRepositoryImpl @Inject constructor(
    private val service: SplashPhotoService,
) : SplashPhotoRepository {

    override fun getSearchResults(query: String): Flow<PagingData<SplashPhoto>> {
        return  Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SplashPhotoPagingSource(query, service) }
        ).flow
    }

}