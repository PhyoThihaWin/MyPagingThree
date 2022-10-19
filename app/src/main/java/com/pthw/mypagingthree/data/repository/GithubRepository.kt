package com.pthw.mypagingthree.data.repository

import androidx.paging.PagingData
import com.pthw.mypagingthree.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}