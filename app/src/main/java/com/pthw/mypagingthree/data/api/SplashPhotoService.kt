package com.pthw.mypagingthree.data.api

import com.pthw.mypagingthree.data.model.response.DataPageResponse
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SplashPhotoService {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<DataPageResponse<SplashPhoto>>

}