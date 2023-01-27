package com.pthw.mypagingthree.feature.article.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pthw.domain.feature.article.model.Article
import com.pthw.domain.feature.article.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    val items: Flow<PagingData<Article>> = repository.getArticles().cachedIn(viewModelScope)
}
