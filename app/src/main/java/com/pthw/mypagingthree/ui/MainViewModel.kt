package com.pthw.mypagingthree.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pthw.mypagingthree.data.model.Article
import com.pthw.mypagingthree.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

private const val ITEMS_PER_PAGE = 50

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ArticleRepository
): ViewModel() {

    val items: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false, initialLoadSize = ITEMS_PER_PAGE*2),
        pagingSourceFactory = { repository.articlePagingSource() }
    ).flow.cachedIn(viewModelScope)
}