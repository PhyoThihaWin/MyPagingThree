package com.pthw.data.feature.githubrepo.repository_impl

import androidx.paging.PagingData
import androidx.paging.map
import com.pthw.data.feature.article.mapper.ArticleEntityMapper
import com.pthw.data.feature.githubrepo.datasource.RepoCacheDSource
import com.pthw.data.feature.githubrepo.mapper.RepoEntityMapper
import com.pthw.domain.feature.githubrepo.model.Repo
import com.pthw.domain.feature.githubrepo.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GithubRepositoryImpl @Inject constructor(
    private val repoCacheDSource: RepoCacheDSource,
    private val mapper: RepoEntityMapper
) : GithubRepository {
    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        return repoCacheDSource.getSearchResultStream(query).map { pagingData ->
            pagingData.map(mapper::map)
        }
    }

}