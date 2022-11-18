package com.pthw.domain.feature.splashimage.usecase

import androidx.paging.PagingData
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import com.pthw.domain.feature.splashimage.repository.SplashPhotoRepository
import com.pthw.domain.utils.CoroutineUseCase
import com.pthw.domain.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResults @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: SplashPhotoRepository
) : CoroutineUseCase<String, Flow<PagingData<SplashPhoto>>>(dispatcherProvider) {
    override suspend fun provide(params: String): Flow<PagingData<SplashPhoto>> {
        return repository.getSearchResults(params)
    }

}