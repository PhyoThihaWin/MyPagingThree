package com.pthw.mypagingthree.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pthw.mypagingthree.data.api.GithubService
import com.pthw.mypagingthree.data.model.Repo
import com.pthw.mypagingthree.db.RepoDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 30

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val database: RepoDatabase
): GithubRepository {
    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}