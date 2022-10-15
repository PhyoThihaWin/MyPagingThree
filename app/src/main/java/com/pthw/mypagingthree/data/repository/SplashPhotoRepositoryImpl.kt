package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.data.api.SplashPhotoService
import com.pthw.mypagingthree.paging.source.SplashPhotoPagingSource
import javax.inject.Inject

class SplashPhotoRepositoryImpl @Inject constructor(
    private val service: SplashPhotoService
) : SplashPhotoRepository {

    override fun getSearchResults(query: String): SplashPhotoPagingSource {
        return SplashPhotoPagingSource(service, query)
    }

}