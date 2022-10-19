package com.pthw.mypagingthree.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pthw.mypagingthree.base.BaseViewModel
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.data.repository.SplashPhotoRepository
import com.pthw.mypagingthree.utils.exception.ExceptionToStringMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class SplashImageViewModel @Inject constructor(
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