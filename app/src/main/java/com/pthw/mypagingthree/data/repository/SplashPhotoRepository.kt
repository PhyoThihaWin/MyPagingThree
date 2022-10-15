package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.paging.SplashPhotoPagingSource

interface SplashPhotoRepository {
    fun getSearchResults(query: String): SplashPhotoPagingSource
}