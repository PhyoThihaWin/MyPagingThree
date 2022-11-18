package com.pthw.network.feature.samplepost.service

import com.pthw.network.feature.samplepost.response.CommentSource
import com.pthw.network.feature.samplepost.response.PostSource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PostService {
    @GET
    suspend fun getLatestPost(
      @Url url: String = "https://jsonplaceholder.typicode.com/posts"
    ): Response<List<PostSource>>

    @GET
    suspend fun getPostDetail(
        @Url url: String = "https://jsonplaceholder.typicode.com/comments",
        @Query("postId") postId: Int
    ): Response<List<CommentSource>>
}