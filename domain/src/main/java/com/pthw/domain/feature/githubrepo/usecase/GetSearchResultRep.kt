package com.pthw.domain.feature.githubrepo.usecase

import androidx.paging.PagingData
import com.pthw.domain.feature.githubrepo.model.Repo
import com.pthw.domain.feature.githubrepo.repository.GithubRepository
import com.pthw.domain.utils.CoroutineUseCase
import com.pthw.domain.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultRep @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: GithubRepository
) : CoroutineUseCase<String, Flow<PagingData<Repo>>>(dispatcherProvider) {
    override suspend fun provide(params: String): Flow<PagingData<Repo>> {
        return repository.getSearchResultStream(params)
    }
}
