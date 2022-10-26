package com.pthw.cache.feature.githubrepo.datasource_impl

import androidx.paging.*
import com.pthw.cache.database.RepoDatabase
import com.pthw.cache.feature.githubrepo.mapper.RepoCacheToDataMapper
import com.pthw.cache.feature.githubrepo.mapper.RepoNetworkToCacheMapper
import com.pthw.cache.feature.githubrepo.mediator.GithubRemoteMediator
import com.pthw.data.feature.githubrepo.datasource.RepoCacheDSource
import com.pthw.data.feature.githubrepo.entity.RepoEntity
import com.pthw.network.feature.githubrepo.service.GithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

class RepoCacheDSourceImpl @Inject constructor(
    private val database: RepoDatabase,
    private val service: GithubService,
    private val cacheToDataMapper: RepoCacheToDataMapper,
    private val networkToCacheMapper: RepoNetworkToCacheMapper
) : RepoCacheDSource {

    override fun getSearchResultStream(query: String): Flow<PagingData<RepoEntity>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            remoteMediator = GithubRemoteMediator(query, service, database, networkToCacheMapper),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map(cacheToDataMapper::map)
        }
    }
}