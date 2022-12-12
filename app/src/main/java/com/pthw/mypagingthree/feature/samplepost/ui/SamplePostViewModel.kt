package com.pthw.mypagingthree.feature.samplepost.ui

import androidx.lifecycle.viewModelScope
import com.pthw.appbase.core.BaseViewModel
import com.pthw.appbase.core.viewstate.ListViewState
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.domain.feature.samplepost.usecase.GetLatestPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SamplePostViewModel @Inject constructor(
    private val getLatestPost: GetLatestPost
) : BaseViewModel() {

    private val _post: MutableStateFlow<ListViewState<List<Post>>> =
        MutableStateFlow(ListViewState.Idle())
    val postFlow = _post.asStateFlow()

    fun getLatestPost() {
        _post.value = ListViewState.Loading()
        viewModelScope.launch {
            runCatching {
                val data = getLatestPost.execute(Unit)
                _post.value = ListViewState.Success(data)
            }.getOrElse {
                Timber.e(it)
                _post.value = ListViewState.Error(exception.map(it))
            }
        }
    }
}
