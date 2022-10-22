package com.pthw.mypagingthree.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pthw.mypagingthree.data.api.GithubService
import com.pthw.mypagingthree.data.model.Repo
import com.pthw.mypagingthree.db.RepoDatabase
import com.pthw.mypagingthree.paging.mediator.GithubRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val database: RepoDatabase
): GithubRepository {

    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = NETWORK_PAGE_SIZE),
            remoteMediator = GithubRemoteMediator(query, service, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }
}