package com.pthw.mypagingthree.feature.samplepost.ui

import androidx.lifecycle.viewModelScope
import com.pthw.appbase.core.BaseViewModel
import com.pthw.appbase.core.viewstate.ListViewState
import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.domain.feature.samplepost.repository.PostRepository
import com.pthw.domain.feature.samplepost.usecase.GetPostDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostDetail: GetPostDetail
): BaseViewModel() {

    var post: Post? = null

    private val _comment: MutableStateFlow<ListViewState<List<Comment>>> =
        MutableStateFlow(ListViewState.Idle())
    val cmFlow = _comment.asStateFlow()

    fun getPostDetail(postId: Int) {
        _comment.value = ListViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getPostDetail.execute(postId)
                _comment.value = ListViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                _comment.value = ListViewState.Error(exception.map(it))
            }
        }
    }
}