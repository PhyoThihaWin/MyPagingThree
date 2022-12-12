package com.pthw.domain.feature.githubrepo.repository

import androidx.paging.PagingData
import com.pthw.domain.feature.githubrepo.model.Repo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}
