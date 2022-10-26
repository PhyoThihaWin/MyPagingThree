package com.pthw.data.feature.githubrepo.datasource

import androidx.paging.PagingData
import com.pthw.data.feature.githubrepo.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoCacheDSource {
    fun getSearchResultStream(query: String): Flow<PagingData<RepoEntity>>
}