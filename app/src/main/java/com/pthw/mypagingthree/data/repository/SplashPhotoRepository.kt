package com.pthw.mypagingthree.data.repository

import com.pthw.mypagingthree.paging.source.SplashPhotoPagingSource


interface SplashPhotoRepository {
    fun getSearchResults(query: String): SplashPhotoPagingSource
}