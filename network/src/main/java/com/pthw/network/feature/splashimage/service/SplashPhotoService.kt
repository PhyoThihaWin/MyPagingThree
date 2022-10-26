package com.pthw.network.feature.splashimage.service

import com.pthw.network.config.DataPageResponse
import com.pthw.network.feature.splashimage.response.SplashPhotoSource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SplashPhotoService {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<DataPageResponse<SplashPhotoSource>>

}