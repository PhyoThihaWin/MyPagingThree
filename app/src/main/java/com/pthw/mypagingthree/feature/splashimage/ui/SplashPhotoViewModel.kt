package com.pthw.mypagingthree.feature.splashimage.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pthw.appbase.exception.ExceptionToStringMapper
import com.pthw.domain.feature.splashimage.repository.SplashPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SplashPhotoViewModel @Inject constructor(
    private val repository: SplashPhotoRepository,
    private val exception: ExceptionToStringMapper,
) : ViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData()

    var pagingFlow = queryString.asFlow().flatMapLatest {
        repository.getSearchResults(it)
    }.cachedIn(viewModelScope)

    fun searchPhotos(query: String) {
        queryString.postValue(query)
    }

    fun mapException(t: Throwable) = exception.map(t)

//    private var _userSearchText = MutableStateFlow("")
//    private val userSearchText = _userSearchText.asStateFlow()
//
//    val pagingFlow = userSearchText.flatMapLatest {
//        Pager(
//            config = PagingConfig(
//                pageSize = ITEMS_PER_PAGE,
//                initialLoadSize = ITEMS_PER_PAGE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { repository.getSearchResults(it) }
//        ).flow.cachedIn(viewModelScope)
//    }
//
//    fun searchPhotos(query: String) {
//        _userSearchText.value = query
//    }
}
