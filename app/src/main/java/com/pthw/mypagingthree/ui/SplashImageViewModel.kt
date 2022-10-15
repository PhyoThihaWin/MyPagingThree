package com.pthw.mypagingthree.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.data.repository.SplashPhotoRepository
import com.pthw.mypagingthree.utils.exception.ExceptionToStringMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@HiltViewModel
class SplashImageViewModel @Inject constructor(
    private val repository: SplashPhotoRepository,
    private val exception: ExceptionToStringMapper,
) : ViewModel() {

    fun searchPhotos(query: String): Flow<PagingData<SplashPhoto>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { repository.getSearchResults(query) }
        ).flow.cachedIn(viewModelScope)
    }

}