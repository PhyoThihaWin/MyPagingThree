package com.pthw.network.feature.githubrepo.service

import com.pthw.network.feature.githubrepo.response.RepoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubService {
    /**
     * Get repos ordered by stars.
     */
    @GET
    suspend fun searchRepos(
        @Url url: String = "https://api.github.com/search/repositories?sort=stars",
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<RepoSearchResponse>
}